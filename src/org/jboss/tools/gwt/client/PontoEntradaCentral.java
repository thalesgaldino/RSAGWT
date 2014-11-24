package org.jboss.tools.gwt.client;



import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;
import org.jboss.tools.gwt.client.rpc.UsuarioAcademicoService;
import org.jboss.tools.gwt.client.rpc.UsuarioAcademicoServiceAsync;

import org.jboss.tools.gwt.client.ui.IndexPage;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class PontoEntradaCentral implements EntryPoint
{
 
	private final UsuarioAcademicoServiceAsync loginService = GWT
	   .create(UsuarioAcademicoService.class);
	
	public void onModuleLoad()
    {
		
		
		
		loginService.loginFromSessionServer(new AsyncCallback<UsuarioAcademicoDTO>() {
		   public void onFailure(Throwable error) {
		    //TODO Handle the Error
		    error.printStackTrace();
		   }
	 
		   public void onSuccess(UsuarioAcademicoDTO result) {
		 
			if (result == null) {
		      
		    	
		     //Window.alert("User not in session. Redirecting to login");
		    	RootPanel.get().clear();
		    	IndexPage indexPage = new IndexPage();
				RootPanel.get().add(indexPage);
		      
		    } else if (result.isLoggedIn()) {
		 
		     //Window.alert("Zeige Hauptanwendung");
		    	
		    	RootPanel.get().clear();
		     HomePage homePage = new HomePage();
		     homePage.setUserView(result);
			 homePage.getPerfilContentsFromHere();
		     RootPanel.get().add(homePage);
			}
		    
		    DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("Loading-Message"));
	 
	   }
	  });
	 
    }
	
	
}