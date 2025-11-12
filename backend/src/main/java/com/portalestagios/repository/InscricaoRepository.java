package com.portalestagios.repository;

import com.portalestagios.model.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByEstudanteId(Long estudanteId);

    List<Inscricao> findByVagaId(Long vagaId);

    Optional<Inscricao> findByEstudanteIdAndVagaId(Long estudanteId, Long vagaId);

    boolean existsByEstudanteIdAndVagaId(Long estudanteId, Long vagaId);
}
