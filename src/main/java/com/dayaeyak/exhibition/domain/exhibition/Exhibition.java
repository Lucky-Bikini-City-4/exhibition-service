package com.dayaeyak.exhibition.domain.exhibition;

import com.dayaeyak.exhibition.common.entity.BaseEntity;
import com.dayaeyak.exhibition.domain.exhibition.enums.Grade;
import com.dayaeyak.exhibition.domain.exhibition.enums.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exhibitions")

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exhibition extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exhibition_id")
    private Long id;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String place;

    @Column(nullable = false, length = 100)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Region region;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private LocalDateTime ticketOpenedAt;

    @Column(nullable = false)
    private LocalDateTime ticketClosedAt;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isActivated = true;

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExhibitionArtist> artistList = new ArrayList<>();

    @Builder
    public Exhibition(
            Long sellerId,
            String name,
            String place,
            String address,
            Region region,
            Grade grade,
            LocalDate startDate,
            LocalDate endDate,
            LocalTime startTime,
            LocalTime endTime,
            LocalDateTime ticketOpenedAt,
            LocalDateTime ticketClosedAt
    ) {
        this.sellerId = sellerId;
        this.name = name;
        this.place = place;
        this.address = address;
        this.region = region;
        this.grade = grade;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ticketOpenedAt = ticketOpenedAt;
        this.ticketClosedAt = ticketClosedAt;
    }

    public void addExhibitionArtist(ExhibitionArtist exhibitionArtist) {
        artistList.add(exhibitionArtist);
        exhibitionArtist.linkExhibition(this);
    }
}
