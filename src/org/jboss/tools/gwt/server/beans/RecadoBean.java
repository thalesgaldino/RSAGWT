package org.jboss.tools.gwt.server.beans;

import java.util.ArrayList;
import java.util.Collection;

import negocio.ProcessadorRecado;
import br.com.framework.Arq.Exception.NegocioException;
import dominio.Recado;
import dominio.UsuarioAcademico;

public class RecadoBean extends ControllerGWT<Recado> {
	
	private ProcessadorRecado p;
	private UsuarioAcademico usuario;
	private ArrayList<Recado> listaMeusRecados;
	
	public RecadoBean(){
		p = new ProcessadorRecado();
		listaMeusRecados = new ArrayList<Recado>();
		setDiretorio("/usuarioAcademico");
		inicialize();
	}
	
	@Override
	public String inicialize(){
		obj = new Recado();
		listaResultado = new ArrayList<Recado>();
		return null;
	}

	@Override
	public String atualizar() throws NegocioException {
		return null;
	}

	@Override
	public String cadastrar() throws NegocioException {
		obj.setRemetente((UsuarioAcademico) getUsuarioLogado());
		obj.getDestinatario().add(usuario);
		p.cadastrar(obj);
		return listar(usuario.getId());
	}
	
	@Override
	public String remover() throws NegocioException {
		obj.setId(getId());
		p.deletar(obj);
		return listar(getUsuarioLogado().getId());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String listar() throws NegocioException{
		inicialize();
		usuario = (UsuarioAcademico) getDao().findByPrimaryKey(getId(), UsuarioAcademico.class);
		listaResultado = (ArrayList)p.getMensagensRecebidasByUsuario(Recado.class, usuario);
		return forward(getDiretorio()+"/recados.jsp");
	}
	
	@SuppressWarnings("unchecked")
	private String listar(int idUsuario){
		inicialize();
		UsuarioAcademico usuario = (UsuarioAcademico) getDao().findByPrimaryKey(idUsuario, UsuarioAcademico.class);
		listaResultado = (ArrayList)p.getMensagensRecebidasByUsuario(Recado.class, usuario);
		return forward(getDiretorio()+"/recados.jsp");
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Recado> listaMeusRecados(){
		ArrayList<Recado> lista = (ArrayList)p.getMensagensRecebidasByUsuario(Recado.class, getUsuarioLogado());
		if(lista.size() <= 5){
			listaMeusRecados.addAll(lista);
			return listaMeusRecados;
		}
		else {
			for(int i=lista.size()-1; i>=lista.size()-5; i++)
				listaMeusRecados.add(lista.get(i));
			return listaMeusRecados;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String removerRecadoHome() throws NegocioException {
		obj.setId(getId());
		p.deletar(obj);
		inicialize();
		UsuarioAcademico usuario = (UsuarioAcademico) getDao().findByPrimaryKey(getUsuarioLogado().getId(), UsuarioAcademico.class);
		listaMeusRecados = (ArrayList)p.getMensagensRecebidasByUsuario(Recado.class, usuario);
		return forward(getDiretorio()+"/home.jsp");
	}

	@Override
	public boolean validate() {
		return false;
	}

	public ArrayList<Recado> getListaMeusRecados() {
		return (ArrayList<Recado>) listaMeusRecados();
	}

	public void setListaMeusRecados(ArrayList<Recado> listaMeusRecados) {
		this.listaMeusRecados = listaMeusRecados;
	}

}
