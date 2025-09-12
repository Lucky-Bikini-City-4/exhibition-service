package com.dayaeyak.exhibition.domain.exhibition.querydsl;

import com.dayaeyak.exhibition.domain.exhibition.Exhibition;
import com.dayaeyak.exhibition.domain.exhibition.querydsl.dto.response.ExhibitionFindProjectionDto;

import java.util.Optional;

public interface ExhibitionQuerydslRepository {

    Optional<ExhibitionFindProjectionDto> findExhibition(Long exhibitionId);

    Optional<Exhibition> findExhibitionById(Long exhibitionId);
}
