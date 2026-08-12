package com.careercopilot.intelligence.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ResumeAnalysisRequest(

        @NotNull(message = "Resume ID is required")
        UUID resumeId,

        @NotBlank(message = "Job description is required")
        String jobDescription

) {
}