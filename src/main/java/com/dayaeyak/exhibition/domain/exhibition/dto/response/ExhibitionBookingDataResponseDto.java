package com.dayaeyak.exhibition.domain.exhibition.dto.response;

import com.dayaeyak.exhibition.domain.exhibition.enums.Grade;
import com.dayaeyak.exhibition.domain.exhibition.querydsl.dto.response.ExhibitionFindBookingDataProjectionDto;

public record ExhibitionBookingDataResponseDto(
        Integer price,

        Grade grade
) {

    public static ExhibitionBookingDataResponseDto from(ExhibitionFindBookingDataProjectionDto data) {
        return new ExhibitionBookingDataResponseDto(data.price(), data.grade());
    }
}
