package com.dayaeyak.exhibition.domain.exhibition.dto.response;

import com.dayaeyak.exhibition.domain.exhibition.Exhibition;

public record ExhibitionUpdateActivationResponseDto(
        Long exhibitionId,

        Boolean isActivated
) {

    public static ExhibitionUpdateActivationResponseDto from(Exhibition exhibition) {
        return new ExhibitionUpdateActivationResponseDto(exhibition.getId(), exhibition.getIsActivated());
    }
}
