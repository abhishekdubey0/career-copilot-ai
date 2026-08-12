package com.careercopilot.intelligence.dto.analyzer;

import com.careercopilot.intelligence.analyzer.RequirementType;

public record JobRequirement(
        String value,
        RequirementType type,
        boolean required
) {
}