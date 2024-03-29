package com.baekopa.backend.domain.meeting.entity;

import com.baekopa.backend.global.entity.BaseBy;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingSummary extends BaseBy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_summary_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Lob
    @Column(name = "summary_content", columnDefinition = "TEXT")
    private String summaryContent;

    @Builder
    public MeetingSummary(Meeting meeting, String summaryContent) {
        this.meeting = meeting;
        this.summaryContent = summaryContent;
    }

    public static MeetingSummary of(Meeting meeting, String summaryContent) {
        return builder().meeting(meeting)
                .summaryContent(summaryContent)
                .build();
    }
    public void updateMeetingSummary(String summaryContent) {
        this.summaryContent = summaryContent;
    }
}
