package com.portalestagios.controller;

import com.portalestagios.model.Vaga;
import com.portalestagios.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/vagas")
public class VagaController {

    @Autowired
    private VagaService vagaService;

    @PostMapping
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<Vaga> criarVaga(@Valid @RequestBody Vaga vaga, Authentication authentication) {
        // Assumir que o usuário logado é a empresa
        // Em produção, buscar empresa pelo usuário autenticado
        Vaga novaVaga = vagaService.salvarVaga(vaga);
        return ResponseEntity.ok(novaVaga);
    }

    @GetMapping
    public ResponseEntity<List<Vaga>> listarVagas() {
        List<Vaga> vagas = vagaService.listarVagasAbertas();
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/empresa/{empresaId}")
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<List<Vaga>> listarVagasPorEmpresa(@PathVariable Long empresaId) {
        List<Vaga> vagas = vagaService.listarVagasPorEmpresa(empresaId);
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/areas")
    public ResponseEntity<List<Vaga>> listarVagasPorAreas(@RequestParam List<Long> areaIds) {
        List<Vaga> vagas = vagaService.listarVagasPorAreas(areaIds);
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaga> buscarVaga(@PathVariable Long id) {
        return vagaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/encerrar")
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<Vaga> encerrarVaga(@PathVariable Long id) {
        Vaga vaga = vagaService.encerrarVaga(id);
        if (vaga != null) {
            return ResponseEntity.ok(vaga);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<Void> deletarVaga(@PathVariable Long id) {
        vagaService.deletarVaga(id);
        return ResponseEntity.noContent().build();
    }
}
