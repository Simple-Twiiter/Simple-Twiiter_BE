package com.example.simpletwiter_be.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.simpletwiter_be.configuration.AWSConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ImageUploadService.class, AmazonS3Client.class})
class ImageUploadServiceTest {
    @Autowired
    private ImageUploadService imageUploadService;

    @Test
    void uploadImage() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "test.jpg",
                "image/jpeg",
                new FileInputStream("/Users/mkkim/Downloads/GAw5c99f58892eb6.jpg"));

        String imgUrl = imageUploadService.uploadImage(multipartFile);
        assertNotNull(imgUrl);
    }
}