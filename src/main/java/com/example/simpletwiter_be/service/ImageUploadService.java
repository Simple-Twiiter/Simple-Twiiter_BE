package com.example.simpletwiter_be.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String S3Bucket; // Bucket 이름
    private static final String FILE_EXTENSION_SEPARATOR = ".";

    private final AmazonS3Client amazonS3Client;


    public String uploadImage(MultipartFile multipartFile) throws Exception {

        String originalName = multipartFile.getOriginalFilename(); // 파일 이름
        String saveName = buildFileName(originalName);
        long size = multipartFile.getSize(); // 파일 크기

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(multipartFile.getContentType());
        objectMetaData.setContentLength(size);



        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(S3Bucket, saveName, multipartFile.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3Client.getUrl(S3Bucket, saveName).toString();
    }

    private static String buildFileName(String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return fileName + "@" + now + fileExtension;
    }
}
