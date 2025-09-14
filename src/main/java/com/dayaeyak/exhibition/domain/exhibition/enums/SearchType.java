package com.dayaeyak.exhibition.domain.exhibition.enums;

import com.dayaeyak.exhibition.common.exception.CustomRuntimeException;
import com.dayaeyak.exhibition.common.exception.type.ExhibitionExceptionType;
import com.dayaeyak.exhibition.domain.artist.QArtist;
import com.dayaeyak.exhibition.domain.exhibition.QExhibition;
import com.querydsl.core.types.dsl.StringPath;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SearchType {

    NAME("name", QExhibition.exhibition.name),
    PLACE("place", QExhibition.exhibition.place),
    ARTIST("artist", QArtist.artist.name),
    ;

    private final String type;
    private final StringPath path;

    public static SearchType of(String type) {
        return Arrays.stream(SearchType.values())
                .filter(searchType -> searchType.type.equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new CustomRuntimeException(ExhibitionExceptionType.INVALID_SEARCH_TYPE));
    }
}
