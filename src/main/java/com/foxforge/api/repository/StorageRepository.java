package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface StorageRepository extends JpaRepository<StorageEntity, Long>, JpaSpecificationExecutor<StorageEntity> {

    /**
     * Finds storage devices filtered by storage type (e.g., "NVMe SSD", "SATA SSD").
     *
     * @param storageType Storage form factor / interface type
     * @return List of matching storage drives.
     */
    List<StorageEntity> findByStorageType(String storageType);
}
