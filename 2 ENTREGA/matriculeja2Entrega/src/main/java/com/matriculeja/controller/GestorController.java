package com.matriculeja.controller;

import com.matriculeja.dto.DashboardDTO;
import com.matriculeja.service.SolicitacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para o painel do gestor.
 * Fornece métricas e visão geral do sistema.
 */
@RestController
@RequestMapping("/api/gestor")
public class GestorController {

    private final SolicitacaoService service;

    public GestorController(SolicitacaoService service) {
        this.service = service;
    }

    /**
     * GET /api/gestor/dashboard
     * Retorna as métricas consolidadas para o painel do gestor:
     * total de solicitações, vagas preenchidas, fila de espera,
     * distribuição por prioridade e por creche.
     */
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> dashboard() {
        DashboardDTO dashboard = service.gerarDashboard();
        return ResponseEntity.ok(dashboard);
    }
}
