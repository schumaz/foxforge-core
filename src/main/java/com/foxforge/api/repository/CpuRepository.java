package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.CpuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CpuRepository extends JpaRepository<CpuEntity, Long> {

    /**
     * Finds CPUs filtered by socket type.
     * Crucial for CPU and Motherboard compatibility specification.
     *
     * @param socket The CPU socket (e.g., "AM4", "LGA1700")
     * @return List of CPUs matching the specified socket.
     */
    List<CpuEntity> findBySocket(String socket);
}
