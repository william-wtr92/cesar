package com.example.cesar.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AzureService {
    String uploadFile(MultipartFile file)  throws IOException;
}
