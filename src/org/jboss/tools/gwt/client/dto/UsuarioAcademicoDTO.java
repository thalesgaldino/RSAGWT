package org.jboss.tools.gwt.client.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UsuarioAcademicoDTO implements Serializable{
	
	private int id;
	private String nome;
	private Date dataNascimento;
	private String universidade;
	private String curso;
	private String email;
	private String senha;
	private List<UsuarioAcademicoDTO> contatos;
	private List<UsuarioAcademicoDTO> solicitacaoContato;
	private boolean loggedIn;
	
	public UsuarioAcademicoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getUniversidade() {
		return universidade;
	}

	public void setUniversidade(String universidade) {
		this.universidade = universidade;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<UsuarioAcademicoDTO> getContatos() {
		return contatos;
	}

	public void setContatos(List<UsuarioAcademicoDTO> contatos) {
		this.contatos = contatos;
	}

	public List<UsuarioAcademicoDTO> getSolicitacaoContato() {
		return solicitacaoContato;
	}

	public void setSolicitacaoContato(List<UsuarioAcademicoDTO> solicitacaoContato) {
		this.solicitacaoContato = solicitacaoContato;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean b) {
		this.loggedIn = b;
	}
	
}
