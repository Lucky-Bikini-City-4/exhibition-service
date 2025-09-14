package com.dayaeyak.exhibition.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    MASTER("MASTER"),
    SELLER("SELLER"),
    NORMAL("NORMAL"),
    ;

    private final String role;

    @JsonCreator
    public static UserRole of(String role) {
        return Stream.of(UserRole.values())
                .filter(userRole -> userRole.role.equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
