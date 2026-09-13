package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.RamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface RamRepository extends JpaRepository<RamEntity, Long>, JpaSpecificationExecutor<RamEntity> {

    /**
     * Finds RAM modules by memory type (e.g., "DDR4", "DDR5").
     *
     * @param memoryType Memory generation type
     * @return List of matching RAM modules.
     */
    List<RamEntity> findByMemoryType(String memoryType);
}
