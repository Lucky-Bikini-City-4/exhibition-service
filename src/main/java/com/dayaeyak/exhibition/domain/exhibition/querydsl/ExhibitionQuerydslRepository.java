package com.dayaeyak.exhibition.domain.exhibition.querydsl;

import com.dayaeyak.exhibition.domain.exhibition.querydsl.dto.response.ExhibitionFindProjectionDto;

import java.util.Optional;

public interface ExhibitionQuerydslRepository {

    Optional<ExhibitionFindProjectionDto> findExhibitionById(Long exhibitionId);
}
