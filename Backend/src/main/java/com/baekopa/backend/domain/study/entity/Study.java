package com.baekopa.backend.domain.study.entity;

import com.baekopa.backend.global.entity.BaseBy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE study SET deleted_at = NOW() WHERE study_id = ?")
public class Study extends BaseBy {

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

    @Column(name = "category", length = 20)
    private String category;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "day")
    private String day; // 비트마스킹 (일월화수목금토 기준)

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Builder
    private Study(StudyType type, String title, String description, String backgroundImage, String category, LocalDate startDate, LocalDate endDate, String day, LocalTime startTime, LocalTime endTime) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.backgroundImage = backgroundImage;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Study of(String title, String description, String backgroundImage, String category, LocalDate startDate, LocalDate endDate, String day, LocalTime startTime, LocalTime endTime) {
        return builder()
                .type(StudyType.GROUP)
                .title(title)
                .description(description)
                .backgroundImage(backgroundImage)
                .category(category)
                .startDate(startDate)
                .endDate(endDate)
                .day(day)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    public void updateStudyBasicInfo(String title, String description, String backgroundImage, String category) {
        this.title = title;
        this.description = description;
        this.backgroundImage = backgroundImage;
        this.category = category;
    }

}
