package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.CoolerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface CoolerRepository extends JpaRepository<CoolerEntity, Long>, JpaSpecificationExecutor<CoolerEntity> {

    /**
     * Finds coolers compatible with a given CPU socket.
     *
     * @param socket CPU socket (e.g., "AM4", "LGA1700")
     * @return List of matching coolers.
     */
    List<CoolerEntity> findBySupportedSocketsContainingIgnoreCase(String socket);
}
