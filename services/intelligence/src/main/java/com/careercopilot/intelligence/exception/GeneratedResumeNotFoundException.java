package com.careercopilot.intelligence.exception;

import java.util.UUID;

public class GeneratedResumeNotFoundException extends RuntimeException {

    public GeneratedResumeNotFoundException(UUID generatedResumeId) {
        super("Generated resume not found: " + generatedResumeId);
    }
}