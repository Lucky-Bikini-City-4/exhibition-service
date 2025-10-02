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
    INVALID_SEARCH_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 검색어 타입입니다."),
    END_DATE_BEFORE_START_DATE(HttpStatus.BAD_REQUEST, "시작 날짜가 종료 날짜보다 더 늦을 수 없습니다."),
    END_TIME_BEFORE_START_TIME(HttpStatus.BAD_REQUEST, "시작 시간이 종료 시간보다 더 늦을 수 없습니다."),
    TICKET_CLOSE_DATE_TIME_BEFORE_OPEN_DATE_TIME(HttpStatus.BAD_REQUEST, "티켓 판매 오픈 날짜가 종료 날짜보다 더 늦을 수 없습니다."),
    DATE_RANGE_SEARCH_NEEDS_START_DATE(HttpStatus.BAD_REQUEST, "날짜 범위로 조회하기 위해서 시작 날짜가 필요합니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
