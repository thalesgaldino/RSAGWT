package org.jboss.tools.gwt.server.rpc;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.jboss.tools.gwt.client.dto.AlunoDTO;
import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;
import org.jboss.tools.gwt.client.rpc.GWTService;
import org.jboss.tools.gwt.server.beans.UsuarioAcademicoBean;
import org.jboss.tools.gwt.server.entidades.Aluno;

import org.jboss.tools.gwt.server.util.HibernateUtil;
import org.jboss.tools.gwt.server.util.SessionUtil;

import br.com.framework.Arq.Exception.NegocioException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import controle.UsuarioAcademicoMBean;
import dominio.UsuarioAcademico;

@SuppressWarnings("serial")
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService{
	
	Aluno aluno = new Aluno();
	
	UsuarioAcademicoMBean usuarioAcademicoMBean;	
	
	
	
	public boolean salvar(AlunoDTO alunoDTO){
		
		System.out.println("entrei dentro de salvar da implementacao no servidor.. 4");
		
		Session session = HibernateUtil.getSession();
		Transaction t = session.beginTransaction();
		System.out.println("acabei de getar a sessao");
		
		
		//boolean added = false;
		//aluno.setId(alunoDTO.getAlunoId());
		aluno.setNome(alunoDTO.getNome());
		aluno.setIdade(alunoDTO.getIdade());
		boolean added=false;
		
		try {
		
			session.merge(aluno);
			t.commit();
		
			SessionUtil.addSuccessMessage("OperacaoSucesso");
			aluno = new Aluno();
			added = true;
		} catch (Exception e) {
		
			t.rollback();
			SessionUtil.addErrorMessage("OperacaoFracasso");
		}finally{
		
			session.close();
		}
		
		return added;
		
	}
	

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}


	@Override
	public String cadastrar(UsuarioAcademicoDTO usuarioAcademicoDTO) throws NegocioException {
		// TODO Auto-generated method stub
		
		UsuarioAcademicoMBean usuarioAcademicoMBean = new UsuarioAcademicoMBean();
		
		usuarioAcademicoMBean.getObj().setNome(usuarioAcademicoDTO.getNome());
		usuarioAcademicoMBean.getObj().setDataNascimento(usuarioAcademicoDTO.getDataNascimento());
		usuarioAcademicoMBean.getObj().setUniversidade(usuarioAcademicoDTO.getUniversidade());
		usuarioAcademicoMBean.getObj().setCurso(usuarioAcademicoDTO.getCurso());
		usuarioAcademicoMBean.getObj().setEmail(usuarioAcademicoDTO.getEmail());
		usuarioAcademicoMBean.getObj().setSenha(usuarioAcademicoDTO.getSenha());
		
		return usuarioAcademicoMBean.cadastrar();
	}

	//no momento, sem uso
	public boolean login(UsuarioAcademicoDTO usuarioAcademicoDTO){
		usuarioAcademicoMBean = new UsuarioAcademicoMBean();
		System.out.println("Login for " + usuarioAcademicoDTO.getEmail());
		
		
		usuarioAcademicoMBean.getObj().setEmail(usuarioAcademicoDTO.getEmail());
		usuarioAcademicoMBean.getObj().setSenha(usuarioAcademicoDTO.getSenha());
		
		return false;
		//return usuarioAcademicoMBean.login();
		
		//return getMyApp().doLogin(usuarioAcademicoDTO);
	}
	
	public String sair(){
		return usuarioAcademicoMBean.sair();
	}

	public UsuarioAcademicoDTO getPerfil(){
		UsuarioAcademicoDTO usuarioLogado = new UsuarioAcademicoDTO();
		
		if (usuarioAcademicoMBean.getUsuarioLogado() != null){
			usuarioLogado.setNome(usuarioAcademicoMBean.getUsuarioLogado().getNome());
			usuarioLogado.setDataNascimento(usuarioAcademicoMBean.getUsuarioLogado().getDataNascimento());
			usuarioLogado.setUniversidade(usuarioAcademicoMBean.getUsuarioLogado().getUniversidade());
			usuarioLogado.setCurso(usuarioAcademicoMBean.getUsuarioLogado().getCurso());
			usuarioLogado.setEmail(usuarioAcademicoMBean.getUsuarioLogado().getEmail());
			usuarioLogado.setSenha(usuarioAcademicoMBean.getUsuarioLogado().getSenha());
		}
			
		return usuarioLogado;
	}


	@Override
	public boolean verificaUsuario() {
		
		if (usuarioAcademicoMBean != null) {
			if (usuarioAcademicoMBean.getUsuarioLogado() != null){
				return true;
			}
		}
		return false;
	}


	@Override
	public String listar() throws NegocioException {
		
		usuarioAcademicoMBean = new UsuarioAcademicoMBean();
		
		//usuarioAcademicoMBean.getObj().setNome("");
		
		usuarioAcademicoMBean.getObj().setNome("");
		usuarioAcademicoMBean.listar();
		
		//UsuarioAcademicoDTO u = new UsuarioAcademicoDTO();
		
		
		if (usuarioAcademicoMBean.getListaResultado() != null){
		    System.out.println("a lista de resultados nãooo está vazia! TAMANHO = " + usuarioAcademicoMBean.getListaResultado().size());
			
		}
		
		return null;
	}


	@Override
	public List<UsuarioAcademicoDTO> loadUsuarioAcadList(String nome) throws NegocioException {
		
		usuarioAcademicoMBean = new UsuarioAcademicoMBean();
		
		if (nome != null){
			usuarioAcademicoMBean.getObj().setNome(nome);
		}else{
			usuarioAcademicoMBean.getObj().setNome("");
		}
		
		usuarioAcademicoMBean.listar();
		
		//UsuarioAcademicoDTO u = new UsuarioAcademicoDTO();
		
		ArrayList<UsuarioAcademicoDTO> a = new ArrayList<UsuarioAcademicoDTO>();
		/*for (UsuarioAcademico u : usuarioAcademicoMBean.getListaResultado()){
			UsuarioAcademicoDTO usr = new UsuarioAcademicoDTO();
			usr.setId(u.getId());
			usr.setNome(u.getNome());
			usr.setDataNascimento(u.getDataNascimento());
			usr.setEmail(u.getEmail());
			//usr.setSenha(u.getSenha());
			usr.setUniversidade(u.getUniversidade());
			usr.setCurso(u.getCurso());
			usr.setLoggedIn(false);
			
			//Montando Lista de Contatos do usuário que está sendo visualizado
			  //a = lista de contatos
			  ArrayList<UsuarioAcademicoDTO> a2 = new ArrayList<UsuarioAcademicoDTO>();
			  
			  for (UsuarioAcademico usuario: u.getContatos()){
				  UsuarioAcademicoDTO usr2 = new UsuarioAcademicoDTO();
				  usr2.setId(usuario.getId());
				  usr2.setNome(usuario.getNome());
				  usr2.setNome(usuario.getCurso());
				  a2.add(usr2);
			  }
			  
			  usr.setContatos(a2);
			
			a.add(usr);
		}*/
		
		/*if (usuarioAcademicoMBean.getListaResultado() != null){
		    System.out.println("a lista de resultados nãooo está vazia! TAMANHO = " + usuarioAcademicoMBean.getListaResultado().size());
		    System.out.println("e o novo array? TAMANHO = " + a.size());
		}*/
		
		return a;
	}
	
}
