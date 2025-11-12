package com.portalestagios.controller;

import com.portalestagios.model.Entrevista;
import com.portalestagios.service.EntrevistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/entrevistas")
public class EntrevistaController {

    @Autowired
    private EntrevistaService entrevistaService;

    @PostMapping
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<Entrevista> criarEntrevista(@Valid @RequestBody Entrevista entrevista) {
        Entrevista novaEntrevista = entrevistaService.salvarEntrevista(entrevista);
        return ResponseEntity.ok(novaEntrevista);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Entrevista>> listarEntrevistas() {
        List<Entrevista> entrevistas = entrevistaService.listarTodas();
        return ResponseEntity.ok(entrevistas);
    }

    @GetMapping("/empresa/{empresaId}")
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<List<Entrevista>> listarEntrevistasPorEmpresa(@PathVariable Long empresaId) {
        List<Entrevista> entrevistas = entrevistaService.listarPorEmpresa(empresaId);
        return ResponseEntity.ok(entrevistas);
    }

    @GetMapping("/estudante/{estudanteId}")
    @PreAuthorize("hasRole('ESTUDANTE')")
    public ResponseEntity<List<Entrevista>> listarEntrevistasPorEstudante(@PathVariable Long estudanteId) {
        List<Entrevista> entrevistas = entrevistaService.listarPorEstudante(estudanteId);
        return ResponseEntity.ok(entrevistas);
    }

    @GetMapping("/vaga/{vagaId}")
    public ResponseEntity<List<Entrevista>> listarEntrevistasPorVaga(@PathVariable Long vagaId) {
        List<Entrevista> entrevistas = entrevistaService.listarPorVaga(vagaId);
        return ResponseEntity.ok(entrevistas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrevista> buscarEntrevista(@PathVariable Long id) {
        return entrevistaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<Entrevista> atualizarEntrevista(@PathVariable Long id, @Valid @RequestBody Entrevista entrevista) {
        if (!entrevistaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        entrevista.setId(id);
        Entrevista atualizada = entrevistaService.salvarEntrevista(entrevista);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<Void> deletarEntrevista(@PathVariable Long id) {
        entrevistaService.deletarEntrevista(id);
        return ResponseEntity.noContent().build();
    }
}
