package com.matriculeja.enums;

/**
 * Representa os possíveis estados de uma solicitação de vaga.
 */
public enum StatusSolicitacao {
    AGUARDANDO_ANALISE("Aguardando análise"),
    EM_ANALISE("Em análise"),
    APROVADA("Aprovada"),
    INDEFERIDA("Indeferida");

    private final String descricao;

    StatusSolicitacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
