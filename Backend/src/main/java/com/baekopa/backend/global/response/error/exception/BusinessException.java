package com.baekopa.backend.global.response.error.exception;

import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.ErrorResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 에러를 사용하기 위한 구현체
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private List<ErrorResponse.FieldError> errors = new ArrayList<>();

    @Builder
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public BusinessException(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
        super(errorCode.getMessage());
        this.errors = errors;
        this.errorCode = errorCode;
    }
}