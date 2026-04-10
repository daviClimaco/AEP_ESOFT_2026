function abrirModal(tipo) {
    const modal = document.getElementById("modal");

    const dados = {
        maria: {
            nome: "Maria",
            descricao: "Mãe solteira que depende da creche para trabalhar.",
            objetivo: "Conseguir vaga com previsibilidade.",
            problema: "Falta de transparência na fila.",
            img: "imgs/maria.jpg"
        },
        juliana: {
            nome: "Juliana",
            descricao: "Dificuldade com tecnologia e cadastro.",
            objetivo: "Cadastrar sem dificuldades.",
            problema: "Sistema complicado e burocrático.",
            img: "imgs/juliana.png"
        },
        ana: {
            nome: "Ana",
            descricao: "Dificuldade com documentação e processo híbrido.",
            objetivo: "Evitar idas presenciais repetidas.",
            problema: "Processo confuso e retrabalho.",
            img: "imgs/ana.png"
        }
    };

    document.getElementById("nomePersona").innerText = dados[tipo].nome;
    document.getElementById("descricaoPersona").innerText = dados[tipo].descricao;
    document.getElementById("objetivoPersona").innerText = dados[tipo].objetivo;
    document.getElementById("problemaPersona").innerText = dados[tipo].problema;
    document.getElementById("imgPersona").src = dados[tipo].img;

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