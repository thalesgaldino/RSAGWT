package org.jboss.tools.gwt.client.ui;

import java.util.Date;

import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;
import org.jboss.tools.gwt.client.rpc.GWTService;
import org.jboss.tools.gwt.client.rpc.GWTServiceAsync;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class CadastroForm extends FormPanel{
	
	TextField<String> nomeField = new TextField<String>();
	DateField dateOfBirthField = new DateField();
	TextField<String> uFField = new TextField<String>();
	TextField<String> cursoField = new TextField<String>();
	TextField<String> emailField = new TextField<String>();
	TextField<String> senhaField = new TextField<String>();
	Button saveButton=new Button("Salvar");
	
	UsuarioAcademicoDTO usuarioAcademicoDTO;
	
	public CadastroForm()
	{
		//setAutoWidth(true);
		setHeading("Insira seus dados abaixo:");
		//setHeaderVisible(false);
		
		final AsyncCallback<String> callback =
			new AsyncCallback<String>()
			{
			MessageBox messageBox = new MessageBox();
			@Override
			public void onFailure(Throwable caught)
			{
				messageBox.setMessage("An error occured! Cannot save Academic User information");
				messageBox.show();
			}
			@Override
			public void onSuccess(String result)
			{
				messageBox.setMessage("Seu cadastro foi realizado com sucesso!");
				/*if (result)
				{
					messageBox.setMessage("Academic User information saved successfully");
				} else
				{
					messageBox.setMessage("An error occured! Cannot save Academic User information");
				}*/
				messageBox.show();
				
				//l.show(LayoutRegion.values()[1]); //direito
				
				RootPanel.get().remove(0);
        		RootPanel.get().add(new IndexPage());
        		
			}
			};
			

			saveButton.setToolTip("Clique aqui para se cadastrar.");			
			
			saveButton.disable();
			saveButton.addSelectionListener( new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce)
				{
					usuarioAcademicoDTO = new UsuarioAcademicoDTO();
					//branchDTO.setBranchId(Integer.parseInt(loginField.getValue()));
					usuarioAcademicoDTO.setNome(nomeField.getValue());
					usuarioAcademicoDTO.setDataNascimento(dateOfBirthField.getValue());
					usuarioAcademicoDTO.setUniversidade(uFField.getValue());
					usuarioAcademicoDTO.setCurso(cursoField.getValue());
					usuarioAcademicoDTO.setEmail(emailField.getValue());
					usuarioAcademicoDTO.setSenha(senhaField.getValue());
					
					
					//alunoDTO.setIdade(Integer.parseInt(senhaField.getValue()));

					((GWTServiceAsync) GWT.create(GWTService.class)).cadastrar(usuarioAcademicoDTO, callback);
					
					/*if (isValid() ){
						submit();
					}*/
				}

			});
			
		createForm();
		
	}
	
	private void createForm()
	{
		
		KeyListener keyListener = new KeyListener() {
		      public void componentKeyUp(ComponentEvent event) {
		        validate();
		      }
		};
		
		nomeField.setFieldLabel("Nome");
		nomeField.setAllowBlank(false);
		nomeField.addKeyListener(keyListener);
		nomeField.setEmptyText("Nome Completo");
		
		dateOfBirthField.setFieldLabel("Data de Nascimento");
		dateOfBirthField.setAllowBlank(false);
		dateOfBirthField.addKeyListener(keyListener);
		
		uFField.setFieldLabel("Universidade");
		uFField.setAllowBlank(false);
		uFField.addKeyListener(keyListener);
		
		cursoField.setFieldLabel("Curso");
		cursoField.setAllowBlank(false);
		cursoField.addKeyListener(keyListener);
		
		emailField.setFieldLabel("E-Mail");
		emailField.setAllowBlank(false);
		emailField.addKeyListener(keyListener);
		emailField.setEmptyText("exemplo@mail.com");
		
		senhaField.setFieldLabel("Senha");
		senhaField.setAllowBlank(false);
		senhaField.setPassword(true);
		senhaField.addKeyListener(keyListener);
		senhaField.setToolTip("No mínimo 4 caracteres.");
		
		ToolTipConfig saveToolTipConfig = new ToolTipConfig();
		//linkFeedToolTipConfig.setTitle("Link to existing RSSfeed");
		saveToolTipConfig.setText("Clique aqui para se cadastrar");
		
		
		add(nomeField);
		add(dateOfBirthField);
		add(uFField);
		add(cursoField);
		add(emailField);
		add(senhaField);
		
		addButton(saveButton);
		
	}
	
	protected void validate() {
		
		String str01 = nomeField.getValue();
		Date str02 = dateOfBirthField.getValue();
		String str03 = uFField.getValue();
		String str04 = cursoField.getValue();
		String str05 = emailField.getValue();
		String str06 = senhaField.getValue();
		
		saveButton.setEnabled( ( str01 != null ) && ( str02 != null )
				&& ( str03 != null )
				&& ( str04 != null )
				&& ( str05 != null )
				&& ( str06 != null )
				&& (senhaField.getValue().length() > 3) );
	}

}
