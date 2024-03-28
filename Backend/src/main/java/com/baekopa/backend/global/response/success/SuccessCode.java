package com.baekopa.backend.global.response.success;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    /**
     * ******************************* Success CodeList ***************************************
     * common HTTP Status Code
     * 200 : OK                 성공
     * 201 : Created            생성됨
     * 202 : Accepted           허용됨
     * -------------------
     * other HTTP Status Code
     * 204 : No Content         콘텐츠 없음
     * 206 : Partial Content    일부 콘텐츠
     * *********************************************************************************************
     */
    //basic
    INSERT_SUCCESS(201, "삽입 성공"),
    SELECT_SUCCESS(200, "조회 성공"),
    UPDATE_SUCCESS(204, "수정 성공"),
    DELETE_SUCCESS(204, "삭제 성공"),

    /**
     * ******************************* Custom Success CodeList ***************************************
     */
    // Member
    REGISTER_SUCCESS(201, "회원가입에 성공하였습니다."),
    LOGIN_SUCCESS(200, "로그인에 성공하였습니다."),
    LOGOUT_SUCCESS(200, "로그아웃에 성공하였습니다."),
    MEMBER_ID_EXIST(200, "회원 id가 이미 존재합니다."),
    MEMBER_ID_NOT_EXIST(200, "회원 id가 존재하지 않습니다."),
    MEMBER_FIND_SUCCESS(200, "회원정보 조회에 성공하였습니다."),
    MEMBER_UPDATE_SUCCESS(200, "회원정보 수정에 성공하였습니다."),
    MEMBER_DELETE_SUCCESS(200, "회원 탈퇴에 성공하였습니다."),
    CHECK_MEMBER_OF_JWT(200, "JWT토큰의 멤버 정보조회에 성공했습니다."),

    // Study
    STUDY_GET_SUCCESS(200, "스터디 조회에 성공하였습니다."),
    STUDY_MEMBER_GET_SUCCESS(200, "스터디원 조회에 성공하였습니다."),
    STUDY_CREATE_SUCCESS(201, "새로운 스터디 생성에 성공했습니다."),
    STUDY_MEMBER_INVITE_SUCCESS(201, "스터디원 초대에 성공했습니다"),
    STUDY_UPDATE_BASIC_SUCCESS(204, "스터디 기본정보 수정에 성공했습니다"),
    STUDY_MEMBER_JOIN_UPDATE_SUCCESS(204, "스터디 초대 승낙에 성공했습니다."),
    STUDY_MEMBER_JOIN_DELETE_SUCCESS(204, "스터디 초대 거절에 성공했습니다"),
    STUDY_MEMBER_INVITATION_DELETE_SUCCESS(204, "스터디 초대 취소에 성공했습니다"),
    STUDY_LEADER_UPDATE_SUCCESS(204, "스터디장 변경에 성공했습니다"),
    STUDY_MEMBER_DELETE_SELF_SUCCESS(204, "스터디 나가기에 성공했습니다"),
    STUDY_MEMBER_DELETE_SUCCESS(204, "스터디원 강퇴에 성공했습니다"),

    // Meeting
    MEETING_SUMMARY_CREATE_SUCCESS(201, "미팅 요약에 성공했습니다."),
    MEETING_SUMMARY_GET_SUCCESS(200,"미팅 요약 조회에 성공했습니다."),
    MEETING_SUMMARY_UPDATE_SUCCESS(201,"미팅 재요약에 성공했습니다."),
    MEETING_SCRIPT_CREATE_SUCCESS(201, "STT 텍스트 파일 저장에 성공했습니다."),

    // Note
    NOTE_CREATE_SUCCESS(201, "새로운 노트 생성에 성공했습니다."),
    NOTE_GET_SUCCESS(200, "노트 조회에 성공했습니다."),
    NOTE_UPDATE_SUCCESS(204, "노트 수정에 성공했습니다."),
    NOTE_SHARE_SUCCESS(200, "스터디에 노트를 공유하는데 성공했습니다."),
    NOTE_SUMMARY_SUCCESS(201, "새로운 요약 생성에 성공했습니다."),

    // Meeting
    STUDY_MEETING_GET_SUCCESS(200, "스터디 미팅 일정 목록 조회에 성공했습니다."),
    MEETING_GET_SUCCESS(200, "미팅 조회에 성공했습니다."),
    MEETING_CREATE_SUCCESS(201, "새로운 미팅 생성에 성공했습니다."),
    MEETING_REMIND_QUIZ_SUCCESS(201,"새로운 리마인드 퀴즈 생성에 성공했습니다."),

    // My Information
    MY_STUDY_GET_SUCCESS(200, "내가 속한 스터디 조회에 성공했습니다."),
    ; // End

    /**
     * ******************************* Success Code Field ***************************************
     */
    // 성공 코드의 '코드 상태'를 반환한다.
    private final int status;

    // 성공 코드의 '코드 메시지'를 반환한다.
    private final String message;
}