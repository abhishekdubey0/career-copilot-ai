package com.careercopilot.intelligence.analyzer;

import com.careercopilot.intelligence.analyzer.ResumeImprover;
import com.careercopilot.intelligence.dto.ai.ImprovedResume;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResumeImproverImpl implements ResumeImprover {

    private final ChatClient chatClient;

    @Override
    public ImprovedResume improve(
            String resumeText,
            String jobDescription) {

        String systemPrompt = """
                You are an expert resume optimization assistant.

                Your task is to improve a candidate's resume for the
                provided job description.

                IMPORTANT RULES:

                1. Never invent work experience.
                2. Never invent companies.
                3. Never invent projects.
                4. Never invent technologies the candidate has not used.
                5. Never invent certifications.
                6. Preserve factual information from the original resume.
                7. Improve wording to better align with the job description.
                8. Use strong, concise, achievement-oriented language.
                9. Incorporate relevant job requirements only when they
                   are supported by the original resume.
                10. Do not add keywords merely for ATS manipulation.
                11. Keep the resume professional and concise.

                Improve:
                - Professional summary
                - Skills
                - Experience
                - Projects
                - Education
                - Certifications

                Return only structured data matching the requested schema.
                """;

        String userPrompt = """
                JOB DESCRIPTION:

                %s

                ORIGINAL RESUME:

                %s
                """.formatted(
                jobDescription,
                resumeText
        );

        return chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .entity(ImprovedResume.class);
    }
}