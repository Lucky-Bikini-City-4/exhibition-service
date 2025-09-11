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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExhibitionInternalService {

    private final ArtistJpaRepository artistJpaRepository;
    private final ExhibitionJpaRepository exhibitionJpaRepository;
    private final ExhibitionArtistJpaRepository exhibitionArtistJpaRepository;

    public ExhibitionCreateResponseDto createExhibition(ExhibitionCreateRequestDto dto) {
        Exhibition exhibition = Exhibition.builder()
                .sellerId(dto.sellerId())
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

        List<Artist> artists = findOrCreateArtists(artistNames);
        List<ExhibitionArtist> exhibitionArtists = mapExhibitionArtists(createdExhibition, artists);

        exhibitionArtistJpaRepository.saveAll(exhibitionArtists);

        return ExhibitionCreateResponseDto.from(createdExhibition, artists);
    }

    // 조회 후 없으면 생성 후 아티스트 리스트 반환
    private List<Artist> findOrCreateArtists(Set<String> artistNames) {
        return artistNames.stream()
                .map(name -> artistJpaRepository.findByNameAndDeletedAtIsNull(name)
                        .orElseGet(() -> artistJpaRepository.save(new Artist(name)))
                )
                .toList();
    }

    private List<ExhibitionArtist> mapExhibitionArtists(Exhibition exhibition, List<Artist> artists) {
        return artists.stream()
                .map(artist -> new ExhibitionArtist(exhibition, artist))
                .toList();
    }
}
