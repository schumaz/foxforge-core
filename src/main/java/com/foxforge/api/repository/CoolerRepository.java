package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.CoolerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoolerRepository extends JpaRepository<CoolerEntity, Long> {
}
