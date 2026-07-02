package com.example.policyassistant.guardrail;

import com.example.policyassistant.api.dto.PolicyAnswerResponse;
import com.example.policyassistant.config.AiProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class OutputGuardrail {

    private final AiProperties properties;

    public OutputGuardrail(AiProperties properties) {
        this.properties = properties;
    }

    public PolicyAnswerResponse validate(PolicyAnswerResponse response) {
        List<String> warnings = new ArrayList<>(response.warnings());
        String answer = response.answer();

        for (String pattern : properties.guardrail().outputPiiPatterns()) {
            if (Pattern.compile(pattern).matcher(answer).find()) {
                throw new GuardrailViolationException("Output blocked by PII pattern guardrail.");
            }
        }

        if (response.citations() == null || response.citations().isEmpty()) {
            warnings = new ArrayList<>(warnings);
            warnings.add("No citations returned; answer may be ungrounded.");
        }

        return new PolicyAnswerResponse(
                response.answer(),
                response.citations(),
                response.confidence(),
                List.copyOf(warnings),
                response.promptVersion(),
                response.modelName());
    }
}
