package com.careercopilot.intelligence.service;

import com.careercopilot.intelligence.analyzer.AtsAnalyzer;
import com.careercopilot.intelligence.analyzer.ResumeImprover;
import com.careercopilot.intelligence.client.ResumeClient;
import com.careercopilot.intelligence.dto.ai.ImprovedResume;
import com.careercopilot.intelligence.dto.request.ResumeAnalysisRequest;
import com.careercopilot.intelligence.dto.response.ResumeAnalysisResponse;
import com.careercopilot.intelligence.entity.ResumeAnalysis;
import com.careercopilot.intelligence.exception.AnalysisNotFoundException;
import com.careercopilot.intelligence.repository.ResumeAnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeAnalysisServiceImpl implements ResumeAnalysisService {

    private final ResumeClient resumeClient;
    private final AtsAnalyzer atsAnalyzer;
    private final ResumeAnalysisRepository resumeAnalysisRepository;
    private final ResumeImprover resumeImprover;

    @Override
    public ResumeAnalysisResponse analyze(
            ResumeAnalysisRequest request) {

        String resumeText =
                resumeClient.getResumeText(request.resumeId());

        ResumeAnalysisResponse analysis =
                atsAnalyzer.analyze(
                        resumeText,
                        request.jobDescription()
                );

        ResumeAnalysis entity = ResumeAnalysis.builder()
                .resumeId(request.resumeId())
                .jobDescription(request.jobDescription())
                .atsScore(analysis.atsScore())
                .matchedRequirements(
                        String.join(
                                ", ",
                                analysis.matchedRequirements()
                        )
                )
                .missingRequirements(
                        String.join(
                                ", ",
                                analysis.missingRequirements()
                        )
                )
                .createdAt(LocalDateTime.now())
                .build();

        ResumeAnalysis saved =
                resumeAnalysisRepository.save(entity);

        return new ResumeAnalysisResponse(
                saved.getId(),
                analysis.atsScore(),
                analysis.matchedRequirements(),
                analysis.missingRequirements(),
                analysis.strengths(),
                analysis.suggestions()
        );
    }

    @Override
    public ResumeAnalysisResponse getAnalysisById(UUID analysisId) {

        ResumeAnalysis analysis = resumeAnalysisRepository
                .findById(analysisId)
                .orElseThrow(() ->
                        new AnalysisNotFoundException(analysisId)
                );

        return new ResumeAnalysisResponse(
                analysis.getId(),
                analysis.getAtsScore(),
                toList(analysis.getMatchedRequirements()),
                toList(analysis.getMissingRequirements()),
                List.of(),
                List.of()
        );
    }

    private List<String> toList(String value) {

        if (value == null || value.isBlank()) {
            return List.of();
        }

        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .toList();
    }
}