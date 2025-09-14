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

@Service
@RequiredArgsConstructor
public class ExhibitionService {

    private final ArtistJpaRepository artistJpaRepository;
    private final ExhibitionQuerydslRepository exhibitionQuerydslRepository;
    private final ExhibitionJpaRepository exhibitionJpaRepository;

    private final ExhibitionArtistMappingHelper exhibitionArtistMappingHelper;

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
        updateExhibitionArtists(dto, exhibition);

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

    private void updateExhibitionArtists(ExhibitionUpdateRequestDto dto, Exhibition exhibition) {
        Set<String> requestedArtistNames = dto.artists().stream()
                .map(ExhibitionUpdateArtistRequestDto::name)
                .collect(Collectors.toSet());

        Set<String> currentArtistNames = exhibition.getExhibitionArtistNameSet();

        // 추가 할 아티스트
        Set<String> addArtistNames = new HashSet<>(requestedArtistNames);
        addArtistNames.removeAll(currentArtistNames);

        // 삭제 할 아티스트
        Set<String> deleteArtistNames = new HashSet<>(currentArtistNames);
        deleteArtistNames.removeAll(requestedArtistNames);

        if (!deleteArtistNames.isEmpty()) {
            exhibition.getArtistList()
                    .removeIf(exhibitionArtist ->
                            deleteArtistNames.contains(
                                    exhibitionArtist.getArtist().getName()
                            ));
        }

        if (!addArtistNames.isEmpty()) {
            List<Artist> newArtists = exhibitionArtistMappingHelper.findOrCreateArtists(addArtistNames);
            exhibitionArtistMappingHelper.mapExhibitionArtists(exhibition, newArtists);
        }
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

    private Exhibition findExhibitionById(Long exhibitionId) {
        return exhibitionJpaRepository.findById(exhibitionId)
                .orElseThrow(() -> new CustomRuntimeException(ExhibitionExceptionType.EXHIBITION_NOT_FOUND));
    }

    private Exhibition findExhibitionWithJoinById(Long exhibitionId) {
        return exhibitionQuerydslRepository.findExhibitionById(exhibitionId)
                .orElseThrow(() -> new CustomRuntimeException(ExhibitionExceptionType.EXHIBITION_NOT_FOUND));
    }
}
