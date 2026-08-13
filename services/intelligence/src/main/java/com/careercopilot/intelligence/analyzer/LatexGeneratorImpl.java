package com.careercopilot.intelligence.analyzer;

import com.careercopilot.intelligence.dto.ai.ImprovedResume;
import com.careercopilot.intelligence.exception.AiServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LatexGeneratorImpl implements LatexGenerator {

    private final ChatClient chatClient;

    @Override
    public String generate(ImprovedResume resume) {

        String prompt = """
                Convert the following structured resume into a complete
                professional ATS-friendly LaTeX resume.

                Requirements:

                - Return ONLY valid LaTeX code.
                - Do not use Markdown.
                - Do not use ```latex fences.
                - Use \\documentclass.
                - The output must be compilable on Overleaf.
                - Use a clean single-column professional resume layout.
                - Make the resume ATS-friendly.
                - Do not invent or remove factual information.
                - Preserve all provided resume information.
                - Use standard LaTeX packages available on Overleaf.

                RESUME:

                %s
                """.formatted(resume);

        try {
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            throw new AiServiceException(
                    "Unable to generate latex with AI service",
                    e
            );
        }
    }
}