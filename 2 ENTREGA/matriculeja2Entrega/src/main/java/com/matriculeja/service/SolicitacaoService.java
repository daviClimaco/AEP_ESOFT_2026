package com.matriculeja.service;

import com.matriculeja.dto.DashboardDTO;
import com.matriculeja.dto.SolicitacaoRequestDTO;
import com.matriculeja.enums.NivelPrioridade;
import com.matriculeja.enums.StatusSolicitacao;
import com.matriculeja.model.Crianca;
import com.matriculeja.model.Responsavel;
import com.matriculeja.model.Solicitacao;
import com.matriculeja.repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Camada de serviço do MatriculeJá.
 * Contém todas as regras de negócio.
 */
@Service
public class SolicitacaoService {

    private final SolicitacaoRepository repository;

    public SolicitacaoService(SolicitacaoRepository repository) {
        this.repository = repository;
    }

    /** Cria e registra uma nova solicitação de vaga. */
    public Solicitacao criarSolicitacao(SolicitacaoRequestDTO dto) {
        Solicitacao solicitacao = montarSolicitacao(dto);
        solicitacao.setPrioridade(calcularPrioridade(solicitacao));
        solicitacao.setProtocolo(gerarProtocolo());
        solicitacao.adicionarHistorico("Solicitação enviada com sucesso.");
        Solicitacao salva = repository.save(solicitacao);
        atualizarPosicoesFila();
        return salva;
    }

    /**
     * Calcula o nível de prioridade com base nos critérios sociais.
     * Vulnerabilidade social = 3 pts, Renda baixa = 2 pts,
     * Mãe trabalhadora = 2 pts, Família monoparental = 1 pt.
     */
    public NivelPrioridade calcularPrioridade(Solicitacao solicitacao) {
        int pontos = 0;
        if (solicitacao.isVulnerabilidadeSocial()) pontos += 3;
        if (solicitacao.isRendaPerCapitaBaixa())   pontos += 2;
        if (solicitacao.isMaeTrabalhadora())        pontos += 2;
        if (solicitacao.isFamiliaMonoparental())    pontos += 1;
        if (pontos >= 4) return NivelPrioridade.ALTA;
        if (pontos >= 2) return NivelPrioridade.MEDIA;
        return NivelPrioridade.NORMAL;
    }

    /** Gera protocolo único no formato ANO-NNNNN. */
    private String gerarProtocolo() {
        int ano = Year.now().getValue();
        long total = repository.count() + 1;
        return String.format("%d-%05d", ano, total);
    }

    /** Recalcula posições da fila respeitando prioridade e data. */
    public void atualizarPosicoesFila() {
        List<Solicitacao> fila = repository.findAllByOrderByPrioridadeAscDataCriacaoAsc()
                .stream()
                .filter(s -> s.getStatus() != StatusSolicitacao.APROVADA
                          && s.getStatus() != StatusSolicitacao.INDEFERIDA)
                .collect(Collectors.toList());
        for (int i = 0; i < fila.size(); i++) {
            fila.get(i).setPosicaoNaFila(i + 1);
            repository.save(fila.get(i));
        }
    }

    /** Inicia a análise de uma solicitação. */
    public Solicitacao iniciarAnalise(Long id) {
        Solicitacao s = buscarPorIdOuErro(id);
        s.setStatus(StatusSolicitacao.EM_ANALISE);
        s.adicionarHistorico("Análise iniciada pelo servidor.");
        return repository.save(s);
    }

    /** Aprova a solicitação. */
    public Solicitacao aprovarSolicitacao(Long id) {
        Solicitacao s = buscarPorIdOuErro(id);
        if (s.getStatus() == StatusSolicitacao.APROVADA)
            throw new IllegalStateException("Solicitação já está aprovada.");
        if (s.getStatus() == StatusSolicitacao.INDEFERIDA)
            throw new IllegalStateException("Não é possível aprovar uma solicitação indeferida.");
        s.setStatus(StatusSolicitacao.APROVADA);
        s.adicionarHistorico("Solicitação aprovada pelo servidor.");
        atualizarPosicoesFila();
        return repository.save(s);
    }

    /** Indefere a solicitação com justificativa. */
    public Solicitacao indeferirSolicitacao(Long id, String justificativa) {
        Solicitacao s = buscarPorIdOuErro(id);
        s.setStatus(StatusSolicitacao.INDEFERIDA);
        s.setJustificativa(justificativa);
        s.adicionarHistorico("Solicitação indeferida. Motivo: " + justificativa);
        atualizarPosicoesFila();
        return repository.save(s);
    }

    /** Busca solicitação por protocolo para acompanhamento do responsável. */
    public Solicitacao buscarPorProtocolo(String protocolo) {
        return repository.findByProtocolo(protocolo)
                .orElseThrow(() -> new NoSuchElementException("Protocolo não encontrado: " + protocolo));
    }

    public List<Solicitacao> listarTodas() {
        return repository.findAll();
    }

    public List<Solicitacao> listarPorStatus(StatusSolicitacao status) {
        return repository.findByStatus(status);
    }

    public Solicitacao buscarPorId(Long id) {
        return buscarPorIdOuErro(id);
    }

    /** Gera métricas consolidadas para o painel do gestor. */
    public DashboardDTO gerarDashboard() {
        long aprovadas = repository.countByStatus(StatusSolicitacao.APROVADA);
        long total     = repository.count();
        long naFila    = total - aprovadas - repository.countByStatus(StatusSolicitacao.INDEFERIDA);
        long alta      = repository.countByPrioridade(NivelPrioridade.ALTA);

        Map<String, Long> porCreche = new HashMap<>();
        repository.countByCrechePreferencia()
                .forEach(row -> porCreche.put((String) row[0], (Long) row[1]));

        Map<String, Long> porPrioridade = Map.of(
                "ALTA",   repository.countByPrioridade(NivelPrioridade.ALTA),
                "MEDIA",  repository.countByPrioridade(NivelPrioridade.MEDIA),
                "NORMAL", repository.countByPrioridade(NivelPrioridade.NORMAL)
        );

        return new DashboardDTO((int) total, (int) aprovadas, (int) naFila,
                (int) alta, porCreche, porPrioridade);
    }

    private Solicitacao buscarPorIdOuErro(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Solicitação não encontrada: " + id));
    }

    private Solicitacao montarSolicitacao(SolicitacaoRequestDTO dto) {
        Responsavel responsavel = new Responsavel(null,
                dto.getNomeResponsavel(), dto.getCpfResponsavel(),
                dto.getTelefoneResponsavel(), dto.getEmailResponsavel(),
                dto.getEnderecoResponsavel());
        Crianca crianca = new Crianca(null,
                dto.getNomeCrianca(), dto.getIdadeCrianca(),
                dto.getCpfCrianca(), null);
        Solicitacao s = new Solicitacao();
        s.setResponsavel(responsavel);
        s.setCrianca(crianca);
        s.setCrechePreferencia(dto.getCrechePreferencia());
        s.setTurno(dto.getTurno());
        s.setVulnerabilidadeSocial(dto.isVulnerabilidadeSocial());
        s.setMaeTrabalhadora(dto.isMaeTrabalhadora());
        s.setRendaPerCapitaBaixa(dto.isRendaPerCapitaBaixa());
        s.setFamiliaMonoparental(dto.isFamiliaMonoparental());
        return s;
    }
}
