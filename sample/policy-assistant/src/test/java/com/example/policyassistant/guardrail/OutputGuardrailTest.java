package com.example.policyassistant.guardrail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.policyassistant.api.dto.PolicyAnswerResponse;
import com.example.policyassistant.config.AiProperties;
import java.util.List;
import org.junit.jupiter.api.Test;

class OutputGuardrailTest {

    private final OutputGuardrail outputGuardrail = new OutputGuardrail(new AiProperties(
            "policy-answer-v1",
            "stub-local-v1",
            3,
            new AiProperties.Guardrail(true, List.of("(?i)\\b\\d{3}-\\d{2}-\\d{4}\\b"))));

    @Test
    void blocksSsnLikeOutput() {
        PolicyAnswerResponse response = new PolicyAnswerResponse(
                "Employee SSN is 123-45-6789.",
                List.of(new PolicyAnswerResponse.Citation("pto-policy", "sample")),
                "high",
                List.of(),
                "policy-answer-v1",
                "stub-local-v1");

        assertThatThrownBy(() -> outputGuardrail.validate(response))
                .isInstanceOf(GuardrailViolationException.class)
                .hasMessage("Output blocked by PII pattern guardrail.");
    }

    @Test
    void warnsWhenCitationsAreMissing() {
        PolicyAnswerResponse response = new PolicyAnswerResponse(
                "Use HR policy sources.",
                List.of(),
                "low",
                List.of(),
                "policy-answer-v1",
                "stub-local-v1");

        PolicyAnswerResponse validated = outputGuardrail.validate(response);

        assertThat(validated.warnings()).contains("No citations returned; answer may be ungrounded.");
    }
}
