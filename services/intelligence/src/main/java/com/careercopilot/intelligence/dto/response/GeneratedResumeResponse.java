package com.careercopilot.intelligence.dto.response;

import com.careercopilot.intelligence.dto.ai.ImprovedResume;

import java.time.LocalDateTime;
import java.util.UUID;

public record GeneratedResumeResponse(
        UUID id,
        UUID resumeId,
        UUID analysisId,
        Integer version,
        ImprovedResume resumeContent,
        String latexCode,
        LocalDateTime createdAt
) {
}