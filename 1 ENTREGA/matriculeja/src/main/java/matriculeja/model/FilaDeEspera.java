package matriculeja.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FilaDeEspera {

    private final List<Solicitacao> solicitacoes;

    public FilaDeEspera() {
        this.solicitacoes = new ArrayList<>();
    }

    public void adicionar(Solicitacao solicitacao) {
        solicitacoes.add(solicitacao);
    }

    public List<Solicitacao> listarOrdenadaPorPrioridade() {
        return solicitacoes.stream()
                .filter(s -> s.getStatusAtual() == StatusSolicitacao.AGUARDANDO_TRIAGEM
                          || s.getStatusAtual() == StatusSolicitacao.EM_ANALISE)
                .sorted(Comparator.comparingInt(s -> s.getPrioridade().getNivel()))
                .collect(Collectors.toList());
    }

    public List<Solicitacao> listarTodas() {
        return new ArrayList<>(solicitacoes);
    }

    public Solicitacao buscarPorProtocolo(String protocolo) {
        return solicitacoes.stream()
                .filter(s -> s.getProtocolo().equalsIgnoreCase(protocolo))
                .findFirst()
                .orElse(null);
    }

    public int totalNaFila() {
        return (int) solicitacoes.stream()
                .filter(s -> s.getStatusAtual() == StatusSolicitacao.AGUARDANDO_TRIAGEM
                          || s.getStatusAtual() == StatusSolicitacao.EM_ANALISE)
                .count();
    }
}
