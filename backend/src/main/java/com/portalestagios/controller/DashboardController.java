package com.portalestagios.controller;

import com.portalestagios.model.Vaga;
import com.portalestagios.repository.EmpresaRepository;
import com.portalestagios.repository.EstudanteRepository;
import com.portalestagios.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private VagaRepository vagaRepository;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Map<String, Object>> getDashboardAdmin() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalEmpresas", empresaRepository.count());
        stats.put("totalEstudantes", estudanteRepository.count());
        stats.put("totalVagasAbertas", vagaRepository.findByEncerradaFalse().size());
        stats.put("totalVagasEncerradas", vagaRepository.findAll().stream().filter(Vaga::getEncerrada).count());
        return ResponseEntity.ok(stats);
    }
}
