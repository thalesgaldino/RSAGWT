package org.jboss.tools.gwt.client.lists;

import java.util.List;

import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;
import org.jboss.tools.gwt.client.rpc.GWTService;
import org.jboss.tools.gwt.client.rpc.GWTServiceAsync;
import org.jboss.tools.gwt.client.windows.PerfilWindow;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.gwt.user.client.ui.RootPanel;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Info;

public class UsuarioOverviewView extends ContentPanel {
	
	private ListView<BeanModel> listView = new ListView<BeanModel>();

	private String getTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append("<tpl for=\".\">");
		sb.append("<div class=\"feed-box\">");
		sb.append("<h1>{nome}</h1>");
		sb.append("<img src='resources/images/image0.jpg' width='70' height='60'>");
		sb.append("<p>{curso}</p>");
		sb.append("</div>");
		sb.append("</tpl>");
		return sb.toString();
	}
	
	public UsuarioOverviewView(final String nome) {
		
		//setLayout(new FitLayout());
		setHeight(300);
		
		setHeading("Usuários Cadastrados");
		setHeaderVisible(false);
		
		final GWTServiceAsync gwtService = (GWTServiceAsync) GWT.create(GWTService.class);
		
		//to retrieve a list of Users objects
		RpcProxy<List<UsuarioAcademicoDTO>> proxy = new RpcProxy<List<UsuarioAcademicoDTO>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<List<UsuarioAcademicoDTO>> callback) {
				
					gwtService.loadUsuarioAcadList(nome, new AsyncCallback<List<UsuarioAcademicoDTO>>() 
							{
									
								public void onFailure(Throwable caught) {
			                         // Convenient way to find out which exception was thrown.
			                           caught.printStackTrace();
			                           Info.display("Falha", "Erro ao carregar a busca");
			                           callback.onFailure(caught);
			                    }
								@Override
								public void onSuccess( List<UsuarioAcademicoDTO> arg0) {
									// TODO Auto-generated method stub
									//Info.display("Success", "Success");
									callback.onSuccess(arg0);
								}
							});
				
			}
		};
		
		//to convert the Users objects into BeanModel objects
		BeanModelReader reader = new BeanModelReader();
		
		//takes the RpcProxy and the BeanModelReader and uses them to load a list of BeanModel representations of the Users objects
		ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(
				proxy, reader);
		
		//takes the ListLoader as a parameter in order to use the loader to populate the store
		ListStore<BeanModel> userStore = new ListStore<BeanModel>(loader);
		
		listView.setStore(userStore);
		listView.setTemplate(getTemplate());
		
		//define the item selector of the ListView to be the feed-box div
		listView.setItemSelector("div.feed-box");
		
		listView.getSelectionModel().addListener(Events.SelectionChange,
				new Listener<SelectionChangedEvent<BeanModel>>() {
					@Override
					public void handleEvent(SelectionChangedEvent<BeanModel> be) {
						BeanModel usuario = be.getSelection().get(0);
						Info.display("Feed selected",
								(String) usuario.get("nome"));
						PerfilWindow perfilWindow = new PerfilWindow(usuario);
						perfilWindow.show();
					}
				});
		
		//call to the load method of the ListLoader to trigger the loading of the Store
		loader.load();
		
		add(listView);
		
	}
	
}
