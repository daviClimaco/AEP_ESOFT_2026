package com.matriculeja.dto;

import com.matriculeja.enums.TurnoVaga;
import jakarta.validation.constraints.*;

/**
 * DTO (Data Transfer Object) para receber os dados de criação de uma nova solicitação.
 * Separa os dados recebidos pela API do modelo interno do sistema.
 */
public class SolicitacaoRequestDTO {

    // Dados do responsável
    @NotBlank(message = "Nome do responsável é obrigatório")
    private String nomeResponsavel;

    @NotBlank(message = "CPF do responsável é obrigatório")
    private String cpfResponsavel;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefoneResponsavel;

    private String emailResponsavel;

    @NotBlank(message = "Endereço é obrigatório")
    private String enderecoResponsavel;

    // Dados da criança
    @NotBlank(message = "Nome da criança é obrigatório")
    private String nomeCrianca;

    @NotNull(message = "Idade é obrigatória")
    @Min(0) @Max(5)
    private Integer idadeCrianca;

    @NotBlank(message = "CPF da criança é obrigatório")
    private String cpfCrianca;

    // Dados da vaga
    @NotBlank(message = "Creche de preferência é obrigatória")
    private String crechePreferencia;

    @NotNull(message = "Turno é obrigatório")
    private TurnoVaga turno;

    // Critérios sociais
    private boolean vulnerabilidadeSocial;
    private boolean maeTrabalhadora;
    private boolean rendaPerCapitaBaixa;
    private boolean familiaMonoparental;

    // Getters e Setters
    public String getNomeResponsavel() { return nomeResponsavel; }
    public void setNomeResponsavel(String v) { this.nomeResponsavel = v; }

    public String getCpfResponsavel() { return cpfResponsavel; }
    public void setCpfResponsavel(String v) { this.cpfResponsavel = v; }

    public String getTelefoneResponsavel() { return telefoneResponsavel; }
    public void setTelefoneResponsavel(String v) { this.telefoneResponsavel = v; }

    public String getEmailResponsavel() { return emailResponsavel; }
    public void setEmailResponsavel(String v) { this.emailResponsavel = v; }

    public String getEnderecoResponsavel() { return enderecoResponsavel; }
    public void setEnderecoResponsavel(String v) { this.enderecoResponsavel = v; }

    public String getNomeCrianca() { return nomeCrianca; }
    public void setNomeCrianca(String v) { this.nomeCrianca = v; }

    public Integer getIdadeCrianca() { return idadeCrianca; }
    public void setIdadeCrianca(Integer v) { this.idadeCrianca = v; }

    public String getCpfCrianca() { return cpfCrianca; }
    public void setCpfCrianca(String v) { this.cpfCrianca = v; }

    public String getCrechePreferencia() { return crechePreferencia; }
    public void setCrechePreferencia(String v) { this.crechePreferencia = v; }

    public TurnoVaga getTurno() { return turno; }
    public void setTurno(TurnoVaga v) { this.turno = v; }

    public boolean isVulnerabilidadeSocial() { return vulnerabilidadeSocial; }
    public void setVulnerabilidadeSocial(boolean v) { this.vulnerabilidadeSocial = v; }

    public boolean isMaeTrabalhadora() { return maeTrabalhadora; }
    public void setMaeTrabalhadora(boolean v) { this.maeTrabalhadora = v; }

    public boolean isRendaPerCapitaBaixa() { return rendaPerCapitaBaixa; }
    public void setRendaPerCapitaBaixa(boolean v) { this.rendaPerCapitaBaixa = v; }

    public boolean isFamiliaMonoparental() { return familiaMonoparental; }
    public void setFamiliaMonoparental(boolean v) { this.familiaMonoparental = v; }
}
