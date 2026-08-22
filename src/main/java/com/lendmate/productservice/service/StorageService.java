package com.lendmate.productservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    String uploadFile(MultipartFile file);
    List<Object> listFiles();
    String getFileUrl(String fileName);
    void deleteFile(String fileName);
}
