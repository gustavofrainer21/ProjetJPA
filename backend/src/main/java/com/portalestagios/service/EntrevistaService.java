package com.portalestagios.service;

import com.portalestagios.model.Entrevista;
import com.portalestagios.repository.EntrevistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EntrevistaService {

    @Autowired
    private EntrevistaRepository entrevistaRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Entrevista salvarEntrevista(Entrevista entrevista) {
        Entrevista saved = entrevistaRepository.save(entrevista);
        enviarNotificacao(saved);
        return saved;
    }

    public List<Entrevista> listarTodas() {
        return entrevistaRepository.findAll();
    }

    public List<Entrevista> listarPorEmpresa(Long empresaId) {
        return entrevistaRepository.findByEmpresaId(empresaId);
    }

    public List<Entrevista> listarPorEstudante(Long estudanteId) {
        return entrevistaRepository.findByEstudanteId(estudanteId);
    }

    public List<Entrevista> listarPorVaga(Long vagaId) {
        return entrevistaRepository.findByVagaId(vagaId);
    }

    public Optional<Entrevista> buscarPorId(Long id) {
        return entrevistaRepository.findById(id);
    }

    public void deletarEntrevista(Long id) {
        entrevistaRepository.deleteById(id);
    }

    private void enviarNotificacao(Entrevista entrevista) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(entrevista.getEstudante().getEmail());
        message.setSubject("Entrevista Agendada - Portal de Estágios");
        message.setText("Olá " + entrevista.getEstudante().getNome() + ",\n\n" +
                        "Uma entrevista foi agendada para a vaga '" + entrevista.getVaga().getTitulo() + "'.\n" +
                        "Data e Hora: " + entrevista.getDataHora() + "\n" +
                        "Local: " + entrevista.getLocal() + "\n\n" +
                        "Atenciosamente,\nPortal de Estágios");
        mailSender.send(message);
    }
}
