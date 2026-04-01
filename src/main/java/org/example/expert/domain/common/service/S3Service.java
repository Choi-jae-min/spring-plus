package org.example.expert.domain.common.service;


import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;


    public String uploadImage(MultipartFile file, String folder) {
        String fileKey = folder + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            s3Template.upload(bucketName, fileKey, file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("S3 업로드 실패", e);
        }
        return fileKey;
    }

    public URL getImageUrl(String key) {
        return s3Template.createSignedGetURL(bucketName, key, Duration.ofDays(7));
    }

}