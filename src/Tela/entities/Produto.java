package Tela.entities;

public class Produto {
    private String codigoBarras;
    private String nome;
    private int quantidade;
    private String validade;

    public Produto(String codigoBarras, String nome, int quantidade, String validade) {
        this.codigoBarras = codigoBarras;
        this.nome = nome;
        this.quantidade = quantidade;
        this.validade = validade;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return nome + " (Qtd: " + quantidade + ", Val: " + validade + ")";
    }
}
