package com.baekopa.backend.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MyInfoReqeustDto {

    private String name;
    private MultipartFile image;

}
