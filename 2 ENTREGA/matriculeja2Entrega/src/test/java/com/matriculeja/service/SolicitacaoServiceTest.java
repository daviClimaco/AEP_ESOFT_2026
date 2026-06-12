package com.matriculeja.service;

import com.matriculeja.enums.NivelPrioridade;
import com.matriculeja.enums.StatusSolicitacao;
import com.matriculeja.model.Solicitacao;
import com.matriculeja.repository.SolicitacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a camada de serviço do MatriculeJá.
 * Valida as regras de negócio: cálculo de prioridade e transições de status.
 */
class SolicitacaoServiceTest {

    private SolicitacaoService service;
    private SolicitacaoRepository repository;

    @BeforeEach
    void setup() {
        repository = new SolicitacaoRepository();
        service = new SolicitacaoService(repository);
    }

    @Test
    @DisplayName("Deve calcular prioridade ALTA quando vulnerabilidade + renda baixa")
    void deveCalcularPrioridadeAlta() {
        Solicitacao s = new Solicitacao();
        s.setVulnerabilidadeSocial(true);
        s.setRendaPerCapitaBaixa(true);

        NivelPrioridade prioridade = service.calcularPrioridade(s);

        assertEquals(NivelPrioridade.ALTA, prioridade);
    }

    @Test
    @DisplayName("Deve calcular prioridade MEDIA quando apenas mãe trabalhadora + família monoparental")
    void deveCalcularPrioridadeMedia() {
        Solicitacao s = new Solicitacao();
        s.setMaeTrabalhadora(true);
        s.setFamiliaMonoparental(true);

        NivelPrioridade prioridade = service.calcularPrioridade(s);

        assertEquals(NivelPrioridade.MEDIA, prioridade);
    }

    @Test
    @DisplayName("Deve calcular prioridade NORMAL quando nenhum critério informado")
    void deveCalcularPrioridadeNormal() {
        Solicitacao s = new Solicitacao();

        NivelPrioridade prioridade = service.calcularPrioridade(s);

        assertEquals(NivelPrioridade.NORMAL, prioridade);
    }

    @Test
    @DisplayName("Deve lançar exceção ao aprovar solicitação já aprovada")
    void deveLancarExcecaoAoAprovarJaAprovada() {
        Solicitacao s = new Solicitacao();
        s.setPrioridade(NivelPrioridade.NORMAL);
        s.setStatus(StatusSolicitacao.APROVADA);
        s.setCrechePreferencia("CMEI Teste");
        Solicitacao salva = repository.salvar(s);

        assertThrows(IllegalStateException.class,
                () -> service.aprovarSolicitacao(salva.getId()));
    }

    @Test
    @DisplayName("Deve aprovar solicitação em análise com sucesso")
    void deveAprovarSolicitacaoEmAnalise() {
        Solicitacao s = new Solicitacao();
        s.setPrioridade(NivelPrioridade.NORMAL);
        s.setStatus(StatusSolicitacao.EM_ANALISE);
        s.setCrechePreferencia("CMEI Teste");
        Solicitacao salva = repository.salvar(s);

        Solicitacao aprovada = service.aprovarSolicitacao(salva.getId());

        assertEquals(StatusSolicitacao.APROVADA, aprovada.getStatus());
    }

    @Test
    @DisplayName("Solicitação deve ter status AGUARDANDO_ANALISE ao ser criada")
    void deveIniciarComStatusAguardando() {
        Solicitacao s = new Solicitacao();
        assertEquals(StatusSolicitacao.AGUARDANDO_ANALISE, s.getStatus());
    }
}
