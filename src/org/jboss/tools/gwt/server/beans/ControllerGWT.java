package org.jboss.tools.gwt.server.beans;

import java.util.Collection;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.framework.Arq.Controle.AbstractController;
import br.com.framework.Arq.Dominio.Usuario;
import br.com.framework.Arq.Exception.DAOException;
import br.com.framework.Arq.Exception.NegocioException;
import br.com.framework.Arq.Factory.GenericFactory;

public abstract class ControllerGWT<T> extends AbstractController {
	
	protected T obj;
	protected Collection<T> listaResultado;
	protected String diretorio;

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public Collection<T> getListaResultado() {
		return listaResultado;
	}

	public void setListaResultado(Collection<T> listaResultado) {
		this.listaResultado = listaResultado;
	}
	
	@SuppressWarnings("unchecked")
	public void getObjeto(){
		try {
			obj = (T)getDao().findByPrimaryKey(getId(), obj.getClass());
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public String inicialize(){
		return null;
	}
	
	public String sair(){
		GenericFactory.resetDadosAcesso();
		inicialize();
		return forward("/index.jsp");
	}
	
	public String preCadastrar() throws NegocioException{
		return forwardCadastro();
	}

	public abstract String cadastrar() throws NegocioException;
	
	public String preAtualizar() throws NegocioException{
		getObjeto();
		return forwardAtualiza();
	}

	public abstract String atualizar() throws NegocioException;
	
	public String preRemover() throws NegocioException{
		getObjeto();
		return forwardRemover();
	}

	public abstract String remover() throws NegocioException;

	@SuppressWarnings("unchecked")
	public String listar() throws NegocioException {
		try {
			listaResultado = (Collection<T>) getDao().findAll(obj.getClass());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return forwardLista();
	}
	
	public abstract boolean validate();
	
	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}
	
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public String getParameter(String nomeParametro){
		HttpServletRequest request = getRequest();
		return request.getParameter(nomeParametro);
	}
	
	public int getParameterInt(String nomeParametro){
		HttpServletRequest request = getRequest();
		return Integer.valueOf(request.getParameter(nomeParametro));
	}
	
	public double getParameterDouble(String nomeParametro){
		HttpServletRequest request = getRequest();
		return Double.valueOf(request.getParameter(nomeParametro));
	}
	
	public void setParameter(String nome, Object obj){
		getRequest().setAttribute(nome, obj);
	}
	
	public int getId(){
		return getParameterInt("id");
	}
	
	public String forward(String pagina) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.setViewRoot(context.getApplication().getViewHandler().createView(context, pagina));
        context.renderResponse();
        return null;
    }
	
	public String forwardCadastro() {
        return forward(diretorio+"/cadastro.jsp");
    }
	
	public String forwardAtualiza() {
        return forward(diretorio+"/atualiza.jsp");
    }
	
	public String forwardRemover() {
        return forward(diretorio+"/remove.jsp");
    }
	
	public String forwardBusca() {
        return forward(diretorio+"/busca.jsp");
    }
	
	public String forwardLista() {
        return forward(diretorio+"/lista.jsp");
    }
	
	@SuppressWarnings("unchecked")
	public void setUsuarioLogado(Usuario usuario){
		GenericFactory.getDadosAcesso().setUsuarioLogado(usuario);
	}
	
	@SuppressWarnings("unchecked")
	public Usuario getUsuarioLogado(){
		return GenericFactory.getDadosAcesso().getUsuarioLogado();
	}

}
