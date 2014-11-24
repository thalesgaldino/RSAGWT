package org.jboss.tools.gwt.client.ui;

import java.util.ArrayList;
import java.util.List;


import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;
import org.jboss.tools.gwt.client.rpc.GWTService;
import org.jboss.tools.gwt.client.rpc.GWTServiceAsync;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;

import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ModelData;

public class UserList extends ContentPanel{

	public UserList() {
		
		setLayout(new FitLayout());
		
		setHeading("Usuários Cadastrados");
		
		final ListField<BeanModel> userList = new ListField<BeanModel>();
		
		final GWTServiceAsync gwtService = (GWTServiceAsync) GWT.create(GWTService.class);
    	
		//to retrieve a list of Users objects
		RpcProxy<List<UsuarioAcademicoDTO>> proxy = new RpcProxy<List<UsuarioAcademicoDTO>>() {
			@Override
			protected void load(Object loadConfig,
				AsyncCallback<List<UsuarioAcademicoDTO>> callback) {
				
				/*gwtService.loadUsuarioAcadList(callback);*/

			}
		};
		
		//to convert the Users objects into BeanModel objects
		BeanModelReader reader = new BeanModelReader();
		
		//takes the RpcProxy and the BeanModelReader and uses them to load a list of BeanModel representations of the Users objects
		ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(
				proxy, reader);
		
		//takes the ListLoader as a parameter in order to use the loader to populate the store
		ListStore<BeanModel> userStore = new ListStore<BeanModel>(loader);
		
		userList.setStore(userStore);
		userList.setDisplayField("nome");
		
		//call to the load method of the ListLoader to trigger the loading of the Store
		loader.load();
		
		add(userList);
		
	}
	
}
