package org.jboss.tools.gwt.server.beans;

import java.util.ArrayList;

import negocio.ProcessadorTurmaVirtual;
import br.com.framework.Arq.Exception.NegocioException;
import dominio.TurmaVirtual;
import dominio.UsuarioAcademico;

public class TurmaVirtualBean extends ControllerGWT<TurmaVirtual> {
	
	private ProcessadorTurmaVirtual p;
	private ArrayList<TurmaVirtual> turmasVirtuais;
	
	public TurmaVirtualBean(){
		p = new ProcessadorTurmaVirtual();
		setDiretorio("/turmaVirtual");
		inicialize();
	}
	
	@Override
	public String inicialize(){
		obj = new TurmaVirtual();
		return null;
	}
	
	public String home(){
		inicialize();
		getObjeto();
		p.getParticipantes(obj);
		return forward("/turmaVirtual/home.jsp");
	}

	@Override
	public String atualizar() throws NegocioException {
		getObjeto();
		inicialize();
		return null;
	}

	@Override
	public String cadastrar() throws NegocioException {
		obj.setCriador((UsuarioAcademico) getUsuarioLogado());
		p.cadastrar(obj);
		return listar();
	}

	@Override
	public String remover() throws NegocioException {
		getObjeto();
		p.deletar(obj);
		return listar();
	}
	
	@Override
	public String listar() throws NegocioException {
		inicialize();
		return forwardLista();
	}
	
	public String participar(){
		getObjeto();
		if(isGerenciaTurma())
			return null;
		p.participar(obj, getUsuarioLogado());
		return forward("/turmaVirtual/home.jsp");
	}
	
	public String sair(){
		getObjeto();
		if(isGerenciaTurma())
			return null;
		p.sair(obj, getUsuarioLogado());
		return forward("/turmaVirtual/home.jsp");
	}
	
	public String buscar(){
		turmasVirtuais = (ArrayList)p.getGruposByNome(obj);
		return forward("/turmaVirtual/buscar.jsp");
	}
	
	public boolean isParticipaTurma(){
		return p.verificaUsuarioParticipaGrupo(obj, getUsuarioLogado());
	}
	
	public boolean isGerenciaTurma(){
		return getUsuarioLogado().getId() == obj.getCriador().getId();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TurmaVirtual> getTurmasParticipo(){
		return (ArrayList)p.getGruposByUsuarioParticipa(TurmaVirtual.class, getUsuarioLogado());
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TurmaVirtual> getTurmasGerencio(){
		return (ArrayList)p.getGruposByUsuarioCriador(TurmaVirtual.class, getUsuarioLogado());
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TurmaVirtual> getTurmas(){
		return (ArrayList)p.getGruposByUsuarioCriador(TurmaVirtual.class, getUsuarioLogado());
	}

	@Override
	public boolean validate() {
		return false;
	}

	public ArrayList<TurmaVirtual> getTurmasVirtuais() {
		return turmasVirtuais;
	}

	public void setTurmasVirtuais(ArrayList<TurmaVirtual> turmasVirtuais) {
		this.turmasVirtuais = turmasVirtuais;
	}

}

