package com.dayaeyak.exhibition.domain.exhibition.dto.request;

import com.dayaeyak.exhibition.common.constraints.ArtistsValidationMessage;
import jakarta.validation.constraints.NotBlank;

public record ExhibitionUpdateArtistRequestDto(
        @NotBlank(message = ArtistsValidationMessage.INVALID_NAME)
        String name
) {
}
