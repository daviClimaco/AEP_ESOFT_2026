package com.matriculeja.dto;

import java.util.Map;

/**
 * DTO com as métricas gerais do sistema para o painel do gestor.
 */
public class DashboardDTO {

    private int totalSolicitacoes;
    private int vagasPreenchidas;
    private int naFilaDeEspera;
    private int altaPrioridade;
    private Map<String, Long> solicitacoesPorCreche;
    private Map<String, Long> distribuicaoPorPrioridade;

    public DashboardDTO() {}

    public DashboardDTO(int totalSolicitacoes, int vagasPreenchidas, int naFilaDeEspera,
                        int altaPrioridade, Map<String, Long> solicitacoesPorCreche,
                        Map<String, Long> distribuicaoPorPrioridade) {
        this.totalSolicitacoes = totalSolicitacoes;
        this.vagasPreenchidas = vagasPreenchidas;
        this.naFilaDeEspera = naFilaDeEspera;
        this.altaPrioridade = altaPrioridade;
        this.solicitacoesPorCreche = solicitacoesPorCreche;
        this.distribuicaoPorPrioridade = distribuicaoPorPrioridade;
    }

    // Getters e Setters
    public int getTotalSolicitacoes() { return totalSolicitacoes; }
    public void setTotalSolicitacoes(int v) { this.totalSolicitacoes = v; }

    public int getVagasPreenchidas() { return vagasPreenchidas; }
    public void setVagasPreenchidas(int v) { this.vagasPreenchidas = v; }

    public int getNaFilaDeEspera() { return naFilaDeEspera; }
    public void setNaFilaDeEspera(int v) { this.naFilaDeEspera = v; }

    public int getAltaPrioridade() { return altaPrioridade; }
    public void setAltaPrioridade(int v) { this.altaPrioridade = v; }

    public Map<String, Long> getSolicitacoesPorCreche() { return solicitacoesPorCreche; }
    public void setSolicitacoesPorCreche(Map<String, Long> v) { this.solicitacoesPorCreche = v; }

    public Map<String, Long> getDistribuicaoPorPrioridade() { return distribuicaoPorPrioridade; }
    public void setDistribuicaoPorPrioridade(Map<String, Long> v) { this.distribuicaoPorPrioridade = v; }
}
