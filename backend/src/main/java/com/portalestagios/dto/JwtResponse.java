package com.portalestagios.dto;

import com.portalestagios.model.Usuario;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private Usuario.Perfil perfil;

    public JwtResponse(String accessToken, Long id, String email, Usuario.Perfil perfil) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.perfil = perfil;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario.Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Usuario.Perfil perfil) {
        this.perfil = perfil;
    }
}
