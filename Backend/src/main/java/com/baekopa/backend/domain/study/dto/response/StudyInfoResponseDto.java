package com.baekopa.backend.domain.study.dto.response;

import com.baekopa.backend.domain.study.dto.StudyMemberDto;
import com.baekopa.backend.domain.study.entity.Study;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StudyInfoResponseDto {

    private String type;
    private String title;
    private String description;
    private String backgroundImage;
    private String category;
    private List<StudyMemberDto> members;

    // 스터디원
    // 스터디 일정

    @Builder
    private StudyInfoResponseDto(String type, String title, String description, String backgroundImage, String category, List<StudyMemberDto> members) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.backgroundImage = backgroundImage;
        this.category = category;
        this.members = members;
    }

    public static StudyInfoResponseDto of(Study study, List<StudyMemberDto> members) {

        return builder()
                .type(study.getType().getTypeKor())
                .title(study.getTitle())
                .description(study.getDescription())
                .backgroundImage(study.getBackgroundImage())
                .category(study.getCategory())
                .members(members)
                .build();
    }

    public static StudyInfoResponseDto from(Study study) {

        return builder()
                .type(study.getType().getTypeKor())
                .title(study.getTitle())
                .description(study.getDescription())
                .backgroundImage(study.getBackgroundImage())
                .category(study.getCategory())
                .build();
    }

}
