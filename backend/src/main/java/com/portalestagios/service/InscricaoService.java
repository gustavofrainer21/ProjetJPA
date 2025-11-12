package com.portalestagios.service;

import com.portalestagios.model.Inscricao;
import com.portalestagios.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    public Inscricao salvarInscricao(Inscricao inscricao) {
        return inscricaoRepository.save(inscricao);
    }

    public List<Inscricao> listarTodas() {
        return inscricaoRepository.findAll();
    }

    public List<Inscricao> listarPorEstudante(Long estudanteId) {
        return inscricaoRepository.findByEstudanteId(estudanteId);
    }

    public List<Inscricao> listarPorVaga(Long vagaId) {
        return inscricaoRepository.findByVagaId(vagaId);
    }

    public Optional<Inscricao> buscarPorId(Long id) {
        return inscricaoRepository.findById(id);
    }

    public Optional<Inscricao> buscarPorEstudanteEVaga(Long estudanteId, Long vagaId) {
        return inscricaoRepository.findByEstudanteIdAndVagaId(estudanteId, vagaId);
    }

    public boolean existeInscricao(Long estudanteId, Long vagaId) {
        return inscricaoRepository.existsByEstudanteIdAndVagaId(estudanteId, vagaId);
    }

    public void deletarInscricao(Long id) {
        inscricaoRepository.deleteById(id);
    }
}
