package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.MotherboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MotherboardRepository extends JpaRepository<MotherboardEntity, Long> {

    /**
     * Finds motherboards matching the specified processor socket.
     *
     * @param socket The CPU socket (e.g., "AM4", "LGA1700")
     * @return List of matching motherboards.
     */
    List<MotherboardEntity> findBySocket(String socket);
}
