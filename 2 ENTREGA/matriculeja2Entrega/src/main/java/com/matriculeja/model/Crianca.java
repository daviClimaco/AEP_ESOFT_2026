package com.matriculeja.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Embeddable
public class Crianca {

    @NotBlank(message = "Nome da criança é obrigatório")
    private String nome;

    @NotNull(message = "Idade é obrigatória")
    @Min(0) @Max(5)
    private Integer idadeAnos;

    @NotBlank(message = "CPF da criança é obrigatório")
    private String cpfCrianca;

    public Crianca() {}

    public Crianca(Long id, String nome, Integer idadeAnos, String cpf, Long responsavelId) {
        this.nome = nome;
        this.idadeAnos = idadeAnos;
        this.cpfCrianca = cpf;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getIdadeAnos() { return idadeAnos; }
    public void setIdadeAnos(Integer idadeAnos) { this.idadeAnos = idadeAnos; }
    public String getCpf() { return cpfCrianca; }
    public void setCpf(String cpf) { this.cpfCrianca = cpf; }
}
