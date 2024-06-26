package com.baekopa.backend.global.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * ******************************* Error CodeList ***************************************
     * HTTP Status Code
     * 400 : Bad Request
     * 401 : Unauthorized
     * 403 : Forbidden
     * 404 : Not Found
     * 500 : Internal Server Error
     * ******************************* Global Error CodeList ***************************************
     */
    MISSING_REQUEST_PARAMETER_ERROR(400, "Request Parameter로 데이터가 전달되지 않았습니다."),
    REQUEST_BODY_MISSING_ERROR(400, "@RequestBody 데이터가 누락되었습니다."),
    NOT_VALID_HEADER_ERROR(400, "Header에 데이터가 존재하지 않습니다."),
    JACKSON_PROCESS_ERROR(400, "Jackson 처리 오류 발생했습니다."),
    INVALID_TYPE_VALUE(400, "유효하지 않은 데이터 타입입니다."),
    BAD_REQUEST_ERROR(400, "잘못된 서버 요청입니다."),
    JSON_PARSE_ERROR(400, "JSON 파싱 오류 발생했습니다."),
    NOT_VALID_ERROR(400, "유효하지 않은 요청 데이터입니다."),
    IO_ERROR(400, "입출력 오류가 발생했습니다."),
    FORBIDDEN_ERROR(403, "권한이 없습니다."),
    NOT_FOUND_ERROR(404, "요청한 리소스를 찾을 수 없습니다."),
    NULL_POINT_ERROR(500, "NULL 포인터 예외가 발생했습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다."),
    /**
     * *********************************************************************************************
     */
    // basic
    INSERT_ERROR(200, "삽입 실패"),
    UPDATE_ERROR(200, "수정 실패"),
    DELETE_ERROR(200, "삭제 실패"),

    /**
     * ******************************* Custom Error CodeList ***************************************
     */

    // Member
    FAIL_TO_OAUTH_LOGIN(400, "소셜 로그인에 실패했습니다."),
    MEMBER_ID_NOT_EXIST(400, "회원 ID가 존재하지 않습니다."),
    MEMBER_ID_ALREADY_EXIST(400, "회원 ID가 이미 존재합니다."),

    // JWT
    REFRESH_INVALID(400, "리프레시 토큰이 유효하지 않습니다."),
    TOKEN_ALIVE(400, "유효기간이 만료되지 않은 토큰입니다."),
    JWT_BADTYPE(401, "Bearer 타입 토큰이 아닙니다."),
    JWT_MALFORM(401, "토큰 값이 올바르지 않습니다."),
    BLACK_TOKEN(401, "접근이 차단된 토큰입니다."),
    JWT_INVALID(401, "유효하지 않은 토큰입니다."),
    JWT_EXPIRED(403, "만료된 토큰입니다."),

    // Study
    STUDY_NOT_EXIST(400, "스터디가 존재하지 않습니다."),
    STUDY_MEMBER_NOT_EXIST(400, "스터디원에 대한 정보가 올바르지 않습니다."),
    STUDY_MEMBER_FORBIDDEN_ERROR(403, "권한이 없습니다"),

    // Note
    NOTE_NOT_FOUND(400, "존재하지 않거나 이미 삭제된 노트입니다."),
    NOTE_DUPLICATE_MEETING(400, "이미 내보내기 된 미팅입니다."),

    // Meeting
    MEETING_NOT_FOUND(400, "미팅이 존재하지 않습니다."),
    MEETING_SUMMARY_NOT_FOUND(400,"미팅 요약이 존재하지 않습니다."),
    MEETING_REMIND_QUIZ_NOT_FOUND(400, "리마인드 퀴즈가 존재하지 않습니다."),
    MEETING_SCRIPT_NOT_FOUND(400,"미팅 스크립트가 존재하지 않습니다"),
    MEETING_KEYWORD_NOT_FOUND(400, "미팅 키워드가 존재하지 않습니다"),
    MEETING_SUMMARY_CAN_NOT_ACCESS(403, "미팅 요약을 수정중이므로 접근이 불가능 합니다."),
    REMIND_QUIZ_CAN_CNT_ACCESS(403, "리마인드 퀴즈를 수정중이므로 접근이 불가능 합니다."),
    MEETING_KEYWORD_CAN_NOT_ACCESS(403, "키워드를 수정중 및 사용중이므로 접근이 불가능합니다."),

    // Notification
    NOTIFICATION_NOT_FOUND(400, "알림이 존재하지 않습니다."),
    NOTIFICATION_NOT_SEND(400, "알림 전송에 실패했습니다."); // End

    /**
     * ******************************* Error Code Field ***************************************
     */
    // 에러 코드의 '코드 상태'을 반환한다.
    private final int status;

    // 에러 코드의 '코드 메시지'을 반환한다.
    private final String message;
}