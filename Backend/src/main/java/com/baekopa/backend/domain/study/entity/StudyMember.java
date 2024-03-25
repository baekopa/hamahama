package com.baekopa.backend.domain.study.entity;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.global.entity.BaseBy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE study_member SET deleted_at = NOW() WHERE study_member_id = ?")
public class StudyMember extends BaseBy {

    public enum StudyMemberType {
        // 스터디장 / 스터디 멤버 / 초대한 상태
        STUDY_LEADER, STUDY_MEMBER, INVITATION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    StudyMemberType type;

    @Builder
    private StudyMember(Member member, Study study, StudyMemberType type) {
        this.member = member;
        this.study = study;
        this.type = type;
    }

    // 연관관계 편의 메서드
    public static StudyMember createStudyMember(Study study, Member member, StudyMemberType type) {
        return builder().member(member)
                .study(study)
                .type(type)
                .build();
    }

    // 스터디원 타입 수정
    public void updateStudyMemberType(StudyMemberType type) {

        this.type = type;
    }

}
