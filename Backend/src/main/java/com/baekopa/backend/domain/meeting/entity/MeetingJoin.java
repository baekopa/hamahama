package com.baekopa.backend.domain.meeting.entity;

import com.baekopa.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE meeting_join SET deleted_at = NOW() WHERE meeting_join_id = ?")
public class MeetingJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_join_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Builder
    private MeetingJoin(Long id, Member member, Meeting meeting) {
        this.id = id;
        this.member = member;
        this.meeting = meeting;
    }

    public static MeetingJoin of(Member member, Meeting meeting) {
        return builder()
                .meeting(meeting)
                .member(member)
                .build();
    }
}
