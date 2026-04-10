package matriculeja.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Solicitacao {

    private final String protocolo;
    private final Responsavel responsavel;
    private final Crianca crianca;
    private final Prioridade prioridade;
    private final LocalDateTime dataCriacao;
    private StatusSolicitacao statusAtual;
    private final List<HistoricoStatus> historico;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Solicitacao(Responsavel responsavel, Crianca crianca, Prioridade prioridade) {
        this.protocolo = gerarProtocolo();
        this.responsavel = responsavel;
        this.crianca = crianca;
        this.prioridade = prioridade;
        this.dataCriacao = LocalDateTime.now();
        this.statusAtual = StatusSolicitacao.AGUARDANDO_TRIAGEM;
        this.historico = new ArrayList<>();
        registrarHistorico(StatusSolicitacao.AGUARDANDO_TRIAGEM, "Solicitação criada.", "Sistema");
    }

    private String gerarProtocolo() {
        return "MJ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void atualizarStatus(StatusSolicitacao novoStatus, String comentario, String responsavelAcao) {
        this.statusAtual = novoStatus;
        registrarHistorico(novoStatus, comentario, responsavelAcao);
    }

    private void registrarHistorico(StatusSolicitacao status, String comentario, String responsavelAcao) {
        historico.add(new HistoricoStatus(status, comentario, responsavelAcao));
    }

    public String getProtocolo() { return protocolo; }
    public Responsavel getResponsavel() { return responsavel; }
    public Crianca getCrianca() { return crianca; }
    public Prioridade getPrioridade() { return prioridade; }
    public StatusSolicitacao getStatusAtual() { return statusAtual; }
    public List<HistoricoStatus> getHistorico() { return historico; }

    public void exibirDetalhes() {
        System.out.println("=".repeat(55));
        System.out.println("  PROTOCOLO : " + protocolo);
        System.out.println("  DATA      : " + dataCriacao.format(FORMATTER));
        System.out.println("  STATUS    : " + statusAtual.getDescricao());
        System.out.println("  PRIORIDADE: " + prioridade.getDescricao());
        System.out.println("-".repeat(55));
        System.out.println("  RESPONSÁVEL: " + responsavel);
        System.out.println("  CRIANÇA    : " + crianca);
        System.out.println("-".repeat(55));
        System.out.println("  HISTÓRICO:");
        historico.forEach(h -> System.out.println("    " + h));
        System.out.println("=".repeat(55));
    }
}
