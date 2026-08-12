package com.careercopilot.intelligence.exception;

import java.util.UUID;

public class AnalysisNotFoundException extends RuntimeException {

    public AnalysisNotFoundException(UUID analysisId) {
        super("Resume analysis not found: " + analysisId);
    }
}