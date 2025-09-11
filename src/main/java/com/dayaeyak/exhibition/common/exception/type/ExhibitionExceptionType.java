package com.dayaeyak.exhibition.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExhibitionExceptionType implements ExceptionType{

    EXHIBITION_NOT_FOUND(HttpStatus.NOT_FOUND, "전시회 정보를 찾지 못했습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
