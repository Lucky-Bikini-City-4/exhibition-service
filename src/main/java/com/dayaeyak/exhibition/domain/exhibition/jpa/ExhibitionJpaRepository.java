package com.dayaeyak.exhibition.domain.exhibition.jpa;

import com.dayaeyak.exhibition.domain.exhibition.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionJpaRepository extends JpaRepository<Exhibition, Long> {
}
