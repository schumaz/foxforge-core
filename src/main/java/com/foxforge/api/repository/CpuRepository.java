package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.CpuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface CpuRepository extends JpaRepository<CpuEntity, Long>, JpaSpecificationExecutor<CpuEntity> {

    /**
     * Finds CPUs filtered by socket type.
     * Crucial for CPU and Motherboard compatibility specification.
     *
     * @param socket The CPU socket (e.g., "AM4", "LGA1700")
     * @return List of CPUs matching the specified socket.
     */
    List<CpuEntity> findBySocket(String socket);

    /**
     * Finds CPUs supporting a specific memory type.
     * Since CPUs like Intel 13th/14th Gen can support multiple memory types (e.g., "DDR4, DDR5"),
     * containing search is required.
     *
     * @param memoryType the memory type to search for (e.g., "DDR4" or "DDR5")
     * @return List of CPUs supporting the given memory type
     */
    List<CpuEntity> findBySupportedMemoryTypeContainingIgnoreCase(String memoryType);
}
