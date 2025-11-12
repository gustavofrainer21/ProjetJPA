package com.portalestagios.controller;

import com.portalestagios.model.Empresa;
import com.portalestagios.model.Estudante;
import com.portalestagios.model.Usuario;
import com.portalestagios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/empresas")
    public ResponseEntity<Empresa> cadastrarEmpresa(@Valid @RequestBody Empresa empresa) {
        Empresa novaEmpresa = usuarioService.salvarEmpresa(empresa);
        return ResponseEntity.ok(novaEmpresa);
    }

    @PostMapping("/estudantes")
    public ResponseEntity<Estudante> cadastrarEstudante(@Valid @RequestBody Estudante estudante) {
        Estudante novoEstudante = usuarioService.salvarEstudante(estudante);
        return ResponseEntity.ok(novoEstudante);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
