package com.dayaeyak.exhibition.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonExceptionType implements ExceptionType {

    REQUEST_ACCESS_DENIED(HttpStatus.FORBIDDEN, "요청 접근 권한이 부족합니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
