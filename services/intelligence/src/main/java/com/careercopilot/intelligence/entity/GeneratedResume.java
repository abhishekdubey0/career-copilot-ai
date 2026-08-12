package com.careercopilot.intelligence.entity;

import com.careercopilot.intelligence.dto.ai.ImprovedResume;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "generated_resumes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedResume {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "resume_id", nullable = false)
    private UUID resumeId;

    @Column(name = "analysis_id", nullable = false)
    private UUID analysisId;

    @Column(name = "job_description", nullable = false, columnDefinition = "TEXT")
    private String jobDescription;

    @Column(nullable = false)
    private Integer version;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "resume_content", nullable = false, columnDefinition = "jsonb")
    private ImprovedResume resumeContent;

    @Column(name = "latex_code", columnDefinition = "TEXT")
    private String latexCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}