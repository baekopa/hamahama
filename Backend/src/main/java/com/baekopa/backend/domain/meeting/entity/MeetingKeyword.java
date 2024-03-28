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
@SQLDelete(sql = "UPDATE meeting_keyword SET delete_at = NOW() WHERE group_keyword_id = ?")
public class MeetingKeyword extends BaseBy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_keyword_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Column(name = "keyword")
    private String keyword;

    @Builder
    public MeetingKeyword(Meeting meeting, String keyword) {
        this.meeting = meeting;
        this.keyword = keyword;
    }

    private static MeetingKeyword of(Meeting meeting, String keyword){
        return builder().meeting(meeting)
                .keyword(keyword)
                .build();
    }
}
