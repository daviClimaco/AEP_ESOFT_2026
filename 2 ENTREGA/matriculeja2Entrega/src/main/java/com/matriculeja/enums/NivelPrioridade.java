package com.matriculeja.enums;

/**
 * Nível de prioridade da solicitação, calculado automaticamente
 * com base nos critérios sociais informados.
 */
public enum NivelPrioridade {
    ALTA(1),
    MEDIA(2),
    NORMAL(3);

    private final int ordem;

    NivelPrioridade(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return ordem;
    }
}
