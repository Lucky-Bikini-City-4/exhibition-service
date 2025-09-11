package com.dayaeyak.exhibition.domain.exhibition;

import com.dayaeyak.exhibition.common.exception.CustomRuntimeException;
import com.dayaeyak.exhibition.common.exception.type.ExhibitionExceptionType;
import com.dayaeyak.exhibition.domain.exhibition.dto.response.ExhibitionFindResponseDto;
import com.dayaeyak.exhibition.domain.exhibition.querydsl.ExhibitionQuerydslRepository;
import com.dayaeyak.exhibition.domain.exhibition.querydsl.dto.response.ExhibitionFindProjectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExhibitionService {

    private final ExhibitionQuerydslRepository exhibitionQuerydslRepository;

    public ExhibitionFindResponseDto findExhibition(Long exhibitionId) {
        ExhibitionFindProjectionDto record = exhibitionQuerydslRepository.findExhibitionById(exhibitionId)
                .orElseThrow(() -> new CustomRuntimeException(ExhibitionExceptionType.EXHIBITION_NOT_FOUND));

        return ExhibitionFindResponseDto.from(record);
    }
}
