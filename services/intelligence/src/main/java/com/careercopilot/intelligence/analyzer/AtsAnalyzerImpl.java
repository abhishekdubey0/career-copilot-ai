package com.careercopilot.intelligence.analyzer;

import com.careercopilot.intelligence.dto.analyzer.JobAnalysis;
import com.careercopilot.intelligence.dto.analyzer.JobRequirement;
import com.careercopilot.intelligence.dto.response.ResumeAnalysisResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AtsAnalyzerImpl implements AtsAnalyzer {

    private final AiJobAnalyzer aiJobAnalyzer;
    private final RequirementMatcher requirementMatcher;

    @Override
    public ResumeAnalysisResponse analyze(
            String resumeText,
            String jobDescription) {

        JobAnalysis jobAnalysis =
                aiJobAnalyzer.analyze(jobDescription);

        List<JobRequirement> requirements =
                jobAnalysis.requirements();

        List<JobRequirement> matched =
                requirementMatcher.findMatchedRequirements(
                        resumeText,
                        requirements
                );

        List<JobRequirement> missing =
                requirementMatcher.findMissingRequirements(
                        resumeText,
                        requirements
                );

        int atsScore = calculateScore(
                matched.size(),
                requirements.size()
        );

        return new ResumeAnalysisResponse(
                null,
                atsScore,
                matched.stream()
                        .map(JobRequirement::value)
                        .toList(),
                missing.stream()
                        .map(JobRequirement::value)
                        .toList(),
                buildStrengths(matched),
                buildSuggestions(missing)
        );
    }

    private int calculateScore(int matched, int total) {

        if (total == 0) {
            return 0;
        }

        return (matched * 100) / total;
    }

    private List<String> buildStrengths(
            List<JobRequirement> matched) {

        if (matched.isEmpty()) {
            return List.of(
                    "No job requirements were matched."
            );
        }

        return List.of(
                "Matched " + matched.size()
                        + " of the identified job requirements."
        );
    }

    private List<String> buildSuggestions(
            List<JobRequirement> missing) {

        if (missing.isEmpty()) {
            return List.of(
                    "Resume covers the identified job requirements."
            );
        }

        return List.of(
                "Consider addressing the missing job requirements."
        );
    }
}