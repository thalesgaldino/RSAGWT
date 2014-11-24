package org.jboss.tools.gwt.server.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import negocio.ProcessadorTurmaVirtual;
import negocio.ProcessadorUsuarioAcademico;
import negocio.StrategyConcreto;
import br.com.framework.Arq.Exception.NegocioException;
import br.com.framework.Arq.Negocio.ProcessadorConsulta;
import dominio.TurmaVirtual;
import dominio.UsuarioAcademico;


public class UsuarioAcademicoBean extends ControllerGWT<UsuarioAcademico>{
				
	private ProcessadorUsuarioAcademico u;
	private UsuarioAcademico usuarioLogado = null;
	private ArrayList<UsuarioAcademico> usuarios;
	private ArrayList<UsuarioAcademico> solicitacoes;
	private ArrayList<UsuarioAcademico> indicacoes;
	private ArrayList<TurmaVirtual> turmas;
	private boolean jaEhContato = false;
	private boolean home = false;
	private boolean mostrarIndicacoes;
	
	public UsuarioAcademicoBean(){
		u = new ProcessadorUsuarioAcademico();
		setDiretorio("/usuarioAcademico");
		inicialize();
	}
	
	@Override
	public String inicialize(){
		obj = new UsuarioAcademico();
		listaResultado = new ArrayList<UsuarioAcademico>();
		usuarios = new ArrayList<UsuarioAcademico>();
		solicitacoes = new ArrayList<UsuarioAcademico>();
		indicacoes = new ArrayList<UsuarioAcademico>();
		turmas = new ArrayList<TurmaVirtual>();
		jaEhContato = false;
		mostrarIndicacoes = true;
		return null;
	}

	@Override
	public boolean validate() {
		return true;
	}
	
	@Override
	public String cadastrar() throws NegocioException{
		
		
		u.cadastrar(obj);
		
		System.out.println("o usuario foi cadastrado");
		
		inicialize();
		return null;
		//return forward("/");
	}
	
	@Override
	public String remover() throws NegocioException{
		u.deletar(obj);
		return listar();
	}

