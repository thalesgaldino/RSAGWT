package org.jboss.tools.gwt.client.ui;


import org.jboss.tools.gwt.client.HomePage;
import org.jboss.tools.gwt.client.dto.AlunoDTO;

import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;
import org.jboss.tools.gwt.client.rpc.UsuarioAcademicoService;
import org.jboss.tools.gwt.client.rpc.UsuarioAcademicoServiceAsync;

import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;


public class LoginForm extends FormPanel{
	
	TextField<String> loginField;
	TextField<String> senhaField;
	Button loginButton = new Button("Login");
	AlunoDTO alunoDTO;
	UsuarioAcademicoDTO usuarioAcademicoDTO;
	
	public LoginForm(final BorderLayout l)
	{
		setHeading("Acessar");
		setFieldWidth(130);
		setAction(GWT.getModuleBaseURL());
		
		KeyListener keyListener = new KeyListener() {
		      public void componentKeyUp(ComponentEvent event) {
		        validate();
		      }
		};
		
		loginField = new TextField<String>();
		loginField.addKeyListener(keyListener);
		
		senhaField = new TextField<String>();
		senhaField.addKeyListener(keyListener);
		senhaField.setPassword(true);
		
		final AsyncCallback<UsuarioAcademicoDTO> callback =
			new AsyncCallback<UsuarioAcademicoDTO>()
			{
			MessageBox messageBox = new MessageBox();
			@Override
			public void onFailure(Throwable caught)
			{
				if ( caught.getMessage().contains("call failed") ){
					messageBox.setMessage("Erro! Tente Novamente!");
					messageBox.show();
				}
				else{
					messageBox.setMessage(caught.getMessage());
					messageBox.show();
				}
				caught.printStackTrace();
			}
			@Override
			public void onSuccess(UsuarioAcademicoDTO result)
			{
				messageBox = new MessageBox();
				if(result.isLoggedIn()) {
		              
		              //Zeige Hauptanwendung
		              //Window.alert("Zeige Hauptanwendung");
		              RootPanel.get().clear();
		              HomePage homePage = new HomePage();
		              homePage.setUserView(result);
		              homePage.getPerfilContentsFromHere();
		              RootPanel.get().add(homePage);
//		           
		        } else
				{
					messageBox.setMessage("Senha ou Usuário inválido!");
					messageBox.show();
				}
				
				//messageBox.show();
				
			}
			};
		
			
			loginButton.disable();
			loginButton.addSelectionListener( new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce)
				{
					usuarioAcademicoDTO = new UsuarioAcademicoDTO();
					usuarioAcademicoDTO.setEmail(loginField.getValue());
					usuarioAcademicoDTO.setSenha(senhaField.getValue());
					((UsuarioAcademicoServiceAsync) GWT.create(UsuarioAcademicoService.class)).loginServer(loginField.getValue(), senhaField.getValue(), callback);
				}

			});
			
			createForm();
			
	}
	
	protected void validate() {
		
		String str01 = loginField.getValue();
		String str02 = senhaField.getValue();
		
		loginButton.setEnabled( ( str01 != null ) && ( str02 != null ) && (senhaField.getValue().length() > 3) );
	}
	
	private void createForm()
	{
		
		loginField.setFieldLabel("Email");
		//loginField.setAllowBlank(false);
		
		senhaField.setFieldLabel("Senha");
		//senhaField.setAllowBlank(true);
		
		add(loginField);
		add(senhaField);
		
		addButton(loginButton);
			
	}

}
