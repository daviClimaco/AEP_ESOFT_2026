package matriculeja.ui;

import matriculeja.model.*;
import matriculeja.service.ServicoSolicitacoes;

import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

    private static final ServicoSolicitacoes servico = new ServicoSolicitacoes();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        exibirBanner();
        boolean executando = true;

        while (executando) {
            exibirMenuPrincipal();
            int opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> fluxoCidadao();
                case 2 -> fluxoGestor();
                case 3 -> consultarProtocolo();
                case 0 -> executando = encerrar();
                default -> System.out.println("\n  [!] Opção inválida. Tente novamente.\n");
            }
        }
    }

    // ─── MENUS ────────────────────────────────────────────────────────────────

    private static void exibirBanner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║         MatriculeJá — v1.0 (Beta)           ║");
        System.out.println("  ║  Sistema de Vagas em Creches e Escolas       ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("  ┌─────────────────────────────────┐");
        System.out.println("  │         MENU PRINCIPAL          │");
        System.out.println("  ├─────────────────────────────────┤");
        System.out.println("  │  1. Área do Cidadão             │");
        System.out.println("  │  2. Área do Gestor              │");
        System.out.println("  │  3. Consultar por Protocolo     │");
        System.out.println("  │  0. Sair                        │");
        System.out.println("  └─────────────────────────────────┘");
    }

    // ─── FLUXO CIDADÃO ────────────────────────────────────────────────────────

    private static void fluxoCidadao() {
        System.out.println("\n  === ÁREA DO CIDADÃO ===");
        System.out.println("  1. Nova solicitação de vaga");
        System.out.println("  2. Consultar minha solicitação");
        System.out.print("  Opção: ");

        int opcao = lerInteiro("");
        if (opcao == 1) novasolicitacao();
        else if (opcao == 2) consultarProtocolo();
    }

    private static void novasolicitacao() {
        System.out.println("\n  --- NOVA SOLICITAÇÃO ---");

        System.out.print("  Deseja se identificar? (s/n): ");
        boolean identificado = scanner.nextLine().trim().equalsIgnoreCase("s");

        String nome = "Não informado";
        String cpf = "000.000.000-00";
        String telefone = "Não informado";

        if (identificado) {
            System.out.print("  Nome completo: ");
            nome = lerTexto();
            System.out.print("  CPF: ");
            cpf = lerTexto();
            System.out.print("  Telefone: ");
            telefone = lerTexto();
        }

        Responsavel responsavel = new Responsavel(nome, cpf, telefone, !identificado);

        System.out.print("\n  Nome da criança: ");
        String nomeCrianca = lerTexto();
        System.out.print("  Idade (em meses): ");
        int idadeMeses = lerInteiro("");
        System.out.print("  Possui necessidade especial? (s/n): ");
        boolean nee = scanner.nextLine().trim().equalsIgnoreCase("s");
        String descNee = "";
        if (nee) {
            System.out.print("  Descreva a necessidade: ");
            descNee = lerTexto();
        }

        Crianca crianca = new Crianca(nomeCrianca, idadeMeses, nee, descNee);

        System.out.println("\n  Selecione a prioridade:");
        System.out.println("  1. Alta (Vulnerabilidade Social)");
        System.out.println("  2. Média (Renda Baixa)");
        System.out.println("  3. Normal");
        int prio = lerInteiro("  Opção: ");
        Prioridade prioridade = switch (prio) {
            case 1 -> Prioridade.ALTA;
            case 2 -> Prioridade.MEDIA;
            default -> Prioridade.NORMAL;
        };

        Solicitacao solicitacao = servico.criarSolicitacao(responsavel, crianca, prioridade);

        System.out.println("\n  ✔ Solicitação registrada com sucesso!");
        System.out.println("  Guarde seu protocolo: " + solicitacao.getProtocolo());
        System.out.println("  Tipo de vaga: " + crianca.getTipoVaga());
        System.out.println("  Posição estimada na fila: " + servico.totalNaFila() + "ª");
        System.out.println();
    }

    // ─── FLUXO GESTOR ─────────────────────────────────────────────────────────

    private static void fluxoGestor() {
        System.out.println("\n  === ÁREA DO GESTOR ===");
        System.out.println("  1. Ver fila por prioridade");
        System.out.println("  2. Atualizar status de solicitação");
        System.out.println("  3. Listar todas as solicitações");
        int opcao = lerInteiro("  Opção: ");

        switch (opcao) {
            case 1 -> listarFila();
            case 2 -> atualizarStatus();
            case 3 -> listarTodas();
            default -> System.out.println("  Opção inválida.");
        }
    }

    private static void listarFila() {
        List<Solicitacao> fila = servico.listarFilaPorPrioridade();
        System.out.println("\n  === FILA DE ESPERA (" + fila.size() + " solicitações) ===");

        if (fila.isEmpty()) {
            System.out.println("  Nenhuma solicitação na fila.");
            return;
        }

        int pos = 1;
        for (Solicitacao s : fila) {
            System.out.println("  " + pos++ + ". [" + s.getProtocolo() + "] "
                    + s.getCrianca().getNome() + " | "
                    + s.getCrianca().getTipoVaga() + " | "
                    + s.getPrioridade().getDescricao() + " | "
                    + s.getStatusAtual().getDescricao());
        }
        System.out.println();
    }

    private static void listarTodas() {
        List<Solicitacao> todas = servico.listarTodas();
        System.out.println("\n  === TODAS AS SOLICITAÇÕES (" + todas.size() + ") ===");
        todas.forEach(s -> System.out.println("  [" + s.getProtocolo() + "] "
                + s.getCrianca().getNome() + " — " + s.getStatusAtual().getDescricao()));
        System.out.println();
    }

    private static void atualizarStatus() {
        System.out.print("  Protocolo: ");
        String protocolo = lerTexto();

        System.out.println("  Novo status:");
        System.out.println("  1. Em Análise");
        System.out.println("  2. Vaga Disponível");
        System.out.println("  3. Matriculado");
        System.out.println("  4. Cancelado");
        int opcao = lerInteiro("  Opção: ");

        StatusSolicitacao novoStatus = switch (opcao) {
            case 1 -> StatusSolicitacao.EM_ANALISE;
            case 2 -> StatusSolicitacao.VAGA_DISPONIVEL;
            case 3 -> StatusSolicitacao.MATRICULADO;
            case 4 -> StatusSolicitacao.CANCELADO;
            default -> null;
        };

        if (novoStatus == null) { System.out.println("  Opção inválida."); return; }

        System.out.print("  Comentário obrigatório: ");
        String comentario = lerTexto();
        System.out.print("  Seu nome (responsável): ");
        String responsavelAcao = lerTexto();

        try {
            boolean ok = servico.atualizarStatus(protocolo, novoStatus, comentario, responsavelAcao);
            System.out.println(ok ? "  ✔ Status atualizado com sucesso!" : "  [!] Protocolo não encontrado.");
        } catch (IllegalStateException e) {
            System.out.println("  [!] " + e.getMessage());
        }
        System.out.println();
    }

    // ─── CONSULTA ─────────────────────────────────────────────────────────────

    private static void consultarProtocolo() {
        System.out.print("\n  Informe o protocolo (ex: MJ-XXXXXXXX): ");
        String protocolo = lerTexto();
        Solicitacao solicitacao = servico.buscarPorProtocolo(protocolo);

        if (solicitacao == null) {
            System.out.println("  [!] Protocolo não encontrado.\n");
        } else {
            solicitacao.exibirDetalhes();
        }
    }

    // ─── UTILITÁRIOS ──────────────────────────────────────────────────────────

    private static String lerTexto() {
        return scanner.nextLine().trim();
    }

    private static int lerInteiro(String prompt) {
        if (!prompt.isEmpty()) System.out.print(prompt);
        try {
            int valor = Integer.parseInt(scanner.nextLine().trim());
            return valor;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean encerrar() {
        System.out.println("\n  Obrigado por usar o MatriculeJá. Até logo!\n");
        return false;
    }
}
