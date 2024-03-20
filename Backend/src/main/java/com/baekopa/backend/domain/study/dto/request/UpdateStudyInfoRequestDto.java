package com.baekopa.backend.domain.study.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateStudyInfoRequestDto {

    private String title;
    private String description;
    private MultipartFile backgroundImage;
    private String category;
}
