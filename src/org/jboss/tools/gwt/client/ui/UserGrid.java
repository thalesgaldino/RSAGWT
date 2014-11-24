package org.jboss.tools.gwt.client.ui;

import java.util.ArrayList;
import java.util.List;


import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;
import org.jboss.tools.gwt.client.rpc.GWTService;
import org.jboss.tools.gwt.client.rpc.GWTServiceAsync;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.form.CheckBox;

import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;

import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;

import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;



public class UserGrid extends ContentPanel{

	 ColumnConfig fooColumn;
     
     public UserGrid() {
		
		setLayout(new FitLayout());
		
		setHeaderVisible(false);
		//setHeading("Usuários Cadastrados");
			
        fooColumn = new ColumnConfig("foo", "Foo", 100);
        final List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		/*columns.add(fooColumn);
		columns.add(new ColumnConfig("nome", "Nome", 200));
		columns.add(new ColumnConfig("curso", "Curso", 200));*/
		//final ColumnModel columnModel = new ColumnModel(columns);
		
		final GWTServiceAsync gwtService = (GWTServiceAsync) GWT.create(GWTService.class);
    	
		GridCellRenderer<BeanModel> itemsRenderer = new GridCellRenderer<BeanModel>() {
			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				
				String title = model.get("nome");
				String description = model.get("curso");
				return "<img src='resources/images/image0.jpg' width='70' height='60'>" + "<b><br/>" + title + "</b><br/>" + description;
			}
		};
		
		ColumnConfig column = new ColumnConfig();
		column.setId("items");
		column.setRenderer(itemsRenderer);
		column.setHeader("Usuários Cadastrados");
		
		columns.add(column);
		final ColumnModel columnModel = new ColumnModel(columns);
		
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
		
		Grid<BeanModel> grid = new Grid<BeanModel>(userStore,columnModel);
		grid.setBorders(true);
		//grid.setAutoExpandColumn("curso");
		grid.setAutoExpandColumn("items");
		
		//call to the load method of the ListLoader to trigger the loading of the Store
		loader.load();
		
		add(grid);
		
	}
	
}
