package com.portalestagios.repository;

import com.portalestagios.model.AreaInteresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AreaInteresseRepository extends JpaRepository<AreaInteresse, Long> {

    Optional<AreaInteresse> findByNome(String nome);

    boolean existsByNome(String nome);
}
