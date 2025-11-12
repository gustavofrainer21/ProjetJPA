package com.portalestagios.service;

import com.portalestagios.model.AreaInteresse;
import com.portalestagios.repository.AreaInteresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AreaInteresseService {

    @Autowired
    private AreaInteresseRepository areaInteresseRepository;

    public AreaInteresse salvarAreaInteresse(AreaInteresse areaInteresse) {
        return areaInteresseRepository.save(areaInteresse);
    }

    public List<AreaInteresse> listarTodas() {
        return areaInteresseRepository.findAll();
    }

    public Optional<AreaInteresse> buscarPorId(Long id) {
        return areaInteresseRepository.findById(id);
    }

    public Optional<AreaInteresse> buscarPorNome(String nome) {
        return areaInteresseRepository.findByNome(nome);
    }

    public void deletarAreaInteresse(Long id) {
        areaInteresseRepository.deleteById(id);
    }
}
