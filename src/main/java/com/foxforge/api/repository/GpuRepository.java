package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.GpuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpuRepository extends JpaRepository<GpuEntity, Long> {
}
