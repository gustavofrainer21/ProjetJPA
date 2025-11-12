package com.portalestagios.controller;

import com.portalestagios.model.AreaInteresse;
import com.portalestagios.service.AreaInteresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/areas-interesse")
public class AreaInteresseController {

    @Autowired
    private AreaInteresseService areaInteresseService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<AreaInteresse> criarAreaInteresse(@Valid @RequestBody AreaInteresse areaInteresse) {
        AreaInteresse novaArea = areaInteresseService.salvarAreaInteresse(areaInteresse);
        return ResponseEntity.ok(novaArea);
    }

    @GetMapping
    public ResponseEntity<List<AreaInteresse>> listarAreasInteresse() {
        List<AreaInteresse> areas = areaInteresseService.listarTodas();
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaInteresse> buscarAreaInteresse(@PathVariable Long id) {
        return areaInteresseService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<AreaInteresse> atualizarAreaInteresse(@PathVariable Long id, @Valid @RequestBody AreaInteresse areaInteresse) {
        if (!areaInteresseService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        areaInteresse.setId(id);
        AreaInteresse atualizada = areaInteresseService.salvarAreaInteresse(areaInteresse);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> deletarAreaInteresse(@PathVariable Long id) {
        areaInteresseService.deletarAreaInteresse(id);
        return ResponseEntity.noContent().build();
    }
}
