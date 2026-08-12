package com.careercopilot.intelligence.dto.response;

import java.util.List;
import java.util.UUID;

public record ResumeAnalysisResponse(
        UUID analysisId,
        int atsScore,
        List<String> matchedRequirements,
        List<String> missingRequirements,
        List<String> strengths,
        List<String> suggestions
) {
}