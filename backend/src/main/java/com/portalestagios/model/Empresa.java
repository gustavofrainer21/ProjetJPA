package com.portalestagios.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "empresas")
public class Empresa extends Usuario {

    @NotBlank
    @Pattern(regexp = "\\d{14}")
    @Column(unique = true)
    private String cnpj;

    @NotBlank
    private String endereco;

    @ManyToMany
    @JoinTable(
        name = "empresa_area_atuacao",
        joinColumns = @JoinColumn(name = "empresa_id"),
        inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private Set<AreaInteresse> areasAtuacao;

    @OneToMany(mappedBy = "empresa")
    private Set<Vaga> vagas;

    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Set<AreaInteresse> getAreasAtuacao() {
        return areasAtuacao;
    }

    public void setAreasAtuacao(Set<AreaInteresse> areasAtuacao) {
        this.areasAtuacao = areasAtuacao;
    }

    public Set<Vaga> getVagas() {
        return vagas;
    }

    public void setVagas(Set<Vaga> vagas) {
        this.vagas = vagas;
    }
}
