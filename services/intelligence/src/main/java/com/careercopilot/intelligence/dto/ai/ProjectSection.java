package com.careercopilot.intelligence.dto.ai;

import java.util.List;

public record ProjectSection(

        String name,

        String description,

        List<String> technologies,

        List<String> achievements

) {
}