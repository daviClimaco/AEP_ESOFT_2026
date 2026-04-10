package matriculeja.model;

public class Crianca {

    private final String nome;
    private final int idadeMeses;
    private final boolean necessidadeEspecial;
    private final String descricaoNecessidade;

    public Crianca(String nome, int idadeMeses, boolean necessidadeEspecial, String descricaoNecessidade) {
        this.nome = nome;
        this.idadeMeses = idadeMeses;
        this.necessidadeEspecial = necessidadeEspecial;
        this.descricaoNecessidade = descricaoNecessidade;
    }

    public String getNome() {
        return nome;
    }

    public int getIdadeMeses() {
        return idadeMeses;
    }
    public boolean temNecessidadeEspecial() {
        return necessidadeEspecial;
    }

    public String getTipoVaga() {
        if (idadeMeses < 48) return "Creche";
        return "Pré-Escola";
    }

    @Override
    public String toString() {
        String tipo = getTipoVaga();
        String idade = (idadeMeses / 12) + " anos e " + (idadeMeses % 12) + " meses";
        String nee = necessidadeEspecial ? " | NEE: " + descricaoNecessidade : "";
        return nome + " | " + idade + " | " + tipo + nee;
    }
}
