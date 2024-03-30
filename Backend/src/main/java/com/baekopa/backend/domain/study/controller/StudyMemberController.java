package com.baekopa.backend.domain.study.controller;

import com.baekopa.backend.domain.member.dto.response.MemberResponseDto;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.study.dto.StudyMemberDto;
import com.baekopa.backend.domain.study.service.StudyMemberService;
import com.baekopa.backend.global.response.success.ApiResponse;
import com.baekopa.backend.global.response.success.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudyMemberController {

    private final StudyMemberService studyMemberService;

    @Operation(summary = "이메일로 멤버(사용자) 조회", description = "이메일에 쿼리 내용이 포함되는 멤버(사용자)를 조회합니다.")
    @GetMapping("/members")
    public ApiResponse<List<MemberResponseDto>> searchMembers(@RequestParam(value = "q") String query, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.SELECT_SUCCESS, studyMemberService.searchMembers(query));
    }

    @Operation(summary = "스터디원 조회", description = "스터디에 속한 멤버를 조회합니다.")
    @GetMapping("/studies/{study-id}/members")
    public ApiResponse<List<StudyMemberDto>> getStudyMembers(@PathVariable(value = "study-id") Long studyId, @AuthenticationPrincipal Member member) {
        return ApiResponse.of(SuccessCode.STUDY_MEMBER_GET_SUCCESS, studyMemberService.getStudyMembers(studyId));
    }

    @Operation(summary = "스터디원 초대", description = "새로운 스터디원을 초대 요청을 보냅니다. (memberId만 필수)")
    @PostMapping("/studies/{study-id}/members")
    public ApiResponse<Void> inviteStudyMember(@PathVariable(value = "study-id") Long studyId, @RequestBody StudyMemberDto studyMember, @AuthenticationPrincipal Member member) {
        studyMemberService.inviteStudyMember(studyId, studyMember.getMemberId());

        return ApiResponse.of(SuccessCode.STUDY_MEMBER_INVITE_SUCCESS);
    }

    @Operation(summary = "스터디 초대 승낙", description = "초대 승낙으로 새로운 스터디에 참여합니다.")
    @PutMapping("/members/me/invitations/{invitation-id}")
    public ApiResponse<Void> joinStudy(@PathVariable(value = "invitation-id") Long invitationId, @AuthenticationPrincipal Member member) {

        studyMemberService.joinStudy(invitationId, member);

        return ApiResponse.of(SuccessCode.STUDY_MEMBER_JOIN_UPDATE_SUCCESS);
    }

    @Operation(summary = "스터디 초대 거절", description = "현재 스터디에 대한 초대를 거절합니다.")
    @DeleteMapping("/members/me/invitations/{invitation-id}")
    public ApiResponse<Void> rejectStudyInvitation(@PathVariable(value = "invitation-id") Long invitationId, @AuthenticationPrincipal Member member) {

        studyMemberService.rejectStudyInvitation(invitationId, member);

        return ApiResponse.of(SuccessCode.STUDY_MEMBER_JOIN_DELETE_SUCCESS);
    }

    @Operation(summary = "스터디 초대하기 취소", description = "초대 요청을 취소합니다.")
    @DeleteMapping("/studies/{study-id}/invitations/{invitation-id}")
    public ApiResponse<Void> deleteStudyInvitation(@PathVariable(value = "study-id") Long studyId, @PathVariable(value = "invitation-id") Long invitationId, @AuthenticationPrincipal Member member) {

        studyMemberService.deleteStudyInvitation(studyId, invitationId, member);

        return ApiResponse.of(SuccessCode.STUDY_MEMBER_INVITATION_DELETE_SUCCESS);
    }

    @Operation(summary = "스터디장 변경", description = "스터디장의 권한을 위임합니다. (memberId만 필수)")
    @PutMapping("/studies/{study-id}/leader")
    public ApiResponse<Void> updateStudyLeader(@PathVariable(value = "study-id") Long studyId, @RequestBody StudyMemberDto studyMember, @AuthenticationPrincipal Member member) {

        studyMemberService.updateStudyLeader(studyId, studyMember, member);

        return ApiResponse.of(SuccessCode.STUDY_LEADER_UPDATE_SUCCESS);
    }

    @Operation(summary = "스터디 나가기", description = "스터디에서 나가기")
    @DeleteMapping("/studies/{study-id}/members/me")
    public ApiResponse<Void> deleteStudyMember(@PathVariable(value = "study-id") Long studyId, @AuthenticationPrincipal Member member) {

        studyMemberService.deleteStudyMember(studyId, member);

        return ApiResponse.of(SuccessCode.STUDY_MEMBER_DELETE_SELF_SUCCESS);
    }

    @Operation(summary = "스터디에서 강퇴하기", description = "스터디에서 강퇴하기")
    @DeleteMapping("/studies/{study-id}/members/{member-id}")
    public ApiResponse<Void> deleteStudyMember(@PathVariable(value = "study-id") Long studyId, @PathVariable(value = "member-id") Long memberId, @AuthenticationPrincipal Member member) {

        studyMemberService.deleteStudyMember(studyId, memberId, member);

        return ApiResponse.of(SuccessCode.STUDY_MEMBER_DELETE_SUCCESS);
    }

}
