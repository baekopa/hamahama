package com.baekopa.backend.domain.study.dto.response;

import com.baekopa.backend.domain.study.entity.StudyType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudyListResponseDto {

    private Long id;
    private String title;
    private String backgroundImage;
    private LocalDateTime scheduledTime;
    private String category;
    private StudyType type;

    @Builder
    private StudyListResponseDto(Long id, String title, String backgroundImage, LocalDateTime scheduledTime, String category, StudyType type) {
        this.id = id;
        this.title = title;
        this.backgroundImage = backgroundImage;
        this.scheduledTime = scheduledTime;
        this.category = category;
        this.type = type;
    }

    public static StudyListResponseDto of(Long id, String title, String backgroundImage, LocalDateTime scheduledTime, String category, StudyType type) {
        return builder().id(id)
                .title(title)
                .backgroundImage(backgroundImage)
                .scheduledTime(scheduledTime)
                .category(category)
                .type(type)
                .build();
    }

}
