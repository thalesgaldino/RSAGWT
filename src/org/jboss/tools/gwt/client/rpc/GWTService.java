package org.jboss.tools.gwt.client.rpc;


import java.util.List;

import javax.ejb.Remote;

import org.jboss.tools.gwt.client.dto.AlunoDTO;
import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;

import br.com.framework.Arq.Exception.NegocioException;




import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@Remote
@RemoteServiceRelativePath("gwtservice")
public interface GWTService extends RemoteService {
	public boolean salvar(AlunoDTO alunoDTO);
	public String cadastrar(UsuarioAcademicoDTO usuarioAcademicoDTO) throws NegocioException;
	public boolean login(UsuarioAcademicoDTO usuarioAcademicoDTO);
	public String sair();
	public UsuarioAcademicoDTO getPerfil();
	public boolean verificaUsuario();
	public String listar() throws NegocioException;
	public List<UsuarioAcademicoDTO> loadUsuarioAcadList(String nome) throws NegocioException;
}
