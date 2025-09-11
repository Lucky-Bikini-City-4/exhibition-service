package com.dayaeyak.exhibition.domain.artist.dto.response;

import com.dayaeyak.exhibition.domain.artist.Artist;

public record ArtistSearchResponseDto(
        Long artistId,

        String name
) {

    public static ArtistSearchResponseDto from(Artist artist) {
        return new ArtistSearchResponseDto(artist.getId(), artist.getName());
    }
}
