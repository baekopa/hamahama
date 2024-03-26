package com.baekopa.backend.domain.meeting.entity;

import com.baekopa.backend.domain.study.entity.Study;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;

@Entity
@Getter
@SQLDelete(sql = "UPDATE meeting SET deleted_at = NOW() WHERE meeting_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "study_at")
    private LocalDate studyAt;

    @Column(name = "record_file")
    private String recordFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @Builder
    private Meeting(String topic, LocalDate studyAt, String recordFile, Study study) {
        this.topic = topic;
        this.studyAt = studyAt;
        this.recordFile = recordFile;
        this.study = study;
    }

    public static Meeting of(String topic, LocalDate studyAt, String recordFile, Study study) {
        return builder()
                .topic(topic)
                .studyAt(studyAt)
                .recordFile(recordFile)
                .study(study)
                .build();
    }

}
