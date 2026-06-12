package com.matriculeja.controller;

import com.matriculeja.dto.SolicitacaoRequestDTO;
import com.matriculeja.enums.StatusSolicitacao;
import com.matriculeja.model.Solicitacao;
import com.matriculeja.service.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService service;

    public SolicitacaoController(SolicitacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Solicitacao> criar(@Valid @RequestBody SolicitacaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarSolicitacao(dto));
    }

    @GetMapping
    public ResponseEntity<List<Solicitacao>> listarTodas(
            @RequestParam(required = false) StatusSolicitacao status) {
        List<Solicitacao> lista = (status != null)
                ? service.listarPorStatus(status) : service.listarTodas();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/protocolo/{protocolo}")
    public ResponseEntity<Solicitacao> buscarPorProtocolo(@PathVariable String protocolo) {
        return ResponseEntity.ok(service.buscarPorProtocolo(protocolo));
    }

    @PatchMapping("/{id}/analisar")
    public ResponseEntity<Solicitacao> iniciarAnalise(@PathVariable Long id) {
        return ResponseEntity.ok(service.iniciarAnalise(id));
    }

    @PatchMapping("/{id}/aprovar")
    public ResponseEntity<Solicitacao> aprovar(@PathVariable Long id) {
        return ResponseEntity.ok(service.aprovarSolicitacao(id));
    }

    @PatchMapping("/{id}/indeferir")
    public ResponseEntity<Solicitacao> indeferir(@PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String justificativa = body.getOrDefault("justificativa", "Não informada");
        return ResponseEntity.ok(service.indeferirSolicitacao(id, justificativa));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
    }
}
