package com.baekopa.backend.global.service;


import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 파일 업로드
    public String saveFile(String folder, MultipartFile multipartFile) throws IOException {
        String fileName = setFileName(folder, multipartFile.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    // 파일 이름 설정
    private String setFileName(String folder, String originalFilename) {

        String extension = StringUtils.getFilenameExtension(originalFilename);
        String baseName = StringUtils.stripFilenameExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();

        return folder + "/" + baseName + "_" + uuid + "." + extension;
    }

    // 파일 삭제
    public void deleteFile(String originalFilename) {
        try {
            amazonS3.deleteObject(bucket, originalFilename.split("/", 4)[3]);
        } catch (SdkClientException e) {
            log.error("SdkClientException", e);
        }
    }

    // 파일 다운로드
    public ResponseEntity<UrlResource> downloadFile(String originalFilename) {
        UrlResource urlResource = new UrlResource(amazonS3.getUrl(bucket, originalFilename));

        String contentDisposition = "attachment; filename=\"" + originalFilename + "\"";

        // header에 CONTENT_DISPOSITION 설정하여 클릭 시 다운로드.
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
