package com.baekopa.backend.domain.meeting.entity;

import com.baekopa.backend.domain.meeting.dto.RemindQuizDTO;
import com.baekopa.backend.global.entity.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemindQuiz extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_quiz_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Column(name = "open_date")
    private LocalDateTime openDate;

    @Column(name = "content")
    private String content;

    @Builder
    private RemindQuiz(Meeting meeting, LocalDateTime openDate, String content) {
        this.meeting = meeting;
        this.openDate = openDate;
        this.content = content;
    }

    public static RemindQuiz of(Meeting meeting, LocalDateTime openDate, String content){
        return builder().meeting(meeting)
                .openDate(openDate)
                .content(content)
                .build();
    }

    public static RemindQuiz from(Meeting meeting, RemindQuizDTO remindQuizDTO){
        return builder()
                .meeting(meeting)
                .openDate(remindQuizDTO.getOpenDate())
                .content(remindQuizDTO.getQuiz())
                .build();
    }

    public void updateRemindQuiz(String content){
        this.content=content;
    }
}
