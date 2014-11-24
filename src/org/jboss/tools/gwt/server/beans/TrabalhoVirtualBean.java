package org.jboss.tools.gwt.server.beans;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import negocio.ProcessadorTrabalhoVirtual;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import br.com.framework.Arq.Exception.NegocioException;
import dominio.TrabalhoVirtual;
import dominio.UsuarioAcademico;

public class TrabalhoVirtualBean extends ControllerGWT<TrabalhoVirtual> {
	
	private UploadedFile file;
	private ProcessadorTrabalhoVirtual p;
	private UsuarioAcademico usuario;
	private int nota;
	
	public TrabalhoVirtualBean(){
		p = new ProcessadorTrabalhoVirtual();
		setDiretorio("/trabalhoVirtual");
		inicialize();
	}
	
	@Override
	public String inicialize(){
		obj = new TrabalhoVirtual();
		listaResultado = new ArrayList<TrabalhoVirtual>();
		return null;
	}
	
	public String home(){
		return forward("/trabalhoVirtual/home.jsp");
	}
	
	@Override
	public String atualizar() throws NegocioException {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String cadastrar() throws NegocioException {
		try {
			obj.setUsuario(getUsuarioLogado().getId());
			obj.setFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		listaResultado = p.getArquivosByUsuario(TrabalhoVirtual.class, getUsuarioLogado().getId());
		p.cadastrar(obj);
		return home();
	}
	
	@Override
	public String remover() throws NegocioException {
		getObjeto();
		p.deletar(obj);
		return home();
	}
	
	public String download() throws IOException{
		getObjeto();
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.setHeader("Content-Disposition","attachment;filename=\"" +obj.getNome()+ "\"");
		response.setContentType(obj.getContentType());
		ServletOutputStream out = response.getOutputStream();
		BufferedOutputStream bufferout = new BufferedOutputStream(out);
		bufferout.write(obj.getFilestream());
		bufferout.flush();
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String listar() throws NegocioException{
		listaResultado = p.getArquivosByUsuario(TrabalhoVirtual.class, getId());
		return home();
	}
	
	@SuppressWarnings("unchecked")
	public String meusTrabalhos() throws NegocioException{
		listaResultado = p.getArquivosByUsuario(TrabalhoVirtual.class, getUsuarioLogado().getId());
		return home();
	}
	
	@SuppressWarnings("unchecked")
	public String votar() throws NegocioException{
		getObjeto();
		p.avaliarTrabalhoVirtual(obj, nota);
		nota = 0;
		listaResultado = p.getArquivosByUsuario(TrabalhoVirtual.class, obj.getUsuario());
		return home();
	}
	
	public String compartilhar() throws NegocioException{
		getObjeto();
		p.compartilharTrabalhoVirtual(obj);
		return meusTrabalhos();
	}
	
	public String buscar(){
		return null;
	}

	@Override
	public boolean validate() {
		return false;
	}

	public UsuarioAcademico getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioAcademico usuario) {
		this.usuario = usuario;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

}
