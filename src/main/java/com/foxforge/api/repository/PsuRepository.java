package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.PsuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface PsuRepository extends JpaRepository<PsuEntity, Long>, JpaSpecificationExecutor<PsuEntity> {

    /**
     * Finds power supplies with wattage greater than or equal to the required minimum.
     *
     * @param minWattage Minimum wattage in Watts
     * @return List of power supplies with sufficient capacity.
     */
    List<PsuEntity> findByWattageGreaterThanEqual(Integer minWattage);
}
