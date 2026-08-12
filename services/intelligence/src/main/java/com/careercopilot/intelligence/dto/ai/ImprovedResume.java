package com.careercopilot.intelligence.dto.ai;

import java.util.List;

public record ImprovedResume(

        String professionalSummary,

        List<String> skills,

        List<ExperienceSection> experience,

        List<ProjectSection> projects,

        List<String> education,

        List<String> certifications

) {
}