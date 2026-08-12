package com.careercopilot.intelligence.analyzer;

import com.careercopilot.intelligence.dto.analyzer.JobAnalysis;

public interface AiJobAnalyzer {

    JobAnalysis analyze(String jobDescription);
}