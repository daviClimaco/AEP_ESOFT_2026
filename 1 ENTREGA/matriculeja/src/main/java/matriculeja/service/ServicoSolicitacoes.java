package matriculeja.service;

import matriculeja.model.*;

import java.util.List;

public class ServicoSolicitacoes {

    private final FilaDeEspera fila;

    public ServicoSolicitacoes() {
        this.fila = new FilaDeEspera();
    }

    public Solicitacao criarSolicitacao(Responsavel responsavel, Crianca crianca, Prioridade prioridade) {
        Solicitacao solicitacao = new Solicitacao(responsavel, crianca, prioridade);
        fila.adicionar(solicitacao);
        return solicitacao;
    }

    public Solicitacao buscarPorProtocolo(String protocolo) {
        return fila.buscarPorProtocolo(protocolo);
    }

    public List<Solicitacao> listarFilaPorPrioridade() {
        return fila.listarOrdenadaPorPrioridade();
    }

    public List<Solicitacao> listarTodas() {
        return fila.listarTodas();
    }

    public boolean atualizarStatus(String protocolo, StatusSolicitacao novoStatus,
                                    String comentario, String responsavelAcao) {
        Solicitacao solicitacao = buscarPorProtocolo(protocolo);
        if (solicitacao == null) return false;

        validarTransicaoStatus(solicitacao.getStatusAtual(), novoStatus);
        solicitacao.atualizarStatus(novoStatus, comentario, responsavelAcao);
        return true;
    }

    private void validarTransicaoStatus(StatusSolicitacao atual, StatusSolicitacao novo) {
        if (atual == StatusSolicitacao.MATRICULADO || atual == StatusSolicitacao.CANCELADO) {
            throw new IllegalStateException("Solicitação já encerrada. Status não pode ser alterado.");
        }
    }

    public int totalNaFila() {
        return fila.totalNaFila();
    }
}
