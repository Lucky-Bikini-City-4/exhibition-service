package com.dayaeyak.exhibition.common.dto;

import com.dayaeyak.exhibition.common.enums.UserRole;

public record Passport(
        Long userId,

        UserRole role
) {
}
