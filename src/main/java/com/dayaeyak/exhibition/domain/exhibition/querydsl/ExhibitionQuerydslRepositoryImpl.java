package com.dayaeyak.exhibition.domain.exhibition.querydsl;

import com.dayaeyak.exhibition.domain.exhibition.querydsl.dto.response.ExhibitionFindArtistProjectionDto;
import com.dayaeyak.exhibition.domain.exhibition.querydsl.dto.response.ExhibitionFindProjectionDto;
import com.dayaeyak.exhibition.domain.exhibition.querydsl.dto.response.QExhibitionFindProjectionDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

import static com.dayaeyak.exhibition.domain.artist.QArtist.artist;
import static com.dayaeyak.exhibition.domain.exhibition.QExhibition.exhibition;
import static com.dayaeyak.exhibition.domain.exhibition.QExhibitionArtist.exhibitionArtist;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
public class ExhibitionQuerydslRepositoryImpl implements ExhibitionQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ExhibitionFindProjectionDto> findExhibitionById(Long exhibitionId) {
        Map<Long, ExhibitionFindProjectionDto> record = queryFactory
                .selectFrom(exhibition)
                .innerJoin(exhibitionArtist).on(exhibitionArtist.exhibition.id.eq(exhibition.id))
                .innerJoin(exhibitionArtist.artist, artist)
                .where(
                        exhibition.id.eq(exhibitionId),
                        exhibition.isActivated.eq(true),
                        exhibition.deletedAt.isNull()
                )
                .transform(groupBy(exhibition.id).as(
                        new QExhibitionFindProjectionDto(
                                exhibition.id,
                                exhibition.name,
                                exhibition.place,
                                exhibition.address,
                                exhibition.region,
                                exhibition.grade,
                                exhibition.startDate,
                                exhibition.endDate,
                                exhibition.startTime,
                                exhibition.endTime,
                                exhibition.ticketOpenedAt,
                                exhibition.ticketClosedAt,
                                exhibition.isActivated,
                                list(Projections.constructor(ExhibitionFindArtistProjectionDto.class,
                                        artist.id,
                                        artist.name
                                ))
                        )
                ));

        return Optional.ofNullable(record.get(exhibitionId));
    }
}
