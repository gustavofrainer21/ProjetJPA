package com.portalestagios.controller;

import com.portalestagios.model.Inscricao;
import com.portalestagios.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    @PostMapping
    @PreAuthorize("hasRole('ESTUDANTE')")
    public ResponseEntity<Inscricao> criarInscricao(@Valid @RequestBody Inscricao inscricao) {
        if (inscricaoService.existeInscricao(inscricao.getEstudante().getId(), inscricao.getVaga().getId())) {
            return ResponseEntity.badRequest().build();
        }
        Inscricao novaInscricao = inscricaoService.salvarInscricao(inscricao);
        return ResponseEntity.ok(novaInscricao);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Inscricao>> listarInscricoes() {
        List<Inscricao> inscricoes = inscricaoService.listarTodas();
        return ResponseEntity.ok(inscricoes);
    }

    @GetMapping("/estudante/{estudanteId}")
    @PreAuthorize("hasRole('ESTUDANTE')")
    public ResponseEntity<List<Inscricao>> listarInscricoesPorEstudante(@PathVariable Long estudanteId) {
        List<Inscricao> inscricoes = inscricaoService.listarPorEstudante(estudanteId);
        return ResponseEntity.ok(inscricoes);
    }

    @GetMapping("/vaga/{vagaId}")
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<List<Inscricao>> listarInscricoesPorVaga(@PathVariable Long vagaId) {
        List<Inscricao> inscricoes = inscricaoService.listarPorVaga(vagaId);
        return ResponseEntity.ok(inscricoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscricao> buscarInscricao(@PathVariable Long id) {
        return inscricaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ESTUDANTE')")
    public ResponseEntity<Void> deletarInscricao(@PathVariable Long id) {
        inscricaoService.deletarInscricao(id);
        return ResponseEntity.noContent().build();
    }
}
