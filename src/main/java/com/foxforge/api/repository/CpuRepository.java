package com.foxforge.api.repository;

import com.foxforge.api.domain.entity.CpuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CpuRepository extends JpaRepository<CpuEntity, Long> {

    /**
     * Busca os processadores filtrando pelo tipo de soquete.
     * Crucial para a Especificação de Compatibilidade entre CPU e Placa-mãe.
     *
     * @param socket O soquete da CPU (ex: "AM4", "LGA1700")
     * @return Lista de processadores compatíveis com o soquete informado.
     */
    List<CpuEntity> findBySocket(String socket);
}
