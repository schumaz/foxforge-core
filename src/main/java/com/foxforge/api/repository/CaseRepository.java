package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.CaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CaseRepository extends JpaRepository<CaseEntity, Long>, JpaSpecificationExecutor<CaseEntity> {
}
