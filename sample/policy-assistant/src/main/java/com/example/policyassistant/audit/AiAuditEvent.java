package com.example.policyassistant.audit;

import java.time.Instant;
import java.util.List;

public record AiAuditEvent(
        String requestId,
        String userId,
        String useCaseId,
        String promptVersion,
        String modelName,
        int retrievedDocumentCount,
        List<String> retrievedDocumentIds,
        String outputValidationResult,
        String safetyValidationResult,
        long latencyMs,
        Instant timestamp) {}
