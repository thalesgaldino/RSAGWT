package org.jboss.tools.gwt.client.windows;


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

public class PerfilWindow extends Window {

	private final PerfilView perfilView = new PerfilView();

	public PerfilWindow(final BeanModel user) {
		
		//mostrar o feed-no-caso-o-usuario aqui
		perfilView.displayUser(user);
		setHeading("Visualizar Contato");
		//setHeading((String) user.get("nome"));
		setWidth(740);
		setHeight(320);
		setResizable(false);
		setLayout(new FitLayout());
		
		//AsyncCallback<Void> callback = new 
		
		final Button btnAdicionar = new Button("Adicionar Contato");
		btnAdicionar.setIconStyle("save");
		btnAdicionar.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				btnAdicionar.setEnabled(false);
				//colocar aqui o procedimento de adicionar um contato
				
				
				((UsuarioAcademicoServiceAsync) GWT.create(UsuarioAcademicoService.class)).addContato( (Integer) user.get("id") , new AsyncCallback<Void>() {
 				       public void onFailure(Throwable error) {
	 					    //TODO Handle the Error
	 					    error.printStackTrace();
 					   }
 				 
 					   public void onSuccess(Void result) {
 					 
 						  MessageBox messageBox = new MessageBox();
 						  messageBox.setMessage("Solicitação de contato enviada!");
 							messageBox.show();
 					     
 					    }
 				   }
 				   );
				
				
				
				/*if (feedForm.isValid()) {
					hide(btnSave);
					feedForm.save(feed);
				} else {
					btnSave.setEnabled(true);
				}*/
			}
		});
		
		addButton(btnAdicionar);
	}

	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		add(perfilView);
	}

}
