package com.portalestagios.service;

import com.portalestagios.model.*;
import com.portalestagios.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Empresa salvarEmpresa(Empresa empresa) {
        empresa.setPerfil(Usuario.Perfil.EMPRESA);
        return empresaRepository.save((Empresa) salvarUsuario(empresa));
    }

    public Estudante salvarEstudante(Estudante estudante) {
        estudante.setPerfil(Usuario.Perfil.ESTUDANTE);
        return estudanteRepository.save((Estudante) salvarUsuario(estudante));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
