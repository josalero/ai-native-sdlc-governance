package com.example.policyassistant.guardrail;

import com.example.policyassistant.config.AiProperties;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class InputGuardrail {

    private static final List<String> INJECTION_MARKERS = List.of(
            "ignore previous instructions",
            "ignore all prior",
            "system prompt",
            "jailbreak",
            "reveal your instructions");

    private final AiProperties properties;

    public InputGuardrail(AiProperties properties) {
        this.properties = properties;
    }

    public void validate(String question) {
        if (!properties.guardrail().promptInjectionEnabled()) {
            return;
        }
        if (question == null || question.isBlank()) {
            throw new GuardrailViolationException("Question must not be blank.");
        }
        String normalized = question.toLowerCase(Locale.ROOT);
        for (String marker : INJECTION_MARKERS) {
            if (normalized.contains(marker)) {
                throw new GuardrailViolationException("Input blocked by prompt-injection guardrail.");
            }
        }
    }
}
