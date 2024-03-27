package com.baekopa.backend.domain.note.entity;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@SQLDelete(sql = "UPDATE submitted_note_summary SET deleted_at = NOW() WHERE submitted_note_summary_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubmittedNoteSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submitted_note_summary_id")
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "tail_question", columnDefinition = "TEXT")
    private String tailQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Builder
    private SubmittedNoteSummary(String content, String tailQuestion, Meeting meeting) {
        this.content = content;
        this.tailQuestion = tailQuestion;
        this.meeting = meeting;
    }

    public static SubmittedNoteSummary of(String content, String tailQuestion, Meeting meeting) {
        return builder()
                .content(content)
                .tailQuestion(tailQuestion)
                .meeting(meeting)
                .build();
    }

}
