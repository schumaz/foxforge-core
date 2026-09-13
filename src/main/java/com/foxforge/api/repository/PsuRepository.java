package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.PsuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PsuRepository extends JpaRepository<PsuEntity, Long> {

    /**
     * Busca fontes com potência maior ou igual à potência mínima requerida.
     *
     * @param minWattage Potência mínima em Watts
     * @return Lista de fontes com capacidade suficiente.
     */
    List<PsuEntity> findByWattageGreaterThanEqual(Integer minWattage);
}
