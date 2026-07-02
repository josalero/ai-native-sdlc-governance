package com.example.policyassistant.rag;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

@Component
public class PromptTemplateLoader {

    public String loadTemplate() {
        Path promptPath = Path.of("prompts", "policy-answer-v1.prompt");
        if (Files.exists(promptPath)) {
            try {
                return Files.readString(promptPath, StandardCharsets.UTF_8);
            } catch (IOException ignored) {
                // fall through to classpath default
            }
        }
        return defaultTemplate();
    }

    public String render(String template, String question, String context) {
        return template.replace("{{question}}", question).replace("{{context}}", context);
    }

    private String defaultTemplate() {
        return """
                System:
                Answer using only the policy excerpts. Respond in JSON.

                User:
                Question: {{question}}

                Policy excerpts:
                {{context}}
                """;
    }
}
