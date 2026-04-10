package matriculeja.model;

public enum StatusSolicitacao {
    AGUARDANDO_TRIAGEM("Aguardando Triagem"),
    EM_ANALISE("Em Análise"),
    VAGA_DISPONIVEL("Vaga Disponível"),
    MATRICULADO("Matriculado"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusSolicitacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
