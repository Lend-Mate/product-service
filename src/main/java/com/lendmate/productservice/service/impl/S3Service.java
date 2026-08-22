package com.lendmate.productservice.service.impl;

import com.lendmate.productservice.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class S3Service implements StorageService {
    private S3Client s3Client;
    private final String bucketName = "lend-mate-bucket";

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String uniqueFileName = generateUniqueFileName(fileName);
            log.info("Uploading file: {}", uniqueFileName);
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(uniqueFileName)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes()));
            log.info("File uploaded successfully: {}", uniqueFileName);
            return uniqueFileName;
        } catch (IOException e) {
            log.error("Failed to upload file", e);
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public List<Object> listFiles() {
        log.info("Fetching file list from bucket: {}", bucketName);
        ListObjectsV2Response listObjects = s3Client.listObjectsV2(ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build());
        List<Object> fileList = listObjects.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
        log.info("Files retrieved: {}", fileList);
        return fileList;
    }

    @Override
    public String getFileUrl(String fileName) {
        log.info("Generating URL for file: {}", fileName);
        String url = s3Client.utilities().getUrl(GetUrlRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build()).toString();
        log.info("Generated URL: {}", url);
        return url;
    }

    @Override
    public void deleteFile(String fileName) {
        log.info("Deleting file: {} from bucket: {}", fileName, bucketName);
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build());
        log.info("File deleted successfully: {}", fileName);
    }

    private String generateUniqueFileName(String originalName){
        String extension = (originalName != null && originalName.contains("."))
                ? originalName.substring(originalName.lastIndexOf("."))
                : "";
        return UUID.randomUUID() + extension;
    }
}
