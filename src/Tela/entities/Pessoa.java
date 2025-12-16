package Tela.entities;

import java.time.LocalDateTime;

public abstract class Pessoa {
    private String nome;
    private String email;
    private LocalDateTime ultimoLogin;

    public Pessoa(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.ultimoLogin = null;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    // Método comum de autenticação de login
    public boolean autenticar(String senhaDigitada, String senhaReal) {
        if (senhaReal.equals(senhaDigitada)) {
            this.ultimoLogin = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public abstract void exibirInfo();

	public String getSenha() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	protected abstract void setId(int int1);

	protected abstract void setSenha(String string);
}