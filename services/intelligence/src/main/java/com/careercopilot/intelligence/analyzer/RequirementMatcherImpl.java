package com.careercopilot.intelligence.analyzer;

import com.careercopilot.intelligence.dto.analyzer.JobRequirement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequirementMatcherImpl implements RequirementMatcher {

    @Override
    public List<JobRequirement> findMatchedRequirements(
            String resumeText,
            List<JobRequirement> requirements) {

        String normalizedResume =
                resumeText.toLowerCase();

        return requirements.stream()
                .filter(requirement ->
                        normalizedResume.contains(
                                requirement.value().toLowerCase()
                        )
                )
                .toList();
    }

    @Override
    public List<JobRequirement> findMissingRequirements(
            String resumeText,
            List<JobRequirement> requirements) {

        String normalizedResume =
                resumeText.toLowerCase();

        return requirements.stream()
                .filter(requirement ->
                        !normalizedResume.contains(
                                requirement.value().toLowerCase()
                        )
                )
                .toList();
    }
}