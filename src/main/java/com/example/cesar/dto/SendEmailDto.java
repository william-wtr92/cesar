package com.example.cesar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailDto {
    private String subject;
    private String sender;
    private String body;
    private List<String> recipients;
    private List<MultipartFile> attachments;
}
