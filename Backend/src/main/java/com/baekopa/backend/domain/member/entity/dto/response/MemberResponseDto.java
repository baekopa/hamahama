package com.baekopa.backend.domain.member.entity.dto.response;

import com.baekopa.backend.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private Long memberId;
    private String name;
    private String email;
    private String profileImage;

    @Builder
    private MemberResponseDto(Long memberId, String name, String email, String profileImage) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
    }

    public static MemberResponseDto from(Member member) {

        return builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .profileImage(member.getImage())
                .build();
    }

}
