package com.baekopa.backend.domain.study.entity;

import com.baekopa.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE study SET deleted_at = CONVERT_TZ(NOW(), 'UTC', 'Asia/Seoul') WHERE id = ?")
public class Study extends BaseEntity {

    public enum StudyType {
        GROUP, PERSONAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StudyType type;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "background_image")
    private String backgroundImage;

    @Column(name = "day")
    private String day; // 비트마스킹 (일월화수목금토 기준)

    @Column(name = "start_date")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Builder
    private Study(StudyType type, String title, String description, String backgroundImage, String day, LocalTime startTime, LocalTime endTime) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.backgroundImage = backgroundImage;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Study of(String title, String description, String backgroundImage, String day, LocalTime startTime, LocalTime endTime) {
        return builder()
                .type(StudyType.GROUP)
                .title(title)
                .description(description)
                .backgroundImage(backgroundImage)
                .day(day)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

}
