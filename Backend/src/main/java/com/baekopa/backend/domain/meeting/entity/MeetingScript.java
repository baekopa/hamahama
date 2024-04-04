package com.baekopa.backend.domain.meeting.entity;

import com.baekopa.backend.global.entity.BaseBy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingScript extends BaseBy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_script_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Column(name = "script_content", columnDefinition = "TEXT")
    @Lob
    private String scriptContent;

    @Builder
    private MeetingScript(Meeting meeting, String scriptContent) {
        this.meeting = meeting;
        this.scriptContent = scriptContent;
    }

    public static MeetingScript of(Meeting meeting, String scriptContent) {
        return builder()
                .meeting(meeting)
                .scriptContent(scriptContent)
                .build();
    }

    public void updateMeetingScript(String scriptContent) {
        this.scriptContent = scriptContent;
    }


}
