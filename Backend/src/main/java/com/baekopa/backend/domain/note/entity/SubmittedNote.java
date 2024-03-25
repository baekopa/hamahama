package com.baekopa.backend.domain.note.entity;

import com.baekopa.backend.domain.meeting.entity.Meeting;
import com.baekopa.backend.global.entity.BaseBy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@SQLDelete(sql = "UPDATE submittednote SET deleted_at = NOW() WHERE submitted_note_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubmittedNote extends BaseBy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submitted_note_id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "difference_content")
    private String differenceContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    private Note note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Builder
    private SubmittedNote(String content, String differenceContent, Note note, Meeting meeting) {
        this.content = content;
        this.differenceContent = differenceContent;
        this.note = note;
        this.meeting = meeting;
    }

    // 연관관계 편의 메서드
    public static SubmittedNote createSubmittedNote(String content, String differenceContent, Note note, Meeting meeting) {
        return builder().content(content)
                .differenceContent(differenceContent)
                .note(note)
                .meeting(meeting)
                .build();
    }

}
