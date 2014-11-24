package org.jboss.tools.gwt.client.rpc;


import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UsuarioAcademicoServiceAsync {

	public void loginFromSessionServer(AsyncCallback<UsuarioAcademicoDTO> asyncCallback);

	public void loginServer(String value, String value2,
			AsyncCallback<UsuarioAcademicoDTO> asyncCallback);
	
	void logout(AsyncCallback<Void> callback);

	void addContato(int id, AsyncCallback<Void> callback);

	public void aceitarContato(int id,
			AsyncCallback<Void> asyncCallback);

	public void visualizarContato(int id,	AsyncCallback<UsuarioAcademicoDTO> asyncCallback);

}
