package com.dayaeyak.exhibition.domain.exhibition.dto.response;

import com.dayaeyak.exhibition.domain.artist.Artist;
import com.dayaeyak.exhibition.domain.exhibition.Exhibition;
import com.dayaeyak.exhibition.domain.exhibition.enums.Grade;
import com.dayaeyak.exhibition.domain.exhibition.enums.Region;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Builder
public record ExhibitionCreateResponseDto(
        Long exhibitionId,

        Long sellerId,

        Integer price,

        String name,

        String place,

        String address,

        Region region,

        Grade grade,

        @JsonFormat(pattern = "yyyy년 MM월 dd일")
        LocalDate startDate,

        @JsonFormat(pattern = "yyyy년 MM월 dd일")
        LocalDate endDate,

        @JsonFormat(pattern = "HH시 mm분")
        LocalTime startTime,

        @JsonFormat(pattern = "HH시 mm분")
        LocalTime endTime,

        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분")
        LocalDateTime ticketOpenAt,

        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분")
        LocalDateTime ticketCloseAt,

        @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분 ss초")
        LocalDateTime createdAt,

        Boolean isActivated,

        List<ExhibitionCreateArtistInfoResponseDto> artists
) {

    public static ExhibitionCreateResponseDto from(Exhibition exhibition, List<Artist> artistList) {
        List<ExhibitionCreateArtistInfoResponseDto> artists = artistList.stream()
                .map(ExhibitionCreateArtistInfoResponseDto::from)
                .toList();

        return ExhibitionCreateResponseDto.builder()
                .exhibitionId(exhibition.getId())
                .sellerId(exhibition.getSellerId())
                .price(exhibition.getPrice())
                .name(exhibition.getName())
                .place(exhibition.getPlace())
                .address(exhibition.getAddress())
                .region(exhibition.getRegion())
                .grade(exhibition.getGrade())
                .startDate(exhibition.getStartDate())
                .endDate(exhibition.getEndDate())
                .startTime(exhibition.getStartTime())
                .endTime(exhibition.getEndTime())
                .ticketOpenAt(exhibition.getTicketOpenedAt())
                .ticketCloseAt(exhibition.getTicketClosedAt())
                .createdAt(exhibition.getCreatedAt())
                .isActivated(exhibition.getIsActivated())
                .artists(artists)
                .build();
    }
}
