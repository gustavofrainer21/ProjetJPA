package com.portalestagios.repository;

import com.portalestagios.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

    List<Vaga> findByEncerradaFalse();

    List<Vaga> findByEmpresaId(Long empresaId);

    @Query("SELECT v FROM Vaga v WHERE v.encerrada = false AND v.area.id IN :areaIds")
    List<Vaga> findByAreasInteresse(@Param("areaIds") List<Long> areaIds);
}
