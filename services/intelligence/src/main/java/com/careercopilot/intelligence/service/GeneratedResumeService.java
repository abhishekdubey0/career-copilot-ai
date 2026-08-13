package com.careercopilot.intelligence.service;

import com.careercopilot.intelligence.dto.response.GeneratedResumeResponse;

import java.util.List;
import java.util.UUID;

public interface GeneratedResumeService {

    GeneratedResumeResponse improve(UUID analysisId);

    GeneratedResumeResponse generateLatex(UUID generatedResumeId);

    GeneratedResumeResponse getById(UUID generatedResumeId);

    List<GeneratedResumeResponse> getByResumeId(UUID resumeId);
}