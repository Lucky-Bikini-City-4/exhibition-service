package com.dayaeyak.exhibition.domain.exhibition.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ExhibitionCreateArtistRequestDto(
        @NotBlank
        String name
) {
}
