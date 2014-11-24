package org.jboss.tools.gwt.client.rpc;

import org.jboss.tools.gwt.client.dto.AlunoDTO;
import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("uaservice")
public interface UsuarioAcademicoService extends RemoteService{
	UsuarioAcademicoDTO loginServer(String name, String password) ;
	UsuarioAcademicoDTO loginFromSessionServer();
	 void logout();
	 void addContato(int id);
	void aceitarContato(int id);
	UsuarioAcademicoDTO visualizarContato(int id);
}
