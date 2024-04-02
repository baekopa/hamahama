package com.baekopa.backend.domain.meeting.entity;

import com.baekopa.backend.global.entity.BaseBy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE meeting_summary SET deleted_at = CURRENT_TIMESTAMP WHERE meeting_summary_id = ?")
public class MeetingSummary extends BaseBy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_summary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Column(name = "summary_content")
    private String summaryContent;


    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private IsolationEnum status;

    @Builder
    public MeetingSummary(Meeting meeting, String summaryContent, IsolationEnum status) {
        this.meeting = meeting;
        this.summaryContent = summaryContent;
        this.status = status;
    }

    public static MeetingSummary of(Meeting meeting) {
        return builder()
                .meeting(meeting)
                .status(IsolationEnum.USING)
                .build();
    }

    public void updateMeetingSummary(String summaryContent) {

        this.summaryContent = summaryContent;
        this.status=IsolationEnum.DONE;
    }

    public void setUpdateStatus(IsolationEnum status){
        this.status=status;
    }
}
