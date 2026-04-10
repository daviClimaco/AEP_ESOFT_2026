package matriculeja.model;

public class Responsavel {

    private final String nome;
    private final String cpf;
    private final String telefone;
    private final boolean anonimo;

    public Responsavel(String nome, String cpf, String telefone, boolean anonimo) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.anonimo = anonimo;
    }

    public String getNome() {
        return anonimo ? "Anônimo" : nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean isAnonimo() {
        return anonimo;
    }

    @Override
    public String toString() {
        return "Responsável: " + getNome() + (anonimo ? " (cadastro anônimo)" : " | CPF: " + cpf);
    }
}
