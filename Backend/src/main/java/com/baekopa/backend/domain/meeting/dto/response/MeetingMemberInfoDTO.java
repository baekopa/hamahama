package com.baekopa.backend.domain.meeting.dto.response;

import com.baekopa.backend.domain.member.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingMemberInfoDTO {
    private Long memberId;
    private String name;
    private String profile_image;

    @Builder
    private MeetingMemberInfoDTO(Long memberId, String name, String profile_image) {
        this.memberId = memberId;
        this.name = name;
        this.profile_image = profile_image;
    }

    public static MeetingMemberInfoDTO from(Member member) {
        return builder()
                .memberId(member.getId())
                .profile_image(member.getImage())
                .name(member.getName())
                .build();
    }
}
