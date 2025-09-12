package com.dayaeyak.exhibition.domain.artist.dto.request;

import com.dayaeyak.exhibition.domain.artist.constraints.ArtistValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArtistUpdateRequestDto(
        @NotBlank(message = ArtistValidationMessage.INVALID_NAME_MESSAGE)
//        @Size(
//                min = ArtistValidationMessage.NAME_MIN_LENGTH,
//                max = ArtistValidationMessage.NAME_MAX_LENGTH,
//                message = ArtistValidationMessage.INVALID_NAME_MESSAGE
//        )
        String name
) {
}
