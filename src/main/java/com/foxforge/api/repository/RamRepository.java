package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.RamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RamRepository extends JpaRepository<RamEntity, Long> {

    /**
     * Busca módulos de RAM pelo tipo de memória (ex: "DDR4", "DDR5").
     *
     * @param memoryType Tipo de memória
     * @return Lista de módulos de RAM correspondentes.
     */
    List<RamEntity> findByMemoryType(String memoryType);
}
