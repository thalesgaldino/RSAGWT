package org.jboss.tools.gwt.client.dto;

import java.io.Serializable;

public class AlunoDTO implements Serializable{
	
	private int alunoId;
	private String nome;
	private int idade;
	private boolean loggedIn;
	private String nickname;
	private String mail;
	
	public int getAlunoId() {
		return alunoId;
	}

	public void setAlunoId(int alunoId) {
		this.alunoId = alunoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public AlunoDTO() {
	
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean b) {
		this.loggedIn = b;
	}
	
	public void setNickname(String string) {
		this.nickname = string;
	}

	public String getNickname() {
		return nickname;
	}
	
	public void seteMail(String string) {
		this.mail = string;
	}		
	
	public String geteMail() {
		return mail;
	}
	
}
