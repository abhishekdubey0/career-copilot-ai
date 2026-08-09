package com.careercopilot.resume.util;

import com.careercopilot.resume.dto.response.StoredFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileStorageUtil {

    @Value("${resume.upload.directory}")
    private String uploadDirectory;


    public StoredFile save(MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();

        String extension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String storedFileName = UUID.randomUUID() + extension;

        Path destination = uploadPath.resolve(storedFileName);

        log.info("Saving file: {}", storedFileName);

        Files.copy(
                file.getInputStream(),
                destination,
                StandardCopyOption.REPLACE_EXISTING
        );

        return new StoredFile(
                storedFileName,
                destination.toString()
        );
    }

    public void delete(String filePath) throws IOException {

        Path path = Paths.get(filePath);

        log.info("Deleting file: {}", filePath);

        Files.deleteIfExists(path);

    }
}