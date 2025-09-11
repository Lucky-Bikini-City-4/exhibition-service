package com.dayaeyak.exhibition.domain.exhibition.dto.response;

import com.dayaeyak.exhibition.domain.artist.Artist;
import com.dayaeyak.exhibition.domain.exhibition.Exhibition;

import java.util.List;

public record ExhibitionCreateResponseDto(
        ExhibitionCreateInfoResponseDto exhibition,

        List<ExhibitionCreateArtistInfoResponseDto> artists
) {

    public static ExhibitionCreateResponseDto from(Exhibition exhibition, List<Artist> artistList) {
        ExhibitionCreateInfoResponseDto exhibitionInfo = ExhibitionCreateInfoResponseDto.from(exhibition);

        List<ExhibitionCreateArtistInfoResponseDto> artists = artistList.stream()
                .map(ExhibitionCreateArtistInfoResponseDto::from)
                .toList();

        return new ExhibitionCreateResponseDto(exhibitionInfo, artists);
    }
}
