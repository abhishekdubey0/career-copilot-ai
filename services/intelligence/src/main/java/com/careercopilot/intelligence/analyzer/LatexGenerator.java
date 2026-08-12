package com.careercopilot.intelligence.analyzer;

import com.careercopilot.intelligence.dto.ai.ImprovedResume;

public interface LatexGenerator {

    String generate(ImprovedResume resume);
}