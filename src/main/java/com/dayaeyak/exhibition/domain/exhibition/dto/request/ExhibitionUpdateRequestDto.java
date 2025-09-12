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
        @NotBlank(message = ExhibitionValidationMessage.INVALID_NAME)
        String name,

        Integer price,

        @NotBlank(message = ExhibitionValidationMessage.INVALID_PLACE)
        String place,

        @NotBlank(message = ExhibitionValidationMessage.INVALID_ADDRESS)
        String address,

        @NotNull(message = ExhibitionValidationMessage.INVALID_REGION)
        Region region,

        @NotNull(message = ExhibitionValidationMessage.INVALID_GRADE)
        Grade grade,

        @NotNull(message = ExhibitionValidationMessage.INVALID_START_DATE)
        LocalDate startDate,

        @NotNull(message = ExhibitionValidationMessage.INVALID_END_DATE)
        LocalDate endDate,

        @NotNull(message = ExhibitionValidationMessage.INVALID_START_TIME)
        LocalTime startTime,

        @NotNull(message = ExhibitionValidationMessage.INVALID_END_TIME)
        LocalTime endTime,

        @NotNull(message = ExhibitionValidationMessage.INVALID_TICKET_OPEN_AT)
        LocalDateTime ticketOpenedAt,

        @NotNull(message = ExhibitionValidationMessage.INVALID_TICKET_CLOSE_AT)
        LocalDateTime ticketClosedAt,

        @Valid
        List<ExhibitionUpdateArtistRequestDto> artists
) {
}
