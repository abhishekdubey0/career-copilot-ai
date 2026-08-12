package com.careercopilot.intelligence.analyzer;

import com.careercopilot.intelligence.dto.analyzer.JobAnalysis;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AiJobAnalyzerImpl implements AiJobAnalyzer {

    private final ChatClient chatClient;

    @Override
    public JobAnalysis analyze(String jobDescription) {

        String systemPrompt = """
                You are a professional job description analyzer.

                Analyze the provided job description.

                Extract:
                - Job title
                - Skills
                - Tools
                - Domain knowledge
                - Responsibilities
                - Education requirements
                - Experience requirements
                - Certifications

                Classify each requirement using ONLY:
                SKILL
                TOOL
                DOMAIN_KNOWLEDGE
                RESPONSIBILITY
                EDUCATION
                EXPERIENCE
                CERTIFICATION
                OTHER

                Set required=true only when the requirement is clearly
                mandatory, required, essential, or explicitly expected.

                Do not invent requirements that are not present in the
                job description.

                Return only structured data matching the requested schema.
                """;

        return chatClient.prompt()
                .system(systemPrompt)
                .user(jobDescription)
                .call()
                .entity(JobAnalysis.class);
    }
}