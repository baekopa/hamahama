package com.baekopa.backend.domain.study.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CreateStudyRequestDto {

    private String title;

    private String description;

    private MultipartFile backgroundImage;

    private String category;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDate startDate;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDate endDate;

    private String day;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
}
