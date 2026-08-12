package com.careercopilot.intelligence.analyzer;

import com.careercopilot.intelligence.dto.response.ResumeAnalysisResponse;

public interface AtsAnalyzer {

    ResumeAnalysisResponse analyze(
            String resumeText,
            String jobDescription
    );
}