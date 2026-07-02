package com.example.policyassistant.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.ai")
public record AiProperties(
        String promptVersion,
        String modelName,
        int maxRetrievedChunks,
        Guardrail guardrail) {

    public record Guardrail(boolean promptInjectionEnabled, java.util.List<String> outputPiiPatterns) {}
}
