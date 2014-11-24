package org.jboss.tools.gwt.client.rpc;

import java.util.List;

import org.jboss.tools.gwt.client.dto.AlunoDTO;
import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTServiceAsync {
	public void salvar(AlunoDTO alunoDTO, AsyncCallback<java.lang.Boolean> callback);

	public void cadastrar(UsuarioAcademicoDTO usuarioAcademicoDTO,	AsyncCallback<String> callback);

	void login(UsuarioAcademicoDTO usuarioAcademicoDTO, AsyncCallback<Boolean> callback);
	
	void sair(AsyncCallback<String> callback);

	public void getPerfil(AsyncCallback<UsuarioAcademicoDTO> callback);

	public void verificaUsuario(AsyncCallback<Boolean> callback);

	public void listar(AsyncCallback<String> callback);

	public void loadUsuarioAcadList(String nome, AsyncCallback<List<UsuarioAcademicoDTO>> callback);

}
