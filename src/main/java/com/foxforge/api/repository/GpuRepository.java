package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.GpuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GpuRepository extends JpaRepository<GpuEntity, Long>, JpaSpecificationExecutor<GpuEntity> {
}
