package com.dayaeyak.exhibition.domain.exhibition.querydsl;

import com.dayaeyak.exhibition.domain.exhibition.Exhibition;
import com.dayaeyak.exhibition.domain.exhibition.enums.Grade;
import com.dayaeyak.exhibition.domain.exhibition.enums.Region;
import com.dayaeyak.exhibition.domain.exhibition.enums.SearchType;
import com.dayaeyak.exhibition.domain.exhibition.querydsl.dto.response.*;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
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
    public Optional<ExhibitionFindProjectionDto> findExhibitionDetail(Long exhibitionId) {
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
                                exhibition.price,
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

    @Override
    public Optional<Exhibition> findExhibitionById(Long exhibitionId) {
        Exhibition record = queryFactory.selectFrom(exhibition)
                .innerJoin(exhibition.artistList, exhibitionArtist).fetchJoin()
                .innerJoin(exhibitionArtist.artist, artist).fetchJoin()
                .where(
                        exhibition.id.eq(exhibitionId),
                        exhibition.deletedAt.isNull()
                )
                .fetchOne();

        return Optional.ofNullable(record);
    }

    @Override
    public Page<ExhibitionSearchProjectionDto> searchExhibitions(
            Pageable pageable,
            Region region,
            Grade grade,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            SearchType searchType
    ) {
        Predicate[] whereClauses = {
                eqSearchType(keyword, searchType),
                eqRegion(region),
                eqGrade(grade),
                exhibition.isActivated.eq(true),
                exhibition.deletedAt.isNull()
        };

        List<ExhibitionSearchProjectionDto> records = queryFactory.select(new QExhibitionSearchProjectionDto(
                        exhibition.id,
                        exhibition.name,
                        exhibition.place,
                        exhibition.region,
                        exhibition.grade,
                        exhibition.startDate,
                        exhibition.endDate,
                        exhibition.startTime,
                        exhibition.endTime,
                        exhibition.ticketOpenedAt,
                        exhibition.ticketClosedAt
                ))
                .from(exhibition)
                .innerJoin(exhibition.artistList, exhibitionArtist)
                .innerJoin(exhibitionArtist.artist, artist)
                .where(whereClauses)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(exhibition.count())
                .from(exhibition)
                .where(whereClauses);

        return PageableExecutionUtils.getPage(records, pageable, count::fetchOne);
    }

    @Override
    public Optional<ExhibitionFindBookingDataProjectionDto> findExhibitionForBooking(Long exhibitionId) {
        ExhibitionFindBookingDataProjectionDto record = queryFactory.select(new QExhibitionFindBookingDataProjectionDto(
                        exhibition.price,
                        exhibition.grade
                ))
                .from(exhibition)
                .where(
                        exhibition.id.eq(exhibitionId),
                        exhibition.deletedAt.isNull()
                )
                .fetchOne();

        return Optional.ofNullable(record);
    }

    private BooleanExpression eqRegion(Region region) {
        return region != null ? exhibition.region.eq(region) : null;
    }

    private BooleanExpression eqGrade(Grade grade) {
        return grade != null ? exhibition.grade.eq(grade) : null;
    }

    private BooleanExpression eqEndDate(LocalDate endDate) {
        return endDate != null ? exhibition.startDate.after(endDate) : null;
    }

    private BooleanExpression eqSearchType(String keyword, SearchType searchType) {
        return searchType != null ? searchType.getPath().contains(keyword) : null;
    }
}
