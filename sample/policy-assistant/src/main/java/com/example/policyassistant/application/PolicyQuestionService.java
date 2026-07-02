package com.example.policyassistant.application;

import com.example.policyassistant.api.dto.PolicyAnswerResponse;
import com.example.policyassistant.api.dto.PolicyQuestionRequest;
import com.example.policyassistant.audit.AiAuditEvent;
import com.example.policyassistant.audit.AiAuditLogger;
import com.example.policyassistant.config.AiProperties;
import com.example.policyassistant.domain.PolicyChunk;
import com.example.policyassistant.guardrail.GuardrailViolationException;
import com.example.policyassistant.guardrail.InputGuardrail;
import com.example.policyassistant.guardrail.OutputGuardrail;
import com.example.policyassistant.rag.PolicyAnswerOrchestrator;
import com.example.policyassistant.rag.PolicyRetrievalService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PolicyQuestionService {

    private final InputGuardrail inputGuardrail;
    private final OutputGuardrail outputGuardrail;
    private final PolicyRetrievalService retrievalService;
    private final PolicyAnswerOrchestrator orchestrator;
    private final AiAuditLogger auditLogger;
    private final AiProperties aiProperties;

    public PolicyQuestionService(
            InputGuardrail inputGuardrail,
            OutputGuardrail outputGuardrail,
            PolicyRetrievalService retrievalService,
            PolicyAnswerOrchestrator orchestrator,
            AiAuditLogger auditLogger,
            AiProperties aiProperties) {
        this.inputGuardrail = inputGuardrail;
        this.outputGuardrail = outputGuardrail;
        this.retrievalService = retrievalService;
        this.orchestrator = orchestrator;
        this.auditLogger = auditLogger;
        this.aiProperties = aiProperties;
    }

    public PolicyAnswerResponse ask(PolicyQuestionRequest request) {
        long start = System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();
        String safetyResult = "pass";
        String outputResult = "pass";
        List<String> retrievedDocumentIds = List.of();

        try {
            inputGuardrail.validate(request.question());
            List<PolicyChunk> chunks = retrievalService.retrieve(request.question());
            retrievedDocumentIds = chunks.stream().map(PolicyChunk::documentId).toList();
            PolicyAnswerResponse response = orchestrator.answer(request.question(), chunks);
            PolicyAnswerResponse validated = outputGuardrail.validate(response);
            audit(request, requestId, retrievedDocumentIds, outputResult, safetyResult, start);
            return validated;
        } catch (GuardrailViolationException ex) {
            safetyResult = "blocked";
            outputResult = "blocked";
            audit(request, requestId, retrievedDocumentIds, outputResult, safetyResult, start);
            throw ex;
        }
    }

    private void audit(
            PolicyQuestionRequest request,
            String requestId,
            List<String> retrievedDocumentIds,
            String outputResult,
            String safetyResult,
            long start) {
        auditLogger.log(new AiAuditEvent(
                requestId,
                request.userId(),
                orchestrator.useCaseId(),
                aiProperties.promptVersion(),
                aiProperties.modelName(),
                retrievedDocumentIds.size(),
                List.copyOf(retrievedDocumentIds),
                outputResult,
                safetyResult,
                System.currentTimeMillis() - start,
                Instant.now()));
    }
}
