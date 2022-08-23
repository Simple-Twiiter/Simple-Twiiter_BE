package com.example.simpletwiter_be.service;

import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ImageUploadServiceTest {
    @InjectMocks
    private ImageUploadService imageUploadService;

//    private AmazonS3Client amazonS3Client;


    @Test
    void uploadImage() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "test.jpg",
                "image/jpeg",
                new FileInputStream("/Users/mkkim/Downloads/GAw5c99f58892eb6.jpg"));

        String imgUrl = imageUploadService.uploadImage(multipartFile);
    }
}