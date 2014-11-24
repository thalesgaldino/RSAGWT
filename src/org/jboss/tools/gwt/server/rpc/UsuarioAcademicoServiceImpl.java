package org.jboss.tools.gwt.server.rpc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;
import org.jboss.tools.gwt.client.rpc.UsuarioAcademicoService;
import org.jboss.tools.gwt.server.beans.UsuarioAcademicoBean;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import controle.UsuarioAcademicoMBean;
import dominio.UsuarioAcademico;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UsuarioAcademicoServiceImpl extends RemoteServiceServlet implements
  UsuarioAcademicoService {
 
 @Override
 public UsuarioAcademicoDTO loginServer(String name, String password) {
 
	 UsuarioAcademicoBean u = new UsuarioAcademicoBean();
	 u.getObj().setEmail(name);
	 u.getObj().setSenha(password);
	 
	 u.login();
	 
  UsuarioAcademicoDTO usuarioAcademicoDTO = new UsuarioAcademicoDTO();
 
  if (u.getUsuarioLogado() != null && u.getUsuarioLogado().getId() != 0){
	  usuarioAcademicoDTO.setId(u.getUsuarioLogado().getId());
	  usuarioAcademicoDTO.setNome(u.getUsuarioLogado().getNome());
	  usuarioAcademicoDTO.setDataNascimento(u.getUsuarioLogado().getDataNascimento());
	  usuarioAcademicoDTO.setEmail(u.getUsuarioLogado().getEmail());
	  usuarioAcademicoDTO.setSenha(u.getUsuarioLogado().getSenha());
	  usuarioAcademicoDTO.setUniversidade(u.getUsuarioLogado().getUniversidade());
	  usuarioAcademicoDTO.setCurso(u.getUsuarioLogado().getCurso());
	  usuarioAcademicoDTO.setLoggedIn(true);
	  
	  //Montando Lista de Contatos do usuário logado
	  //a = lista de contatos
	  ArrayList<UsuarioAcademicoDTO> a = new ArrayList<UsuarioAcademicoDTO>();
	  
	  for (UsuarioAcademico usuario: u.getUsuarioLogado().getContatos()){
		  UsuarioAcademicoDTO usr = new UsuarioAcademicoDTO();
		  usr.setId(usuario.getId());
		  usr.setNome(usuario.getNome());
		  usr.setCurso(usuario.getCurso());
		  usr.setUniversidade(usuario.getUniversidade());
		  usr.setEmail(usuario.getEmail());
		  a.add(usr);
	  }
	  
	  usuarioAcademicoDTO.setContatos(a);
	  
	  //Montando Lista de Solicitacoes do usuário logado
	  //sc = lista de solicitacoes
	  ArrayList<UsuarioAcademicoDTO> sc = new ArrayList<UsuarioAcademicoDTO>();
	  
	  for (UsuarioAcademico usuario: u.getUsuarioLogado().getSolicitacaoContato()){
		  UsuarioAcademicoDTO usr = new UsuarioAcademicoDTO();
		  usr.setId(usuario.getId());
		  usr.setNome(usuario.getNome());
		  usr.setCurso(usuario.getCurso());
		  usr.setUniversidade(usuario.getUniversidade());
		  usr.setEmail(usuario.getEmail());
		  sc.add(usr);
	  }
	  
	  usuarioAcademicoDTO.setSolicitacaoContato(sc);
	  
  }
  
  /*if (name.equals("guest") && password.equals("guest")) {
	  usuarioAcademicoDTO.setNome("theGuest");
	  usuarioAcademicoDTO.setLoggedIn(true);
	  usuarioAcademicoDTO.setUniversidade("guest@mail.com");
  }*/
 
  if (usuarioAcademicoDTO.isLoggedIn()) {
      storeUserInSession(usuarioAcademicoDTO);
      storeBeanInSession(u);
  }
  
 
  return usuarioAcademicoDTO;
 }
 
 private UsuarioAcademicoDTO getUserAlreadyFromSession() {
 
  UsuarioAcademicoDTO usuarioAcademicoDTO = null;
  HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
  HttpSession session = httpServletRequest.getSession();
  Object userObj = session.getAttribute("user");
  if (userObj != null && userObj instanceof UsuarioAcademicoDTO) {
	  usuarioAcademicoDTO = (UsuarioAcademicoDTO) userObj;
  }
  return usuarioAcademicoDTO;
 
 }
 
 @Override
 public UsuarioAcademicoDTO loginFromSessionServer() {
  //UsuarioAcademicoDTO user = getUserAlreadyFromSession();
	 UsuarioAcademicoBean bean = getBeanAlreadyFromSession();
	 if (bean == null){ 
		 return null;
	 }
	 else{
		 UsuarioAcademicoDTO usuarioAcademicoDTO = usuarioToDTO(bean.getUsuarioLogado());
		 usuarioAcademicoDTO.setLoggedIn(true);
		 return usuarioAcademicoDTO;
	 }
 }
 
 private void storeUserInSession(UsuarioAcademicoDTO user) {
  HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
  HttpSession session = httpServletRequest.getSession();
  session.setAttribute("user", user);
 
 }
 
 private void storeBeanInSession(UsuarioAcademicoBean u) {
	  HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
	  HttpSession session = httpServletRequest.getSession();
	  session.setAttribute("bean", u);
}
 
 
 
 @Override
 public void logout() {
   //deleteUserFromSession();
	 deleteBeanFromSession();
 }
 
 private void deleteUserFromSession() {
   
  HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
  HttpSession session = httpServletRequest.getSession();
  session.removeAttribute("user");
   
 }
 
 private void deleteBeanFromSession() {
	   
	  HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
	  HttpSession session = httpServletRequest.getSession();
	  session.removeAttribute("bean");
	   
}

@Override
public void addContato(int id) {
	UsuarioAcademicoDTO user = getUserAlreadyFromSession();
	
	UsuarioAcademicoMBean u = new UsuarioAcademicoMBean();
	
	u.addContato(user.getId(), id);
	
}

@Override
public void aceitarContato(int id) {
	UsuarioAcademicoDTO user = getUserAlreadyFromSession();
	
	UsuarioAcademicoMBean u = new UsuarioAcademicoMBean();

	System.out.println("Aceitando Contatoooo");
	
	u.aceitarContato(user.getId(), id);
	
	updateSession();
	
}

public UsuarioAcademicoDTO usuarioToDTO(UsuarioAcademico usuarioAcademico){
	UsuarioAcademicoDTO usuarioAcademicoDTO = getUserAlreadyFromSession();
	
	usuarioAcademicoDTO.setId(usuarioAcademico.getId());
	  usuarioAcademicoDTO.setNome(usuarioAcademico.getNome());
	  usuarioAcademicoDTO.setDataNascimento(usuarioAcademico.getDataNascimento());
	  usuarioAcademicoDTO.setEmail(usuarioAcademico.getEmail());
	  usuarioAcademicoDTO.setSenha(usuarioAcademico.getSenha());
	  usuarioAcademicoDTO.setUniversidade(usuarioAcademico.getUniversidade());
	  usuarioAcademicoDTO.setCurso(usuarioAcademico.getCurso());
	  //usuarioAcademicoDTO.setLoggedIn(true);
	  
	  //Montando Lista de Contatos do usuário logado
	  //a = lista de contatos
	  ArrayList<UsuarioAcademicoDTO> a = new ArrayList<UsuarioAcademicoDTO>();
	  
	  for (UsuarioAcademico usuario: usuarioAcademico.getContatos()){
		  UsuarioAcademicoDTO usr = new UsuarioAcademicoDTO();
		  usr.setId(usuario.getId());
		  usr.setNome(usuario.getNome());
		  usr.setCurso(usuario.getCurso());
		  usr.setUniversidade(usuario.getUniversidade());
		  usr.setEmail(usuario.getEmail());
		  a.add(usr);
	  }
	  
	  usuarioAcademicoDTO.setContatos(a);
	  
	  //Montando Lista de Solicitacoes do usuário logado
	  //sc = lista de solicitacoes
	  ArrayList<UsuarioAcademicoDTO> sc = new ArrayList<UsuarioAcademicoDTO>();
	  
	  for (UsuarioAcademico usuario: usuarioAcademico.getSolicitacaoContato()){
		  UsuarioAcademicoDTO usr = new UsuarioAcademicoDTO();
		  usr.setId(usuario.getId());
		  usr.setNome(usuario.getNome());
		  usr.setCurso(usuario.getCurso());
		  usr.setUniversidade(usuario.getUniversidade());
		  usr.setEmail(usuario.getEmail());
		  sc.add(usr);
	  }
	  
	  usuarioAcademicoDTO.setSolicitacaoContato(sc);
	
	  return usuarioAcademicoDTO;
}

public void updateSession(){
	UsuarioAcademicoDTO usuarioAcademicoDTO = getUserAlreadyFromSession();
	
	UsuarioAcademicoMBean u = new UsuarioAcademicoMBean();
	
	UsuarioAcademico usuarioAcademico = u.getUsuarioPorId(usuarioAcademicoDTO.getId());
	
	System.out.println("Usuario academico OK" + "o nome é " + usuarioAcademico.getEmail() + "a senha é " + usuarioAcademico.getSenha());
	
	u.getObj().setEmail(usuarioAcademico.getEmail());
	u.getObj().setSenha(usuarioAcademico.getSenha());
	
	u.login();
	
	usuarioAcademicoDTO = new UsuarioAcademicoDTO();
	usuarioAcademicoDTO = usuarioToDTO(u.getUsuarioLogado());
	
	usuarioAcademicoDTO.setLoggedIn(true);
	
	deleteUserFromSession();
	
	System.out.println("Atualizando sessao");
	
	storeUserInSession(usuarioAcademicoDTO);
	
}

// vai ficar em desuso
public UsuarioAcademicoBean beanFromSessionServer() {
	UsuarioAcademicoBean bean = getBeanAlreadyFromSession();
	return bean;
}

private UsuarioAcademicoBean getBeanAlreadyFromSession() {
	 
	  UsuarioAcademicoBean usuarioAcademicoBean = null;
	  HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
	  HttpSession session = httpServletRequest.getSession();
	  Object userObj = session.getAttribute("bean");
	  if (userObj != null && userObj instanceof UsuarioAcademicoBean) {
		  usuarioAcademicoBean = (UsuarioAcademicoBean) userObj;
	  }
	  return usuarioAcademicoBean;
	 
}

@Override
public UsuarioAcademicoDTO visualizarContato(int id) {
	UsuarioAcademicoBean bean = getBeanAlreadyFromSession();
	bean.homeVisitante(id);
	UsuarioAcademicoDTO usuarioAcademicoDTO = usuarioToDTO(bean.getObj());
	usuarioAcademicoDTO.setLoggedIn(bean.isHome());
	return usuarioAcademicoDTO;
}

}