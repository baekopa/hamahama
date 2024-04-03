package com.baekopa.backend.domain.meeting.entity;

import com.baekopa.backend.domain.note.entity.SubmittedNote;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.global.entity.BaseBy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SQLDelete(sql = "UPDATE meeting SET deleted_at = NOW() WHERE meeting_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting extends BaseBy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "study_at")
    private LocalDateTime studyAt;

    @Column(name = "record_file")
    private String recordFile;

    @Column(name = "note_summary", columnDefinition = "TEXT")
    private String noteSummary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @OneToMany(mappedBy = "meeting")
    private List<SubmittedNote> submittedNotes = new ArrayList<>();

    @OneToOne(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private RemindQuiz remindQuiz;

    @OneToOne(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private MeetingScript meetingScript;

    @OneToOne(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private MeetingSummary meetingSummary;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeetingKeyword> meetingKeyword = new ArrayList<>();

    @Builder
    private Meeting(String topic, LocalDateTime studyAt, String recordFile, Study study) {
        this.topic = topic;
        this.studyAt = studyAt;
        this.recordFile = recordFile;
        this.study = study;
    }

    public static Meeting of(String topic, LocalDateTime studyAt, Study study) {
        return builder()
                .topic(topic)
                .studyAt(studyAt)
                .study(study)
                .build();
    }

    public void updateTopic(String topic) {
        this.topic = topic;
    }

    public void updateStudyAt(LocalDateTime studyAt) {
        this.studyAt = studyAt;
    }

    public void updateRecordFile(String recordFile) {
        this.recordFile = recordFile;
    }

    public void updateNoteSummary(String noteSummary) {
        this.noteSummary = noteSummary;
    }

}
