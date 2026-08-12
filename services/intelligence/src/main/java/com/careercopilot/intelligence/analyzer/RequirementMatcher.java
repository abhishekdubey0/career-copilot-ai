package com.careercopilot.intelligence.analyzer;

import com.careercopilot.intelligence.dto.analyzer.JobRequirement;

import java.util.List;

public interface RequirementMatcher {

    List<JobRequirement> findMatchedRequirements(
            String resumeText,
            List<JobRequirement> requirements
    );

    List<JobRequirement> findMissingRequirements(
            String resumeText,
            List<JobRequirement> requirements
    );
}