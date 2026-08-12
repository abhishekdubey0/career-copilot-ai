package com.careercopilot.intelligence.service;

import com.careercopilot.intelligence.analyzer.LatexGenerator;
import com.careercopilot.intelligence.analyzer.ResumeImprover;
import com.careercopilot.intelligence.client.ResumeClient;
import com.careercopilot.intelligence.dto.ai.ImprovedResume;
import com.careercopilot.intelligence.dto.response.GeneratedResumeResponse;
import com.careercopilot.intelligence.entity.GeneratedResume;
import com.careercopilot.intelligence.entity.ResumeAnalysis;
import com.careercopilot.intelligence.repository.GeneratedResumeRepository;
import com.careercopilot.intelligence.repository.ResumeAnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeneratedResumeServiceImpl implements GeneratedResumeService {

    private final ResumeClient resumeClient;
    private final ResumeImprover resumeImprover;
    private final ResumeAnalysisRepository resumeAnalysisRepository;
    private final GeneratedResumeRepository generatedResumeRepository;
    private final LatexGenerator latexGenerator;

    @Override
    public GeneratedResumeResponse improve(UUID analysisId) {

        ResumeAnalysis analysis =
                resumeAnalysisRepository.findById(analysisId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Resume analysis not found: " + analysisId
                                )
                        );

        String resumeText =
                resumeClient.getResumeText(
                        analysis.getResumeId()
                );

        ImprovedResume improvedResume =
                resumeImprover.improve(
                        resumeText,
                        analysis.getJobDescription()
                );

        int version =
                generatedResumeRepository
                        .findTopByResumeIdOrderByVersionDesc(
                                analysis.getResumeId()
                        )
                        .map(resume -> resume.getVersion() + 1)
                        .orElse(1);

        GeneratedResume generatedResume =
                GeneratedResume.builder()
                        .resumeId(analysis.getResumeId())
                        .analysisId(analysis.getId())
                        .jobDescription(analysis.getJobDescription())
                        .version(version)
                        .resumeContent(improvedResume)
                        .latexCode(null)
                        .createdAt(LocalDateTime.now())
                        .build();

        GeneratedResume saved =
                generatedResumeRepository.save(generatedResume);

        return toResponse(saved);
    }

    @Override
    public GeneratedResumeResponse generateLatex(
            UUID generatedResumeId) {

        GeneratedResume generatedResume =
                generatedResumeRepository.findById(generatedResumeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Generated resume not found: "
                                                + generatedResumeId
                                )
                        );

        String latexCode =
                latexGenerator.generate(
                        generatedResume.getResumeContent()
                );

        generatedResume.setLatexCode(latexCode);

        GeneratedResume saved =
                generatedResumeRepository.save(generatedResume);

        return toResponse(saved);
    }

    @Override
    public GeneratedResumeResponse getById(
            UUID generatedResumeId) {

        GeneratedResume generatedResume =
                generatedResumeRepository.findById(generatedResumeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Generated resume not found: "
                                                + generatedResumeId
                                )
                        );

        return toResponse(generatedResume);
    }

    private GeneratedResumeResponse toResponse(
            GeneratedResume resume) {

        return new GeneratedResumeResponse(
                resume.getId(),
                resume.getResumeId(),
                resume.getAnalysisId(),
                resume.getVersion(),
                resume.getResumeContent(),
                resume.getLatexCode(),
                resume.getCreatedAt()
        );
    }
}