package org.jboss.tools.gwt.client;

import org.jboss.tools.gwt.client.dto.AlunoDTO;
import org.jboss.tools.gwt.client.rpc.GWTServiceAsync;
import org.jboss.tools.gwt.client.rpc.GWTService;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Entrada implements EntryPoint {

	@Override
	public void onModuleLoad() {

		final TextBox nomeCampo = new TextBox();
		final TextBox idadeCampo = new TextBox();
		
		final GWTServiceAsync gwtService = (GWTServiceAsync) GWT.create(GWTService.class);
		//ServiceDefTarget endpoint = (ServiceDefTarget) gwtService;
		//endpoint.setServiceEntryPoint("/");
		
		final Button botaoSalvar = new Button("Salvar");
		
		botaoSalvar.addClickHandler(new ClickHandler() {
			
			AlunoDTO alunoDTO;
			
			public void onClick(ClickEvent event)
			{
				System.out.println("entrei dentro de Entrada.java 7..");
				
				/*AsyncCallback callback = new AsyncCallback()
				{
					public void onSuccess(Object result)
					{
						if(((Boolean) result).booleanValue())
						{
						Window.alert("Yes, foi salvo.");
						}
						else
						{
						Window.alert("No, nao foi salvo.");
						}
					}
					
					public void onFailure(Throwable caught)
					{
						Window.alert("Error while calling salvar Entrada.java.");
					}
				};*/
			
				alunoDTO = new AlunoDTO();
				alunoDTO.setAlunoId(30);
				alunoDTO.setNome(nomeCampo.getText());
				alunoDTO.setIdade(Integer.parseInt(idadeCampo.getText()));
				
				
				
				gwtService.salvar(alunoDTO, new AsyncCallback<java.lang.Boolean>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						Window.alert("no, nao foi salvo.");
					}

					public void onSuccess(Boolean result) {
						Window.alert("Yes, foi salvo sim.");
					}
				});
			}
		});
	
		RootPanel.get().add(nomeCampo);
		RootPanel.get().add(idadeCampo);
		RootPanel.get().add(botaoSalvar);
	
	}
}