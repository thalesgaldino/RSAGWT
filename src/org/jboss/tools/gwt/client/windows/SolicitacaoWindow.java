package org.jboss.tools.gwt.client.windows;


import org.jboss.tools.gwt.client.HomePage;
import org.jboss.tools.gwt.client.rpc.UsuarioAcademicoService;
import org.jboss.tools.gwt.client.rpc.UsuarioAcademicoServiceAsync;

import org.jboss.tools.gwt.client.ui.PerfilView;

import com.extjs.gxt.ui.client.data.BeanModel;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;


public class SolicitacaoWindow extends Window {

	private final PerfilView solicitacaoView = new PerfilView();

	public SolicitacaoWindow(final BeanModel user) {
		
		solicitacaoView.displayUser(user);
		setHeading("Visualizar Solicitação");
		
		setWidth(740);
		setHeight(320);
		setResizable(false);
		setLayout(new FitLayout());
			
		final Button btnAceitar = new Button("Aceitar");
		btnAceitar.setIconStyle("save");
		btnAceitar.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				btnAceitar.setEnabled(false);
				//o procedimento de aceitar um contato
				
				((UsuarioAcademicoServiceAsync) GWT.create(UsuarioAcademicoService.class)).aceitarContato( (Integer) user.get("id") , new AsyncCallback<Void>() {
 				       public void onFailure(Throwable error) {
	 					    //TODO Handle the Error
	 					    error.printStackTrace();
 					   }
 				 
 					   public void onSuccess(Void result) {
 					 
 						  MessageBox messageBox = new MessageBox();
 						  messageBox.setMessage("Contato adicionado com sucesso!");
 							messageBox.show();
 						RootPanel.get().clear();
 						HomePage homePage = new HomePage();
 						RootPanel.get().add(homePage);
 					    }
 				   }
 				   );
			
			}
		});
		
		addButton(btnAceitar);
	}

	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		add(solicitacaoView);
	}

}
