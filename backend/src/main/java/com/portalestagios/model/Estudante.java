package com.portalestagios.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "estudantes")
public class Estudante extends Usuario {

    @NotBlank
    @Pattern(regexp = "\\d{11}")
    @Column(unique = true)
    private String cpf;

    @NotBlank
    private String curso;

    @ManyToMany
    @JoinTable(
        name = "estudante_area_interesse",
        joinColumns = @JoinColumn(name = "estudante_id"),
        inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private Set<AreaInteresse> areasInteresse;

    @OneToMany(mappedBy = "estudante")
    private Set<Inscricao> inscricoes;

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Set<AreaInteresse> getAreasInteresse() {
        return areasInteresse;
    }

    public void setAreasInteresse(Set<AreaInteresse> areasInteresse) {
        this.areasInteresse = areasInteresse;
    }

    public Set<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(Set<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }
}
