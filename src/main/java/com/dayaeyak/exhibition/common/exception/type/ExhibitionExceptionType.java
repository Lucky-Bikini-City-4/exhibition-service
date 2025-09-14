package com.dayaeyak.exhibition.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExhibitionExceptionType implements ExceptionType{

    EXHIBITION_NOT_FOUND(HttpStatus.NOT_FOUND, "전시회 정보를 찾지 못했습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "전시회 접근 권한이 없습니다."),
    ALREADY_EXHIBITION_TICKET_OPENED(HttpStatus.CONFLICT, "이미 티켓이 판매 진행중인 전시회입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
