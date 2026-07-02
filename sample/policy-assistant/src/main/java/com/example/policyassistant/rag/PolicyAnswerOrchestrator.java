package com.example.policyassistant.rag;

import com.example.policyassistant.api.dto.PolicyAnswerResponse;
import com.example.policyassistant.config.AiProperties;
import com.example.policyassistant.domain.PolicyChunk;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class PolicyAnswerOrchestrator {

    private static final String USE_CASE_ID = "hr-policy-assistant";

    private final ChatModel chatModel;
    private final PromptTemplateLoader promptTemplateLoader;
    private final AiProperties properties;
    private final ObjectMapper objectMapper;

    public PolicyAnswerOrchestrator(
            ChatModel chatModel,
            PromptTemplateLoader promptTemplateLoader,
            AiProperties properties,
            ObjectMapper objectMapper) {
        this.chatModel = chatModel;
        this.promptTemplateLoader = promptTemplateLoader;
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    public PolicyAnswerResponse answer(String question, List<PolicyChunk> retrievedChunks) {
        if (retrievedChunks.isEmpty()) {
            return insufficientInformation();
        }

        String context = formatContext(retrievedChunks);
        String promptText = promptTemplateLoader.render(promptTemplateLoader.loadTemplate(), question, context);
        String raw = chatModel.call(new Prompt(promptText)).getResult().getOutput().getText();
        return parseModelOutput(raw, retrievedChunks);
    }

    public String useCaseId() {
        return USE_CASE_ID;
    }

    private PolicyAnswerResponse insufficientInformation() {
        return new PolicyAnswerResponse(
                "I do not have enough policy information to answer that question.",
                List.of(),
                "low",
                List.of("No matching policy documents were retrieved."),
                properties.promptVersion(),
                properties.modelName());
    }

    private String formatContext(List<PolicyChunk> chunks) {
        return chunks.stream()
                .map(chunk -> "[documentId=" + chunk.documentId() + "]\n" + chunk.content())
                .collect(Collectors.joining("\n\n"));
    }

    private PolicyAnswerResponse parseModelOutput(String raw, List<PolicyChunk> retrievedChunks) {
        try {
            JsonNode node = objectMapper.readTree(raw);
            List<PolicyAnswerResponse.Citation> citations = new ArrayList<>();
            if (node.has("citations") && node.get("citations").isArray()) {
                node.get("citations").forEach(item -> citations.add(new PolicyAnswerResponse.Citation(
                        item.path("documentId").asText(),
                        item.path("excerpt").asText())));
            }
            if (citations.isEmpty()) {
                retrievedChunks.forEach(chunk -> citations.add(new PolicyAnswerResponse.Citation(
                        chunk.documentId(), truncate(chunk.content(), 120))));
            }
            List<String> warnings = new ArrayList<>();
            if (node.has("warnings") && node.get("warnings").isArray()) {
                node.get("warnings").forEach(item -> warnings.add(item.asText()));
            }
            return new PolicyAnswerResponse(
                    node.path("answer").asText(),
                    citations,
                    node.path("confidence").asText("medium"),
                    List.copyOf(warnings),
                    properties.promptVersion(),
                    properties.modelName());
        } catch (Exception ex) {
            return new PolicyAnswerResponse(
                    raw,
                    retrievedChunks.stream()
                            .map(chunk -> new PolicyAnswerResponse.Citation(
                                    chunk.documentId(), truncate(chunk.content(), 120)))
                            .toList(),
                    "medium",
                    List.of("Model output was not valid JSON; returned raw text."),
                    properties.promptVersion(),
                    properties.modelName());
        }
    }

    private String truncate(String value, int max) {
        if (value.length() <= max) {
            return value;
        }
        return value.substring(0, max) + "...";
    }
}
