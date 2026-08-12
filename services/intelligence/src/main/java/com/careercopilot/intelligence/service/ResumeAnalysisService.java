package com.careercopilot.intelligence.service;

import com.careercopilot.intelligence.dto.ai.ImprovedResume;
import com.careercopilot.intelligence.dto.request.ResumeAnalysisRequest;
import com.careercopilot.intelligence.dto.response.ResumeAnalysisResponse;

import java.util.UUID;

public interface ResumeAnalysisService {

    ResumeAnalysisResponse analyze(ResumeAnalysisRequest request);

    ResumeAnalysisResponse getAnalysisById(UUID analysisId);
}