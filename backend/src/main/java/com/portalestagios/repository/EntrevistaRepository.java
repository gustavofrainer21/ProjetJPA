package com.portalestagios.repository;

import com.portalestagios.model.Entrevista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EntrevistaRepository extends JpaRepository<Entrevista, Long> {

    List<Entrevista> findByEmpresaId(Long empresaId);

    List<Entrevista> findByEstudanteId(Long estudanteId);

    List<Entrevista> findByVagaId(Long vagaId);
}
