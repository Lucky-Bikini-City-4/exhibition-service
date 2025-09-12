package com.dayaeyak.exhibition.domain.exhibition;

import com.dayaeyak.exhibition.domain.artist.Artist;
import com.dayaeyak.exhibition.domain.artist.jpa.ArtistJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ExhibitionArtistMappingHelper {

    private final ArtistJpaRepository artistJpaRepository;

    /**
     * 조회 후 없으면 생성 후 아티스트 리스트 반환
     */
    public List<Artist> findOrCreateArtists(Set<String> artistNames) {
        List<Artist> foundArtists = artistJpaRepository.findByNameInAndDeletedAtIsNull(artistNames);

        Set<String> existingArtists = foundArtists.stream()
                .map(Artist::getName)
                .collect(Collectors.toSet());

        List<Artist> newArtists = artistNames.stream()
                .filter(name -> !existingArtists.contains(name))
                .map(Artist::new)
                .toList();

        if (!newArtists.isEmpty()) {
            artistJpaRepository.saveAll(newArtists);
        }

        return Stream.concat(foundArtists.stream(), newArtists.stream())
                .toList();
    }

    /**
     * Mapping 전시회 & 아티스트
     */
    public void mapExhibitionArtists(Exhibition exhibition, List<Artist> artists) {
        artists.forEach(artist -> exhibition.addExhibitionArtist(new ExhibitionArtist(artist)));
    }
}
