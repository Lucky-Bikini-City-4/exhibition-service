package com.dayaeyak.exhibition.domain.exhibition.dto.response;

import com.dayaeyak.exhibition.domain.artist.Artist;

public record ExhibitionCreateArtistInfoResponseDto(
        Long artistId,

        String name
) {

    public static ExhibitionCreateArtistInfoResponseDto from(Artist artist) {
        return new ExhibitionCreateArtistInfoResponseDto(artist.getId(), artist.getName());
    }
}
