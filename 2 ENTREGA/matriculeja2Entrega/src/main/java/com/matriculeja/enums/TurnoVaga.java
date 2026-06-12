package com.matriculeja.enums;

/**
 * Turno desejado para a vaga na creche.
 */
public enum TurnoVaga {
    MANHA("Manhã"),
    TARDE("Tarde"),
    INTEGRAL("Integral");

    private final String descricao;

    TurnoVaga(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
