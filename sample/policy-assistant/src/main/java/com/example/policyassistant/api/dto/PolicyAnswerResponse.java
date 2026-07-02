package com.example.policyassistant.api.dto;

import java.util.List;

public record PolicyAnswerResponse(
        String answer,
        List<Citation> citations,
        String confidence,
        List<String> warnings,
        String promptVersion,
        String modelName) {

    public record Citation(String documentId, String excerpt) {}
}
