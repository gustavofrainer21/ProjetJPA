package com.portalestagios.service;

import com.portalestagios.model.Vaga;
import com.portalestagios.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    public Vaga salvarVaga(Vaga vaga) {
        return vagaRepository.save(vaga);
    }

    public List<Vaga> listarTodas() {
        return vagaRepository.findAll();
    }

    public List<Vaga> listarVagasAbertas() {
        return vagaRepository.findByEncerradaFalse();
    }

    public List<Vaga> listarVagasPorEmpresa(Long empresaId) {
        return vagaRepository.findByEmpresaId(empresaId);
    }

    public List<Vaga> listarVagasPorAreas(List<Long> areaIds) {
        return vagaRepository.findByAreasInteresse(areaIds);
    }

    public Optional<Vaga> buscarPorId(Long id) {
        return vagaRepository.findById(id);
    }

    public void deletarVaga(Long id) {
        vagaRepository.deleteById(id);
    }

    public Vaga encerrarVaga(Long id) {
        Optional<Vaga> vagaOpt = vagaRepository.findById(id);
        if (vagaOpt.isPresent()) {
            Vaga vaga = vagaOpt.get();
            vaga.setEncerrada(true);
            return vagaRepository.save(vaga);
        }
        return null;
    }
}
