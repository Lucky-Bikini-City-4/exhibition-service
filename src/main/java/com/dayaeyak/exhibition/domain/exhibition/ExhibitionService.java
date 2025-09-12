package com.dayaeyak.exhibition.domain.exhibition;

import com.dayaeyak.exhibition.common.exception.CustomRuntimeException;
import com.dayaeyak.exhibition.common.exception.type.ExhibitionExceptionType;
import com.dayaeyak.exhibition.domain.artist.Artist;
import com.dayaeyak.exhibition.domain.artist.jpa.ArtistJpaRepository;
import com.dayaeyak.exhibition.domain.exhibition.dto.request.ExhibitionUpdateArtistRequestDto;
import com.dayaeyak.exhibition.domain.exhibition.dto.request.ExhibitionUpdateRequestDto;
import com.dayaeyak.exhibition.domain.exhibition.dto.response.ExhibitionFindResponseDto;
import com.dayaeyak.exhibition.domain.exhibition.dto.response.ExhibitionUpdateActivationResponseDto;
import com.dayaeyak.exhibition.domain.exhibition.dto.response.ExhibitionUpdateResponseDto;
import com.dayaeyak.exhibition.domain.exhibition.jpa.ExhibitionJpaRepository;
import com.dayaeyak.exhibition.domain.exhibition.querydsl.ExhibitionQuerydslRepository;
import com.dayaeyak.exhibition.domain.exhibition.querydsl.dto.response.ExhibitionFindProjectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ExhibitionService {

    private final ArtistJpaRepository artistJpaRepository;
    private final ExhibitionQuerydslRepository exhibitionQuerydslRepository;
    private final ExhibitionJpaRepository exhibitionJpaRepository;

    public ExhibitionFindResponseDto findExhibition(Long exhibitionId) {
        ExhibitionFindProjectionDto record = exhibitionQuerydslRepository.findExhibition(exhibitionId)
                .orElseThrow(() -> new CustomRuntimeException(ExhibitionExceptionType.EXHIBITION_NOT_FOUND));

        return ExhibitionFindResponseDto.from(record);
    }

    @Transactional
    public ExhibitionUpdateResponseDto updateExhibition(Long exhibitionId, ExhibitionUpdateRequestDto dto, Long userId) {
        Exhibition exhibition = findExhibitionWithJoinById(exhibitionId);

        // validations
        validateOwnExhibition(userId, exhibition.getSellerId());
        validateTicketOpenTime(exhibition.getTicketOpenedAt());

        exhibition.update(dto);

        // 아티스트 수정
        Set<String> requestedArtistNames = dto.artists().stream()
                .map(ExhibitionUpdateArtistRequestDto::name)
                .collect(Collectors.toSet());

        Set<String> currentArtistNames = exhibition.getArtistList().stream()
                .map(ExhibitionArtist::getArtist)
                .map(Artist::getName)
                .collect(Collectors.toSet());

        // 추가 할 아티스트
        Set<String> addArtistNames = new HashSet<>(requestedArtistNames);
        addArtistNames.removeAll(currentArtistNames);

        // 삭제 할 아티스트
        Set<String> deleteArtistNames = new HashSet<>(currentArtistNames);
        deleteArtistNames.removeAll(requestedArtistNames);

        // delete
        exhibition.getArtistList()
                .removeIf(exhibitionArtist ->
                        deleteArtistNames.contains(
                                exhibitionArtist.getArtist().getName()
                        ));

        // add
        List<Artist> newArtists = findOrCreateArtists(addArtistNames);
        mapExhibitionArtists(exhibition, newArtists);

        return ExhibitionUpdateResponseDto.from(exhibition);
    }

    @Transactional
    public ExhibitionUpdateActivationResponseDto updateExhibitionActivation(Long exhibitionId, Long userId) {
        Exhibition exhibition = findExhibitionById(exhibitionId);

        // validations
        validateOwnExhibition(userId, exhibition.getSellerId());
        validateTicketOpenTime(exhibition.getTicketOpenedAt());

        exhibition.changeActivation();

        return ExhibitionUpdateActivationResponseDto.from(exhibition);
    }

    private void validateTicketOpenTime(LocalDateTime ticketOpenAt) {
        if (ticketOpenAt.isBefore(LocalDateTime.now())) {
            throw new CustomRuntimeException(ExhibitionExceptionType.ALREADY_EXHIBITION_TICKET_OPENED);
        }
    }

    private void validateOwnExhibition(Long requestUserId, Long ownerId) {
        if (!requestUserId.equals(ownerId)) {
            throw new CustomRuntimeException(ExhibitionExceptionType.ACCESS_DENIED);
        }
    }

    // TODO 중복된 메서드 리팩토링
    // 조회 후 없으면 생성 후 아티스트 리스트 반환
    private List<Artist> findOrCreateArtists(Set<String> artistNames) {
        List<Artist> foundArtists = artistJpaRepository.findByNameInAndDeletedAtIsNull(artistNames);
        Set<String> existingArtists = foundArtists.stream()
                .map(Artist::getName)
                .collect(Collectors.toSet());

        List<Artist> newArtists = artistNames.stream()
                .filter(name -> !existingArtists.contains(name))
                .map(Artist::new)
                .map(artistJpaRepository::save)
                .toList();

        return Stream.concat(foundArtists.stream(), newArtists.stream())
                .toList();
    }

    // 전시회 & 아티스트 Mapping
    private void mapExhibitionArtists(Exhibition exhibition, List<Artist> artists) {
        artists.forEach(artist -> exhibition.addExhibitionArtist(new ExhibitionArtist(artist)));
    }

    private Exhibition findExhibitionById(Long exhibitionId) {
        return exhibitionJpaRepository.findById(exhibitionId)
                .orElseThrow(() -> new CustomRuntimeException(ExhibitionExceptionType.EXHIBITION_NOT_FOUND));
    }

    private Exhibition findExhibitionWithJoinById(Long exhibitionId) {
        return exhibitionQuerydslRepository.findExhibitionById(exhibitionId)
                .orElseThrow(() -> new CustomRuntimeException(ExhibitionExceptionType.EXHIBITION_NOT_FOUND));
    }
}
