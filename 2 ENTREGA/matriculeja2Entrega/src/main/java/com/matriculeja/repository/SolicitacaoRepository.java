package com.matriculeja.repository;

import com.matriculeja.enums.NivelPrioridade;
import com.matriculeja.enums.StatusSolicitacao;
import com.matriculeja.model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório JPA para Solicitacao.
 * O Spring Data JPA gera automaticamente todas as queries.
 */
@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    Optional<Solicitacao> findByProtocolo(String protocolo);

    List<Solicitacao> findByStatus(StatusSolicitacao status);

    List<Solicitacao> findByCrechePreferencia(String creche);

    List<Solicitacao> findAllByOrderByPrioridadeAscDataCriacaoAsc();

    long countByPrioridade(NivelPrioridade prioridade);

    long countByStatus(StatusSolicitacao status);

    @Query("SELECT s.crechePreferencia, COUNT(s) FROM Solicitacao s GROUP BY s.crechePreferencia")
    List<Object[]> countByCrechePreferencia();
}
