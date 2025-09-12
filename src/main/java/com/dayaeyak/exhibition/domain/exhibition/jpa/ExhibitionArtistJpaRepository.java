package com.dayaeyak.exhibition.domain.exhibition.jpa;

import com.dayaeyak.exhibition.domain.exhibition.ExhibitionArtist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionArtistJpaRepository extends JpaRepository<ExhibitionArtist, Long> {
}
