package com.dayaeyak.exhibition.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExhibitionExceptionType implements ExceptionType{

    ;

    private final HttpStatus status;
    private final String message;
}
