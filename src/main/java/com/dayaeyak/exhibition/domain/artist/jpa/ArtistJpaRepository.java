package com.dayaeyak.exhibition.domain.artist.jpa;

import com.dayaeyak.exhibition.domain.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArtistJpaRepository extends JpaRepository<Artist, Long> {

    boolean existsByNameAndDeletedAtIsNull(String name);

    Optional<Artist> findByIdAndDeletedAtIsNull(Long id);

    Optional<Artist> findByNameAndDeletedAtIsNull(String name);
}
