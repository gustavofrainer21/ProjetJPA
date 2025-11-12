package com.portalestagios.repository;

import com.portalestagios.model.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    Optional<Estudante> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
