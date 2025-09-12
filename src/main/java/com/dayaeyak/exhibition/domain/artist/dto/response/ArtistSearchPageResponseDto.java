package com.dayaeyak.exhibition.domain.artist.dto.response;

import com.dayaeyak.exhibition.domain.artist.Artist;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record ArtistSearchPageResponseDto(
        int pageNumber,

        int pageSize,

        long totalElements,

        int totalPages,

        boolean first,

        boolean last,

        List<ArtistSearchResponseDto> data
) {

    public static ArtistSearchPageResponseDto from(Page<Artist> page) {

        List<ArtistSearchResponseDto> data = page.getContent()
                .stream().map(ArtistSearchResponseDto::from)
                .toList();

        return ArtistSearchPageResponseDto.builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .data(data)
                .build();
    }
}
