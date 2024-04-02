package com.baekopa.backend.domain.meeting.entity;

public enum IsolationEnum {
    DONE,// 이미 처리완료된 상태로 접근이 가능함
    USING, //현재 다른 사용자가 사용중이므로 접근이 불가능
    ERROR // 에러로 결과가 출력된 상태(접근 가능)
}
