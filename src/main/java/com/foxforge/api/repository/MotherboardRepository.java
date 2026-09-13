package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.MotherboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MotherboardRepository extends JpaRepository<MotherboardEntity, Long> {
    
    /**
     * Busca placas-mãe compatíveis com o soquete do processador.
     *
     * @param socket O soquete (ex: "AM4", "LGA1700")
     * @return Lista de placas-mãe.
     */
    List<MotherboardEntity> findBySocket(String socket);
}
