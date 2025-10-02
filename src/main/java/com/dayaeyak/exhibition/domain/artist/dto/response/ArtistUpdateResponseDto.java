package com.dayaeyak.exhibition.domain.artist.dto.response;

import com.dayaeyak.exhibition.domain.artist.Artist;

public record ArtistUpdateResponseDto(
        Long artisId,

        String name
) {

    public static ArtistUpdateResponseDto from(Artist artist) {
        return new ArtistUpdateResponseDto(artist.getId(), artist.getName());
    }
}