	@Override
	public String atualizar() throws NegocioException {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String listar() throws NegocioException {
		listaResultado = (ArrayList<UsuarioAcademico>) u.getUsuariosByNome(obj);
		System.out.println("d a lista de resultados não está vazia! TAMANHO = " + listaResultado.size());
        System.out.println("d a lista por get de resultados não está vazia! TAMANHO = " + getListaResultado().size());
		
		return null;
		//return forwardLista();
	}
	
	/**
	 * Respons�vel por fazer o Login na rede social.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String login(){
		usuarioLogado = (UsuarioAcademico) u.login(obj);
		if (usuarioLogado != null && usuarioLogado.getId() != 0){
			u.getContatos(usuarioLogado);
			u.getSolicitacaoContato(usuarioLogado);
			super.setUsuarioLogado(usuarioLogado);
			turmas = (ArrayList<TurmaVirtual>) getTurmas(usuarioLogado);
			home = true;
			indicarAmigos();
			return null;
			//return forward("/home.jsp");
		}
		return inicialize();
	}
	
	/**
	 * Respons�vel por redirecionar para a p�gina inicial do usu�rio logado.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String home(){
		if(usuarioLogado == null)
			return forward("/index.jsp");
		home = true;
		turmas = (ArrayList<TurmaVirtual>) getTurmas(usuarioLogado);
		return forward("/home.jsp");
	}
	
	/**
	 * Respons�vel por redirecionar para a p�gina inicial de um usu�rio qualquer.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String homeVisitante(int id){
		home = false;
		obj = (UsuarioAcademico) getDao().findByPrimaryKey(id, UsuarioAcademico.class);
		u.getContatos(obj);
		u.getSolicitacaoContato(obj);
		jaEhContato = u.verificaJaEhContato(usuarioLogado, obj) || u.verificaJaSolicitouContato(usuarioLogado, obj);
		turmas = (ArrayList<TurmaVirtual>) getTurmas(obj);
		return null;
		//return forward("/homeVisitante.jsp");
	}
	
	/**
	 * Respons�vel por carregar as turmas de um determinado Usu�rio.
	 * 
	 * @param usuario
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Collection getTurmas(UsuarioAcademico usuario){
		ProcessadorTurmaVirtual turma = new ProcessadorTurmaVirtual();
		return turma.getAllGruposByUsuario(TurmaVirtual.class, usuario);
	}
	
	/**
	 * Respons�vel por adicionar um usu�rio como um contato.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addContato(int idLogado,int id){
		//int id = getId();
		UsuarioAcademico usuario = (UsuarioAcademico) getDao().findByPrimaryKey(id, UsuarioAcademico.class);
		usuarioLogado = (UsuarioAcademico) getDao().findByPrimaryKey(idLogado, UsuarioAcademico.class);
		u.addContato(usuarioLogado, usuario);
		jaEhContato = u.verificaJaEhContato(usuarioLogado, usuario) || u.verificaJaSolicitouContato(usuarioLogado, usuario);
		return null;
	}
	
	/**
	 * Respons�vel por remover um usu�rio da sua lista de contatos.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String removerContato(){
		UsuarioAcademico usuario = (UsuarioAcademico) getDao().findByPrimaryKey(getId(), UsuarioAcademico.class);
		u.removerContato(usuarioLogado, usuario);
		return null;
	}
	
	/**
	 * Respons�vel por aceitar uma solicita��o de contato.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String aceitarContato(int idLogado,int id){
		//int id = getId();
		UsuarioAcademico usuario = (UsuarioAcademico) getDao().findByPrimaryKey(id, UsuarioAcademico.class);
		usuarioLogado = (UsuarioAcademico) getDao().findByPrimaryKey(idLogado, UsuarioAcademico.class);
		u.aceitarContato(usuarioLogado, usuario);
		indicarAmigos();
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public UsuarioAcademico getUsuarioPorId(int id){
		UsuarioAcademico usuario = (UsuarioAcademico) getDao().findByPrimaryKey(id, UsuarioAcademico.class);
		return usuario;
	}
	
	/**
	 * Respons�vel por rejeitar uma solicita��o de contato.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String rejeitarContato(){
		int id = getId();
		UsuarioAcademico usuario = (UsuarioAcademico) getDao().findByPrimaryKey(id, UsuarioAcademico.class);
		u.rejeitarContato(usuarioLogado, usuario);
		return null;
	}
	
	public String iniciarBuscaGeralUsuario(){
		inicialize();
		return forwardBusca();
	}
	
	@SuppressWarnings("unchecked")
	public String buscaGeralUsuario(){
		ProcessadorConsulta p = new ProcessadorConsulta(new StrategyConcreto());
		@SuppressWarnings("unused")
		Hashtable h = p.execute("buscaGeralUsuario", getRequest().getParameterMap());
		return forwardBusca();
	}
	
	@SuppressWarnings("unchecked")
	public String indicarAmigos(){
		ProcessadorConsulta p = new ProcessadorConsulta(new StrategyConcreto());
		Hashtable<Integer, UsuarioAcademico> h = new Hashtable<Integer, UsuarioAcademico>();
		h.put(getUsuarioLogado().getId(), getUsuarioLogado());
		indicacoes.addAll(p.execute("indicarAmigo", h).values());
		return null;
	}
	
	public String ocultarIndicacoes(){
		setMostrarIndicacoes(false);
		return forward("/home.jsp");
	}
	
	public void listarContatos(){
		u.getContatos(usuarioLogado);
	}
	
	public void listarSolicitacoesContato(){
		u.getSolicitacaoContato(usuarioLogado);
	}
	
	
	/* M�todos Getters e Setters */
	
	public UsuarioAcademico getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(UsuarioAcademico usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public ArrayList<UsuarioAcademico> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<UsuarioAcademico> usuarios) {
		this.usuarios = usuarios;
	}

	public ArrayList<UsuarioAcademico> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(ArrayList<UsuarioAcademico> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public ArrayList<UsuarioAcademico> getIndicacoes() {
		return indicacoes;
	}

	public void setIndicacoes(ArrayList<UsuarioAcademico> indicacoes) {
		this.indicacoes = indicacoes;
	}

	public ArrayList<TurmaVirtual> getTurmas() {
		return turmas;
	}

	public void setTurmas(ArrayList<TurmaVirtual> turmas) {
		this.turmas = turmas;
	}
	
	public boolean isPodeAdicionar(){
		return !jaEhContato;
	}

	public boolean isHome() {
		return home;
	}

	public void setHome(boolean home) {
		this.home = home;
	}

	public boolean isMostrarIndicacoes() {
		return mostrarIndicacoes;
	}

	public void setMostrarIndicacoes(boolean mostrarIndicacoes) {
		this.mostrarIndicacoes = mostrarIndicacoes;
	}

}
