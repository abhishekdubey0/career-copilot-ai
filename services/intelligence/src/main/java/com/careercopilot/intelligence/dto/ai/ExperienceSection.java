package com.careercopilot.intelligence.dto.ai;

import java.util.List;

public record ExperienceSection(

        String company,

        String role,

        String duration,

        List<String> achievements

) {
}