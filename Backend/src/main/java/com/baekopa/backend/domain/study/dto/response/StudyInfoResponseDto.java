package com.baekopa.backend.domain.study.dto.response;

import com.baekopa.backend.domain.study.dto.StudyMemberDto;
import com.baekopa.backend.domain.study.entity.Study;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyInfoResponseDto {

    private Long id;
    private String type;
    private String title;
    private String description;
    private String backgroundImage;
    private String category;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String studyDay;
    private List<StudyMemberDto> members;

    // 스터디원
    // 스터디 일정

    @Builder
    private StudyInfoResponseDto(Long id, String type, String title, String description, String backgroundImage, String category, LocalDate startDate, LocalDate endDate, String studyDay, List<StudyMemberDto> members) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.backgroundImage = backgroundImage;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.studyDay = studyDay;
        this.members = members;
    }

    public static StudyInfoResponseDto of(Study study, List<StudyMemberDto> members) {

        return builder()
                .id(study.getId())
                .type(study.getType().getTypeKor())
                .title(study.getTitle())
                .description(study.getDescription())
                .backgroundImage(study.getBackgroundImage())
                .category(study.getCategory())
                .startDate(study.getStartDate())
                .endDate(study.getEndDate())
                .studyDay(study.getDay())
                .members(members)
                .build();
    }

    public static StudyInfoResponseDto from(Study study) {

        return builder()
                .id(study.getId())
                .type(study.getType().getTypeKor())
                .title(study.getTitle())
                .description(study.getDescription())
                .backgroundImage(study.getBackgroundImage())
                .category(study.getCategory())
                .startDate(study.getStartDate())
                .endDate(study.getEndDate())
                .studyDay(study.getDay())
                .build();
    }

}
