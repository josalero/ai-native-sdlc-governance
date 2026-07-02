package com.example.policyassistant.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PolicyQuestionRequest(
        @NotBlank @Size(max = 2000) String question,
        @NotBlank @Size(max = 100) String userId) {}
