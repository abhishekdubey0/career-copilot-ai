package com.careercopilot.resume.service;

import com.careercopilot.resume.dto.response.ResumeResponse;
import com.careercopilot.resume.dto.response.StoredFile;
import com.careercopilot.resume.entity.Resume;
import com.careercopilot.resume.exception.InvalidFileException;
import com.careercopilot.resume.exception.ResourceNotFoundException;
import com.careercopilot.resume.exception.ResumeDeletionException;
import com.careercopilot.resume.exception.ResumeUploadException;
import com.careercopilot.resume.mapper.ResumeMapper;
import com.careercopilot.resume.repository.ResumeRepository;
import com.careercopilot.resume.util.FileStorageUtil;
import com.careercopilot.resume.util.PdfExtractorUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    @Value("${resume.upload.max-file-size}")
    private long maxFileSize;

    private final ResumeRepository resumeRepository;
    private final FileStorageUtil fileStorageUtil;
    private final PdfExtractorUtil pdfExtractorUtil;
    private final ResumeMapper resumeMapper;

    @Transactional
    @Override
    public ResumeResponse uploadResume(MultipartFile file) {

        log.info("Uploading resume: {}", file.getOriginalFilename());

        validateFile(file);

        StoredFile storedFile = null;

        try {

            storedFile = fileStorageUtil.save(file);

            String extractedText = pdfExtractorUtil.extractText(file);

            // Temporary until JWT propagation from Gateway
            UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");

            Resume resume = Resume.builder()
                    .userId(userId)
                    .originalFileName(file.getOriginalFilename())
                    .storedFileName(storedFile.storedFileName())
                    .filePath(storedFile.filePath())
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .extractedText(extractedText)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Resume savedResume = resumeRepository.save(resume);

            log.info("Resume uploaded successfully with id: {}", savedResume.getId());

            return resumeMapper.toResponse(savedResume);

        } catch (Exception ex) {

            if (storedFile != null) {
                try {
                    fileStorageUtil.delete(storedFile.filePath());
                } catch (IOException ignored) {

                }
            }

            log.error("Failed to upload resume", ex);

            throw new ResumeUploadException("Failed to upload resume", ex);
        }
    }

    private void validateFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new InvalidFileException("File cannot be empty");
        }

        if (!"application/pdf".equals(file.getContentType())) {
            throw new InvalidFileException("Only PDF files are allowed");
        }

        if (file.getSize() > maxFileSize) {
            throw new InvalidFileException("Maximum file size is 5 MB");
        }

    }

    @Override
    public ResumeResponse getResumeById(UUID resumeId) {

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resume not found with id: " + resumeId
                        ));

        return resumeMapper.toResponse(resume);
    }

    @Override
    public List<ResumeResponse> getAllResumes() {

        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        return resumeRepository.findByUserId(userId)
                .stream()
                .map(resumeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteResume(UUID resumeId) {

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resume not found with id: " + resumeId
                        ));

        try {

            fileStorageUtil.delete(resume.getFilePath());

            resumeRepository.delete(resume);

            log.info("Resume deleted successfully: {}", resumeId);

        } catch (IOException ex) {

            log.error("Failed to delete resume", ex);

            throw new ResumeDeletionException("Failed to delete resume", ex);
        }
    }
}