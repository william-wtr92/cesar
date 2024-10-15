package com.example.cesar.service.impl;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobClient;
import com.example.cesar.service.AzureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AzureServiceImpl implements AzureService {
    private final BlobContainerClient blobContainerClient;

    public AzureServiceImpl(BlobContainerClient blobContainerClient) {
        this.blobContainerClient = blobContainerClient;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        BlobClient blobClient = blobContainerClient.getBlobClient(file.getOriginalFilename());

        blobClient.upload(file.getInputStream(), file.getSize(), true);

        // Return the name of the file uploaded and not full link of storage location
        return blobClient.getBlobUrl().split("/")[blobClient.getBlobUrl().split("/").length - 1];
    }


}
