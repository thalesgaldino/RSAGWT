package org.jboss.tools.gwt.server.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jboss.tools.gwt.client.dto.AlunoDTO;

@Entity
public class Aluno {
	@Id
	@GeneratedValue
	private int id;
	private String nome;
	private int idade;
	
	/*Usado para criar um objeto da classe de entidade
	 * Aluno a partir de um objeto AlunoDTO
	 **/	
	public Aluno(){}
	
	public Aluno(AlunoDTO alunoDTO){
		setId(alunoDTO.getAlunoId());
		setNome(alunoDTO.getNome());
		setIdade(alunoDTO.getIdade());
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
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	
}
