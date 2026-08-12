package com.careercopilot.intelligence.analyzer;

import com.careercopilot.intelligence.dto.ai.ImprovedResume;

public interface ResumeImprover {

    ImprovedResume improve(
            String resumeText,
            String jobDescription
    );
}