package com.dayaeyak.exhibition.domain.exhibition.dto.request;

import com.dayaeyak.exhibition.common.constraints.ExhibitionValidationMessage;
import com.dayaeyak.exhibition.domain.exhibition.enums.Grade;
import com.dayaeyak.exhibition.domain.exhibition.enums.Region;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record ExhibitionUpdateRequestDto(
        String name,

        Integer price,

        String place,

        String address,

        Region region,

        Grade grade,

        LocalDate startDate,

        LocalDate endDate,

        LocalTime startTime,

        LocalTime endTime,

        LocalDateTime ticketOpenedAt,

        LocalDateTime ticketClosedAt,

        List<ExhibitionUpdateArtistRequestDto> artists
) {
}
