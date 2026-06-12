package com.matriculeja.model;

import com.matriculeja.enums.NivelPrioridade;
import com.matriculeja.enums.StatusSolicitacao;
import com.matriculeja.enums.TurnoVaga;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "solicitacoes")
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String protocolo;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "nome",     column = @Column(name = "resp_nome")),
        @AttributeOverride(name = "cpf",      column = @Column(name = "resp_cpf")),
        @AttributeOverride(name = "telefone", column = @Column(name = "resp_telefone")),
        @AttributeOverride(name = "email",    column = @Column(name = "resp_email")),
        @AttributeOverride(name = "endereco", column = @Column(name = "resp_endereco"))
    })
    @NotNull(message = "Responsável é obrigatório")
    private Responsavel responsavel;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "nome",       column = @Column(name = "crianca_nome")),
        @AttributeOverride(name = "idadeAnos",  column = @Column(name = "crianca_idade")),
        @AttributeOverride(name = "cpfCrianca", column = @Column(name = "crianca_cpf"))
    })
    @NotNull(message = "Dados da criança são obrigatórios")
    private Crianca crianca;

    @NotBlank(message = "Creche de preferência é obrigatória")
    private String crechePreferencia;

    @NotNull(message = "Turno é obrigatório")
    @Enumerated(EnumType.STRING)
    private TurnoVaga turno;

    private boolean vulnerabilidadeSocial;
    private boolean maeTrabalhadora;
    private boolean rendaPerCapitaBaixa;
    private boolean familiaMonoparental;

    @Enumerated(EnumType.STRING)
    private NivelPrioridade prioridade;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status;

    private int posicaoNaFila;

    private LocalDateTime dataCriacao;

    @Column(length = 2000)
    private String justificativa;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "historico_status", joinColumns = @JoinColumn(name = "solicitacao_id"))
    @Column(name = "evento", length = 500)
    private List<String> historicoStatus = new ArrayList<>();

    public Solicitacao() {
        this.status = StatusSolicitacao.AGUARDANDO_ANALISE;
        this.dataCriacao = LocalDateTime.now();
    }

    public void adicionarHistorico(String evento) {
        historicoStatus.add(LocalDateTime.now() + " — " + evento);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProtocolo() { return protocolo; }
    public void setProtocolo(String protocolo) { this.protocolo = protocolo; }
    public Responsavel getResponsavel() { return responsavel; }
    public void setResponsavel(Responsavel responsavel) { this.responsavel = responsavel; }
    public Crianca getCrianca() { return crianca; }
    public void setCrianca(Crianca crianca) { this.crianca = crianca; }
    public String getCrechePreferencia() { return crechePreferencia; }
    public void setCrechePreferencia(String crechePreferencia) { this.crechePreferencia = crechePreferencia; }
    public TurnoVaga getTurno() { return turno; }
    public void setTurno(TurnoVaga turno) { this.turno = turno; }
    public boolean isVulnerabilidadeSocial() { return vulnerabilidadeSocial; }
    public void setVulnerabilidadeSocial(boolean v) { this.vulnerabilidadeSocial = v; }
    public boolean isMaeTrabalhadora() { return maeTrabalhadora; }
    public void setMaeTrabalhadora(boolean v) { this.maeTrabalhadora = v; }
    public boolean isRendaPerCapitaBaixa() { return rendaPerCapitaBaixa; }
    public void setRendaPerCapitaBaixa(boolean v) { this.rendaPerCapitaBaixa = v; }
    public boolean isFamiliaMonoparental() { return familiaMonoparental; }
    public void setFamiliaMonoparental(boolean v) { this.familiaMonoparental = v; }
    public NivelPrioridade getPrioridade() { return prioridade; }
    public void setPrioridade(NivelPrioridade prioridade) { this.prioridade = prioridade; }
    public StatusSolicitacao getStatus() { return status; }
    public void setStatus(StatusSolicitacao status) { this.status = status; }
    public int getPosicaoNaFila() { return posicaoNaFila; }
    public void setPosicaoNaFila(int posicaoNaFila) { this.posicaoNaFila = posicaoNaFila; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public List<String> getHistoricoStatus() { return historicoStatus; }
    public void setHistoricoStatus(List<String> historicoStatus) { this.historicoStatus = historicoStatus; }
    public String getJustificativa() { return justificativa; }
    public void setJustificativa(String justificativa) { this.justificativa = justificativa; }
}
