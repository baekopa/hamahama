package com.baekopa.backend.domain.study.dto;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.study.entity.StudyMember.StudyMemberType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyMemberDto {

    private Long memberId;
    private String name;
    private String email;
    private String image;
    private StudyMemberType type;

    @Builder
    private StudyMemberDto(Long memberId, String name, String email, String image, StudyMemberType type) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.image = image;
        this.type = type;
    }

    public static StudyMemberDto of(Member member, StudyMemberType type) {
        return builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .image(member.getImage())
                .type(type)
                .build();
    }

}
