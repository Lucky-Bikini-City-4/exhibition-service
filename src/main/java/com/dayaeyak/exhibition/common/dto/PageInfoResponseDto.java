package com.dayaeyak.exhibition.common.dto;

import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record PageInfoResponseDto(
        int pageNumber,

        int pageSize,

        long totalElements,

        int totalPages,

        boolean last
) {

    public static <T> PageInfoResponseDto from(Page<T> data) {
        return PageInfoResponseDto.builder()
                .pageNumber(data.getNumber())
                .pageSize(data.getSize())
                .totalElements(data.getTotalElements())
                .totalPages(data.getTotalPages())
                .last(data.isLast())
                .build();
    }
}
