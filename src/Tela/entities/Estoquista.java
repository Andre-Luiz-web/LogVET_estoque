package Tela.entities;

import java.util.ArrayList;
import java.util.List;

public class Estoquista extends Pessoa {
    private String senha;
    private List<String> historicoOperacoes;

    public Estoquista(String nome, String email, String senha) {
        super(nome, email);
        this.senha = senha;
        this.historicoOperacoes = new ArrayList<>();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void registrarOperacao(String operacao) {
        historicoOperacoes.add(operacao);
        System.out.println("Operação registrada: " + operacao);
    }

    @Override
    public void exibirInfo() {
        System.out.println("Estoquista: " + getNome() + " - Email: " + getEmail());
    }

	@Override
	protected void setId(int int1) {
		// TODO Auto-generated method stub
		
	}
}
