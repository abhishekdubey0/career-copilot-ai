package com.careercopilot.intelligence.dto.analyzer;

import java.util.List;

public record JobAnalysis(
        String jobTitle,
        List<JobRequirement> requirements
) {
}