package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.MotherboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface MotherboardRepository extends JpaRepository<MotherboardEntity, Long>, JpaSpecificationExecutor<MotherboardEntity> {

    /**
     * Finds motherboards matching the specified processor socket.
     *
     * @param socket The CPU socket (e.g., "AM4", "LGA1700")
     * @return List of matching motherboards.
     */
    List<MotherboardEntity> findBySocket(String socket);
}
