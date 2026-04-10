# MatriculeJá 🏫

Sistema de Gestão de Vagas em Creches e Escolas Públicas  
**AEP — Engenharia de Software — ESOFT5S — 2026**

---

## 📋 Sobre o Projeto

O **MatriculeJá** é um sistema desenvolvido para facilitar o acesso de famílias a vagas em creches e escolas públicas municipais, com transparência e equidade. O projeto é orientado pelos ODS 10, 11 e 16 da ONU.

---

## 🗂️ Estrutura do Projeto

```
src/main/java/matriculeja/
├── model/
│   ├── Responsavel.java        # Dados do responsável pela criança
│   ├── Crianca.java            # Dados da criança (idade, NEE, tipo de vaga)
│   ├── Solicitacao.java        # Solicitação de vaga com protocolo e histórico
│   ├── HistoricoStatus.java    # Registro de cada movimentação de status
│   ├── FilaDeEspera.java       # Gerencia a fila ordenada por prioridade
│   ├── StatusSolicitacao.java  # Enum dos status possíveis
│   └── Prioridade.java         # Enum de prioridade com nível de ordenação
├── service/
│   └── ServicoSolicitacoes.java # Regras de negócio e orquestração
└── ui/
    └── MenuPrincipal.java       # Interface CLI com menus interativos
```

---

## ▶️ Como Executar

**Pré-requisito:** Java 17+

```bash
# Compilar
find src -name "*.java" > sources.txt
javac -d out @sources.txt

# Executar
java -cp out matriculeja.ui.MenuPrincipal
```

---

## ⚙️ Funcionalidades (Beta)

- ✅ Cadastro de solicitação (identificado ou anônimo)
- ✅ Geração automática de protocolo único
- ✅ Classificação por prioridade (vulnerabilidade social, renda, normal)
- ✅ Consulta por protocolo
- ✅ Histórico de movimentações com data e responsável
- ✅ Painel do gestor: fila ordenada, atualização de status com comentário obrigatório
- ✅ Validação de transição de status

---

## 🔄 Fluxo de Status

```
AGUARDANDO_TRIAGEM → EM_ANALISE → VAGA_DISPONIVEL → MATRICULADO
                                                   → CANCELADO
```

---

## 👥 ODS Relacionados

| ODS | Relação |
|-----|---------|
| ODS 10 | Acesso igualitário ao processo de matrícula |
| ODS 11 | Gestão eficiente de serviço urbano essencial |
| ODS 16 | Transparência e rastreabilidade institucional |
