package com.careercopilot.intelligence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "resume_analyses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "resume_id", nullable = false)
    private UUID resumeId;

    @Column(name = "job_description", nullable = false, columnDefinition = "TEXT")
    private String jobDescription;

    @Column(name = "ats_score", nullable = false)
    private Integer atsScore;

    @Column(name = "matched_requirements", columnDefinition = "TEXT")
    private String matchedRequirements;

    @Column(name = "missing_requirements", columnDefinition = "TEXT")
    private String missingRequirements;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}