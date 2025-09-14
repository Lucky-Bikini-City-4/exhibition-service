package com.dayaeyak.exhibition.domain.exhibition;

import com.dayaeyak.exhibition.domain.artist.Artist;
import com.dayaeyak.exhibition.domain.artist.jpa.ArtistJpaRepository;
import com.dayaeyak.exhibition.domain.exhibition.dto.request.ExhibitionCreateArtistRequestDto;
import com.dayaeyak.exhibition.domain.exhibition.dto.request.ExhibitionCreateRequestDto;
import com.dayaeyak.exhibition.domain.exhibition.dto.response.ExhibitionCreateResponseDto;
import com.dayaeyak.exhibition.domain.exhibition.jpa.ExhibitionArtistJpaRepository;
import com.dayaeyak.exhibition.domain.exhibition.jpa.ExhibitionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ExhibitionInternalService {

    private final ArtistJpaRepository artistJpaRepository;
    private final ExhibitionJpaRepository exhibitionJpaRepository;
    private final ExhibitionArtistJpaRepository exhibitionArtistJpaRepository;

    private final ExhibitionArtistMappingHelper exhibitionArtistMappingHelper;

    @Transactional
    public ExhibitionCreateResponseDto createExhibition(ExhibitionCreateRequestDto dto) {
        Exhibition exhibition = Exhibition.builder()
                .sellerId(dto.sellerId())
                .price(dto.price())
                .name(dto.name())
                .place(dto.place())
                .address(dto.address())
                .region(dto.region())
                .grade(dto.grade())
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .ticketOpenedAt(dto.ticketOpenedAt())
                .ticketClosedAt(dto.ticketClosedAt())
                .build();

        Exhibition createdExhibition = exhibitionJpaRepository.save(exhibition);

        Set<String> artistNames = dto.artists().stream()
                .map(ExhibitionCreateArtistRequestDto::name)
                .collect(Collectors.toSet());

        List<Artist> artists = exhibitionArtistMappingHelper.findOrCreateArtists(artistNames);
        exhibitionArtistMappingHelper.mapExhibitionArtists(createdExhibition, artists);

        return ExhibitionCreateResponseDto.from(createdExhibition, artists);
    }
}
