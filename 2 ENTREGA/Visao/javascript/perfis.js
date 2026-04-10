function abrirModal(tipo) {
    const modal = document.getElementById("modal");

    const dados = {
        /*perfil 1*/
        maria: {
            nome: "Maria",
            descricao: "Mãe solteira, baixa renda e pouca familiaridade com tecnologia.",
            contexto: "Trabalha em período integral e depende da creche para sustentar a família.",
            objetivo: "Conseguir vaga com previsibilidade.",
            problema: "Falta de transparência na fila.",
            restricoes: "Pouco tempo, acesso limitado à internet.",
            acessibilidade: "Dificuldade com sistemas digitais.",
            medo: "Não conseguir vaga e perder o emprego.",
            img: "imgs/perfil1/1maria.jpg"
        },

        juliana: {
            nome: "Juliana",
            descricao: "Usuária com dificuldade em utilizar sistemas digitais.",
            contexto: "Tem baixa escolaridade e pouco contato com tecnologia.",
            objetivo: "Realizar cadastro de forma simples.",
            problema: "Sistema complicado e burocrático.",
            restricoes: "Dificuldade de navegação e entendimento.",
            acessibilidade: "Precisa de interface simples e clara.",
            medo: "Errar no cadastro e perder a vaga.",
            img: "imgs/perfil1/2juliana.jpg"
        },

        ana: {
            nome: "Ana",
            descricao: "Responsável que enfrenta problemas com documentação.",
            contexto: "Precisa ir várias vezes presencialmente para resolver pendências.",
            objetivo: "Resolver tudo de forma digital.",
            problema: "Processo híbrido confuso e retrabalho.",
            restricoes: "Tempo limitado e burocracia.",
            acessibilidade: "Precisa de instruções claras.",
            medo: "Perder prazo por falta de informação.",
            img: "imgs/perfil1/3ana.jpeg"
        },

        /*perfil 2*/
        marcos: {
            nome: "Marcos",
            descricao: "Atendente da secretaria de educação com alta demanda de solicitações.",
            contexto: "Lida diariamente com grande volume de atendimentos e sistemas lentos e desorganizados.",
            objetivo: "Reduzir o estresse e ter mais controle sobre as atividades.",
            problema: "Sistemas ineficientes que geram retrabalho e aumentam o risco de erros.",
            restricoes: "Tempo limitado e excesso de demandas simultâneas.",
            acessibilidade: "Precisa de sistema rápido, intuitivo e bem organizado.",
            medo: "Cometer erros no atendimento e prejudicar a população.",
            img: "imgs/perfil2/1marcos.jpeg"
        },

        fernanda: {
            nome: "Fernanda",
            descricao: "Profissional administrativa organizada e cuidadosa com dados.",
            contexto: "Atua no setor administrativo e precisa lidar com grande volume de informações diariamente.",
            objetivo: "Trabalhar com mais eficiência e reduzir erros.",
            problema: "Sistema atual não atende às necessidades, gerando uso de planilhas paralelas.",
            restricoes: "Tempo limitado e dependência de processos manuais.",
            acessibilidade: "Precisa de sistema intuitivo e centralizado.",
            medo: "Perder informações ou gerar inconsistências nos dados.",
            img: "imgs/perfil2/2fernanda.jpeg"
        },

        joao: {
            nome: "João",
            descricao: "Servidor público em fase de adaptação no cargo.",
            contexto: "Iniciou recentemente no setor e ainda está aprendendo os processos e sistemas.",
            objetivo: "Ganhar confiança e se adaptar rapidamente ao trabalho.",
            problema: "Dificuldade em entender processos e utilizar sistemas complexos.",
            restricoes: "Falta de experiência e dependência de colegas mais experientes.",
            acessibilidade: "Precisa de sistema intuitivo e orientado.",
            medo: "Cometer erros e não conseguir acompanhar as demandas.",
            img: "imgs/perfil2/3joao.png"
        },

        /*perfil3*/
        carla: {
            nome: "Carla",
            descricao: "Gestora estratégica responsável pela educação infantil no município.",
            contexto: "Coordena a distribuição de vagas e o planejamento para atender a demanda da população.",
            objetivo: "Tomar decisões mais seguras e justas.",
            problema: "Falta de dados atualizados e consolidados para embasar decisões.",
            restricoes: "Alta responsabilidade e dependência de informações incompletas.",
            acessibilidade: "Precisa de relatórios claros e visão geral da demanda.",
            medo: "Tomar decisões injustas por falta de informação.",
            img: "imgs/perfil3/1carla.png"
        },

        roberto: {
            nome: "Roberto",
            descricao: "Gestor público que lida com alta pressão por vagas.",
            contexto: "Atua na gestão da educação, enfrentando pressão política e social constante.",
            objetivo: "Manter credibilidade e tomar decisões seguras.",
            problema: "Falta de dados claros e organizados para justificar decisões.",
            restricoes: "Alta responsabilidade e cobrança constante.",
            acessibilidade: "Precisa de relatórios claros e dashboards objetivos.",
            medo: "Tomar decisões sem base e perder credibilidade.",
            img: "imgs/perfil3/2roberto.jpeg"
        },

        patricia: {
            nome: "Patrícia",
            descricao: "Gestora operacional responsável pelo acompanhamento das vagas.",
            contexto: "Atua na gestão operacional, monitorando diariamente solicitações e disponibilidade nas unidades.",
            objetivo: "Trabalhar com mais agilidade e melhorar a eficiência do processo.",
            problema: "Processo manual e demorado para identificar vagas e demandas.",
            restricoes: "Alto volume de dados e falta de automação.",
            acessibilidade: "Precisa de visualização clara e dados em tempo real.",
            medo: "Tomar decisões lentas ou ineficientes que prejudiquem o sistema.",
            img: "imgs/perfil3/3patricia.png"
        }

    };

    const p = dados[tipo];
    document.getElementById("nomePersona").innerText = p.nome;
    document.getElementById("descricaoPersona").innerText = p.descricao;
    document.getElementById("contextoPersona").innerText = p.contexto;
    document.getElementById("objetivoPersona").innerText = p.objetivo;
    document.getElementById("problemaPersona").innerText = p.problema;
    document.getElementById("restricoesPersona").innerText = p.restricoes;
    document.getElementById("acessibilidadePersona").innerText = p.acessibilidade;
    document.getElementById("medoPersona").innerText = p.medo;

    document.getElementById("imgPersona").src = p.img;

    //modal.style.display = "flex";

    modal.style.display = "block";
}

function fecharModal() {
    document.getElementById("modal").style.display = "none";
}

window.onclick = function(event) {
    const modal = document.getElementById("modal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
};