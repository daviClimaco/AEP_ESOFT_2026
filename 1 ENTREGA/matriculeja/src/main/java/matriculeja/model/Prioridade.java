package matriculeja.model;

public enum Prioridade {
    ALTA("Alta - Vulnerabilidade Social", 1),
    MEDIA("Média - Renda Baixa", 2),
    NORMAL("Normal", 3);

    private final String descricao;
    private final int nivel;

    Prioridade(String descricao, int nivel) {
        this.descricao = descricao;
        this.nivel = nivel;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNivel() {
        return nivel;
    }
}
