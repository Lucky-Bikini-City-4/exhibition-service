package com.dayaeyak.exhibition.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ArtistExceptionType implements ExceptionType{

    ALREADY_REGISTERED_ARTIST(HttpStatus.CONFLICT, "이미 등록된 아티스트입니다."),
    ARTIST_NOT_FOUND(HttpStatus.NOT_FOUND, "아티스트 정보를 찾지 못했습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
