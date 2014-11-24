package org.jboss.tools.gwt.client.lists;

import java.util.List;

import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;
import org.jboss.tools.gwt.client.windows.SolicitacaoWindow;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.MemoryProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Info;

public class SolicitacaoOverviewView extends ContentPanel {
	
	private ListView<BeanModel> listSolicitacaoView = new ListView<BeanModel>();

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
	
	public SolicitacaoOverviewView(UsuarioAcademicoDTO result) {
		
		setLayout(new FitLayout());
		//setHeight(240);
		
		setHeading("Solicitação de Contatos");
		
		//to convert the Users objects into BeanModel objects
		BeanModelReader bmr = new BeanModelReader();
		bmr.setFactoryForEachBean(true);
		
		//to retrieve a list of Users objects
		MemoryProxy<List<UsuarioAcademicoDTO>> memoryProxy = new MemoryProxy(result.getSolicitacaoContato());
		
		//takes the RpcProxy and the BeanModelReader and uses them to load a list of BeanModel representations of the Users objects
		ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(
				memoryProxy, bmr);
		
		//takes the ListLoader as a parameter in order to use the loader to populate the store
		ListStore<BeanModel> listSolicitacaoStore = new ListStore<BeanModel>(loader);
		
		//listSolicitacaoStore = new ListStore<BeanModel>(new BaseListLoader(new MemoryProxy(result.getSolicitacaoContato()), bmr));
		
		listSolicitacaoView = new ListView<BeanModel>(listSolicitacaoStore);
		
		//listSolicitacaoView.setSimpleTemplate("Um teste de solicitacao: o id é {id}: o nome é {nome}: o curso é {curso}");
		listSolicitacaoView.setTemplate(getTemplate());
		
		//define the item selector of the ListView to be the feed-box div
		listSolicitacaoView.setItemSelector("div.feed-box");
		
		listSolicitacaoView.getSelectionModel().addListener(Events.SelectionChange,
				new Listener<SelectionChangedEvent<BeanModel>>() {
					@Override
					public void handleEvent(SelectionChangedEvent<BeanModel> be) {
						BeanModel solicitacao = be.getSelection().get(0);
						Info.display("Solicitação selecionada:",
								(String) solicitacao.get("nome"));
						SolicitacaoWindow solicitacaoWindow = new SolicitacaoWindow(solicitacao);
						solicitacaoWindow.show();
					}
				});
		
		//call to the load method of the ListLoader to trigger the loading of the Store
		loader.load();
		
		add(listSolicitacaoView);
		
	}
	
}
