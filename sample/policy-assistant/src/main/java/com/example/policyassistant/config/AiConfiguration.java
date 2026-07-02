package com.example.policyassistant.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import java.util.Locale;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;

@Configuration
@EnableConfigurationProperties(AiProperties.class)
public class AiConfiguration {

    @Bean
    @Profile("stub")
    ChatModel stubChatModel(ObjectMapper objectMapper) {
        return new StubChatModel(objectMapper);
    }

    static final class StubChatModel implements ChatModel {

        private final ObjectMapper objectMapper;

        StubChatModel(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public ChatResponse call(Prompt prompt) {
            String userText = prompt.getInstructions().stream()
                    .map(message -> message.getText() != null ? message.getText() : "")
                    .reduce("", String::concat);
            String json = buildGroundedJson(userText);
            return new ChatResponse(List.of(new Generation(new AssistantMessage(json))));
        }

        @Override
        public Flux<ChatResponse> stream(Prompt prompt) {
            return Flux.just(call(prompt));
        }

        private String buildGroundedJson(String promptText) {
            try {
                ObjectNode root = objectMapper.createObjectNode();
                ArrayNode citations = objectMapper.createArrayNode();
                String answer = "I could not find a grounded answer in the policy excerpts.";
                String confidence = "low";
                String question = extractQuestion(promptText);

                for (String block : promptText.split("\\[documentId=")) {
                    if (!block.contains("]")) {
                        continue;
                    }
                    int end = block.indexOf(']');
                    String documentId = block.substring(0, end);
                    String content = block.substring(end + 1).trim();
                    if (content.isBlank()) {
                        continue;
                    }
                    if (citations.isEmpty()) {
                        answer = summarize(content, question);
                        confidence = "high";
                    }
                    ObjectNode citation = objectMapper.createObjectNode();
                    citation.put("documentId", documentId);
                    citation.put("excerpt", truncate(content, 120));
                    citations.add(citation);
                }

                root.put("answer", answer);
                root.set("citations", citations);
                root.put("confidence", confidence);
                root.putArray("warnings");
                return objectMapper.writeValueAsString(root);
            } catch (Exception ex) {
                return "{\"answer\":\"Unable to generate stub response.\",\"citations\":[],\"confidence\":\"low\",\"warnings\":[\"stub-error\"]}";
            }
        }

        private String extractQuestion(String promptText) {
            int start = promptText.indexOf("Question:");
            int end = promptText.indexOf("Policy excerpts:");
            if (start < 0 || end <= start) {
                return "";
            }
            return promptText.substring(start + "Question:".length(), end).trim();
        }

        private String summarize(String content, String question) {
            List<String> questionTokens = question.toLowerCase(Locale.ROOT).lines()
                    .flatMap(line -> List.of(line.split("\\W+")).stream())
                    .filter(token -> token.length() > 2)
                    .toList();

            return content.lines()
                    .filter(line -> !line.startsWith("#") && !line.isBlank())
                    .max((left, right) -> Integer.compare(score(left, questionTokens), score(right, questionTokens)))
                    .orElse("See cited policy excerpt.");
        }

        private int score(String line, List<String> questionTokens) {
            String normalizedLine = line.toLowerCase(Locale.ROOT);
            int score = 0;
            for (String token : questionTokens) {
                if (normalizedLine.contains(token)) {
                    score += token.length();
                }
            }
            return score;
        }

        private String truncate(String value, int max) {
            if (value.length() <= max) {
                return value;
            }
            return value.substring(0, max) + "...";
        }
    }
}
