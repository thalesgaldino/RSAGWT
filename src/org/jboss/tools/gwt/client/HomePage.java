
package org.jboss.tools.gwt.client;

import java.util.Date;
import java.util.List;

import org.jboss.tools.gwt.client.dto.UsuarioAcadBeanModel;
import org.jboss.tools.gwt.client.dto.UsuarioAcademicoDTO;
import org.jboss.tools.gwt.client.lists.AmigosOverviewView;
import org.jboss.tools.gwt.client.lists.SolicitacaoOverviewView;
import org.jboss.tools.gwt.client.lists.UsuarioOverviewView;
import org.jboss.tools.gwt.client.rpc.GWTService;
import org.jboss.tools.gwt.client.rpc.GWTServiceAsync;
import org.jboss.tools.gwt.client.rpc.UsuarioAcademicoService;
import org.jboss.tools.gwt.client.rpc.UsuarioAcademicoServiceAsync;
import org.jboss.tools.gwt.client.ui.CadastroForm;
import org.jboss.tools.gwt.client.ui.IndexPage;
import org.jboss.tools.gwt.client.ui.PerfilView;
import org.jboss.tools.gwt.client.windows.PerfilWindow;
import org.jboss.tools.gwt.client.windows.RecadosWindow;
import org.jboss.tools.gwt.client.windows.SolicitacaoWindow;

import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;

import com.extjs.gxt.ui.client.data.MemoryProxy;
import com.extjs.gxt.ui.client.data.BeanModelReader;

public class HomePage extends ContentPanel
{

	UsuarioAcademicoDTO user = new UsuarioAcademicoDTO();
	
	private TabPanel tabPanel = new TabPanel();
	
	private BorderLayoutData rightSidebarLayoutData;
	private BorderLayoutData mainContentsLayoutData;
	private ContentPanel mainContentsPanel;
	
	private BorderLayout layout;	
	
	TextField<String> nomeField = new TextField<String>();
	TextField<String> ufField = new TextField<String>();
	TextField<String> cursoField = new TextField<String>();
	TextField<String> emailField = new TextField<String>();
	
	TextField<String> buscarField = new TextField<String>();
	
	String teste;
	
	private PerfilView perfilView;
	private ContentPanel centerPanel = new ContentPanel();
	
	ContentPanel rightSidebarPanel = new ContentPanel();
	
	ContentPanel perfilContentsPanel;
	
    public HomePage()
    {
		//setUserView(user);
    	
    	addStyleName("home-page");
    	
        setSize(980,630);
        setHeaderVisible(false);
  
        layout = new BorderLayout();
        setLayout(layout);
        
        setTopComponent(getBanner());
        
        BorderLayoutData menubarLayoutData = new BorderLayoutData(LayoutRegion.NORTH, 25);
        menubarLayoutData.setMargins(new Margins(5));
        
        BorderLayoutData leftSidebarLayoutData = new BorderLayoutData(LayoutRegion.WEST, 150);
        leftSidebarLayoutData.setSplit(true);
        leftSidebarLayoutData.setCollapsible(false);
        leftSidebarLayoutData.setMargins(new Margins(0, 5, 0, 5));

        mainContentsLayoutData = new BorderLayoutData(LayoutRegion.CENTER, 300);
        mainContentsLayoutData.setMargins(new Margins(0));

        rightSidebarLayoutData = new BorderLayoutData(LayoutRegion.EAST, 230);
        rightSidebarLayoutData.setCollapsible(false);
        rightSidebarLayoutData.setMargins(new Margins(0, 5, 0, 5));

        BorderLayoutData footerLayoutData = new BorderLayoutData(LayoutRegion.SOUTH, 20);
        footerLayoutData.setMargins(new Margins(5));

        tabPanel.setMinTabWidth(115);
	    tabPanel.setTabScroll(true);
	    tabPanel.setCloseContextMenu(true);
        
	    TabItem item1 = new TabItem();
	    TabItem item2 = new TabItem();
	    TabItem item3 = new TabItem();
	    TabItem item4 = new TabItem();
	    
	    item1.setText("Perfil");
	    item2.setText("Início");
	    item3.setText("Notícias");
	    item4.setText("Eventos");
	    
	    item2.setClosable(false);
	    item3.setClosable(false);
	    item4.setClosable(false);
	    
	    item2.add(getMainContents());
	    
	    add(getMenuBar(), menubarLayoutData);
	    
	    
	    
	    add(getFooter(), footerLayoutData);
        
	    //getPerfilContentsFromHere();
	    //getPerfilContents();
	    
    }
    
    public void getPerfilContentsFromHere(){
    	
    	perfilView = new PerfilView();
    	BeanModelFactory factory = BeanModelLookup.get().getFactory(UsuarioAcademicoDTO.class);
    	
    	BeanModel model = factory.createModel(user);
    	
    	//se houver solicitacoes:
    	
    	SolicitacaoOverviewView solicitacaoPanel = null;
    	if (user.isLoggedIn()){
	    	if ( user.getSolicitacaoContato().size() != 0 ){
	    		solicitacaoPanel = new SolicitacaoOverviewView(user);
	    	}
    	}
    	//se houver amigos:
    	AmigosOverviewView amigosPanel = null;
    	if ( user.getContatos().size() != 0 ){
    		amigosPanel = new AmigosOverviewView(user);
    	}
    	
    	//centerPanel.setLayout(new FitLayout());
    	centerPanel.setHeaderVisible(false);
		rightSidebarPanel.setHeaderVisible(false);
		
		perfilView.displayUser(model);
    	
		centerPanel.add(perfilView);
		
		//se houver solicitações:
		if (user.isLoggedIn()){
			if ( user.getSolicitacaoContato().size() != 0 ){
				centerPanel.add(solicitacaoPanel);
			}
		}
		
		ContentPanel semRecadosPanel = new ContentPanel();
		semRecadosPanel.setHeading("Recados");
		semRecadosPanel.add(new HTML("Você ainda não possui recados."),new RowData(1,-1,new Margins(5,5,5,5)));
		centerPanel.add(semRecadosPanel);
		
		if ( user.getContatos().size() != 0 ){
			rightSidebarPanel.add(amigosPanel);
		}else{
			ContentPanel semAmigosPanel = new ContentPanel();
			semAmigosPanel.setHeading("Amigos");
			semAmigosPanel.add(new HTML("Você ainda não possui amigos."),new RowData(1,-1,new Margins(5,5,5,5)));
			rightSidebarPanel.add(semAmigosPanel);
		}
		
		ContentPanel semTurmasPanel = new ContentPanel();
		semTurmasPanel.setHeading("Turmas Virtuais");
		semTurmasPanel.add(new HTML("Você ainda não possui turmas."),new RowData(1,-1,new Margins(5,5,5,5)));
		rightSidebarPanel.add(semTurmasPanel);
		
		add(centerPanel, mainContentsLayoutData);
		add(rightSidebarPanel, rightSidebarLayoutData);
    	
    	layout();
    }

    private void getPerfilContents() {
    	
    	         //final ContentPanel centerPanel = new ContentPanel();
    	
    			((UsuarioAcademicoServiceAsync) GWT.create(UsuarioAcademicoService.class)).loginFromSessionServer(new AsyncCallback<UsuarioAcademicoDTO>() {
    				   public void onFailure(Throwable error) {
    					    //TODO Handle the Error
    					    error.printStackTrace();
    					   }
    				 
    					   public void onSuccess(UsuarioAcademicoDTO result) {
    					 
    					    if (result == null) {
    					      
    					     //Window.alert("User not in session. Redirecting to login");
    					     IndexPage indexPage = new IndexPage();
    							RootPanel.get().add(indexPage);
    					      
    					    } else if (result.isLoggedIn()) {
    					 
    					    	
    					    	perfilView = new PerfilView();
    					    	
    					    	
    					    	BeanModelFactory factory = BeanModelLookup.get().getFactory(UsuarioAcademicoDTO.class);
    					    	
    					    	BeanModel model = factory.createModel(result);
    					    	
    					    	//se houver solicitacoes:
    					    	SolicitacaoOverviewView solicitacaoPanel = null;
    					    	if ( result.getSolicitacaoContato().size() != 0 ){
    					    		solicitacaoPanel = new SolicitacaoOverviewView(result);
    					    	}
    					    	//se houver amigos:
    					    	AmigosOverviewView amigosPanel = null;
    					    	if ( result.getContatos().size() != 0 ){
    					    		amigosPanel = new AmigosOverviewView(result);
    					    	}
    					    	
								centerPanel.setHeaderVisible(false);
								rightSidebarPanel.setHeaderVisible(false);
								
								perfilView.displayUser(model);
    					    	
								centerPanel.add(perfilView);
								
								//se houver solicitações:
								if ( result.getSolicitacaoContato().size() != 0 ){
									centerPanel.add(solicitacaoPanel);
								}
								
								ContentPanel semRecadosPanel = new ContentPanel();
								semRecadosPanel.setHeading("Recados");
								semRecadosPanel.add(new HTML("Você ainda não possui recados."),new RowData(1,-1,new Margins(5,5,5,5)));
								centerPanel.add(semRecadosPanel);
								
								if ( result.getContatos().size() != 0 ){
									rightSidebarPanel.add(amigosPanel);
								}else{
									ContentPanel semAmigosPanel = new ContentPanel();
									semAmigosPanel.setHeading("Amigos");
									semAmigosPanel.add(new HTML("Você ainda não possui amigos."),new RowData(1,-1,new Margins(5,5,5,5)));
									rightSidebarPanel.add(semAmigosPanel);
								}
								
								ContentPanel semTurmasPanel = new ContentPanel();
								semTurmasPanel.setHeading("Turmas Virtuais");
								semTurmasPanel.add(new HTML("Você ainda não possui turmas."),new RowData(1,-1,new Margins(5,5,5,5)));
								rightSidebarPanel.add(semTurmasPanel);
								
								add(centerPanel, mainContentsLayoutData);
								add(rightSidebarPanel, rightSidebarLayoutData);
    					    	
    					    	layout();
    					    	
    					    	
    					    }
    					    
    					   }
    				  });
        
	}

	public ToolBar getMenuBar()
    {
		ToolBar toolBar = new ToolBar();
    	
    	Button home = new Button("Home",
        		new SelectionListener<ButtonEvent>() {
        		
        	    @Override
        		public void componentSelected(ButtonEvent ce)
        		{
        	    	//RootPanel.get().remove(0);
        	    	//Window.Location.assign("http://www.globo.com");
        	    	Window.Location.assign(GWT.getHostPageBaseURL());
        	    	
        	    	
        	    	//RootPanel.get().clear();
	        		//RootPanel.get().add(new HomePage());
	        	}		
        });
        
    	Button buttonPerfil = new Button("Perfil",
        		new SelectionListener<ButtonEvent>() {
    		
    	    @Override
    		public void componentSelected(ButtonEvent ce)
    		{
    	    	
    	    	//perfilContentsPanel.removeFromParent();
    	    	
    	    	//perfilContentsPanel = new ContentPanel();
    	    	
    	    	perfilContentsPanel.add(new HTML("teste"));
    	    	
    	    	ContentPanel c = new ContentPanel();
    	    	c.setHeading("Nao acredito!");
    	    	
    	    	perfilContentsPanel.add(c);
    	    	
    	    	add(perfilContentsPanel, mainContentsLayoutData);
    	    	
    	    	
    	    	
    	    	layout();
    	    	
    	    	
    	    	//perfilContentsPanel.add(new HTML("teste"));
    	    	
    	    	
        	}		
    });
    	
    	Button buttonRecados = new Button("Meus Recados",
        		new SelectionListener<ButtonEvent>() {
    		
    	    @Override
    		public void componentSelected(ButtonEvent ce)
    		{
    	    	RecadosWindow recadosWindow = new RecadosWindow();
				recadosWindow.show();
        	}		
    });
    	Button buttonAmigos = new Button("Amigos");
    	Button buttonTurmas = new Button("Turmas");
    	Button buttonTVirtuais = new Button("Trabalhos Virtuais");
    	

        /*HtmlContainer hc = new HtmlContainer(
                "<div class=text style='padding:0px'>"
                + "<p align='right'>" +
                "<a href='/gwt-test'>Sair</a>" +
                //"<div class='b1'></div>"+
                "</p>"
                + "</div>");*/
        
        /*hc.add(new Button("Teste", new SelectionListener<ButtonEvent>() {  
        public void componentSelected(ButtonEvent ce) {  
              
        }  
     }), "div.b1");*/
        
        //hc.setBorders(false);
        //hc.setSize(570, 10);
    	
        toolBar.add(home);
        toolBar.add(new SeparatorToolItem());
        toolBar.add(buttonPerfil);
        toolBar.add(new SeparatorToolItem());
        toolBar.add(buttonRecados);
        toolBar.add(new SeparatorToolItem());
        toolBar.add(buttonAmigos);
        toolBar.add(new SeparatorToolItem());
        toolBar.add(buttonTurmas);
        toolBar.add(new SeparatorToolItem());
        toolBar.add(buttonTVirtuais);
        //toolBar.add(new SeparatorToolItem());
        //toolBar.add(hc);
        
        return toolBar;

    }
    
    private void addTab(String text, ContentPanel contentPanel)
    {
    	TabItem item = new TabItem();
	    item.setText(text);
	    //item.setClosable(true);
	    item.add(contentPanel);
	    item.setClosable(true);
	    item.setId(text);
	    tabPanel.add(item);
	    tabPanel.setSelection(item);
	    
	       
    }
    
    public ContentPanel getBanner()
    {
    	ContentPanel bannerPanel = new ContentPanel();
        bannerPanel.setHeaderVisible(false);
        
        final ButtonBar buttonBar = new ButtonBar();
        
        final ButtonBar buttonBar2 = new ButtonBar();
        
        buttonBar.setMinButtonWidth(75);
        buttonBar.setStyleAttribute("background", "none repeat scroll 0 0 #EEEEEE");
        
        Button inicio = new Button("Início", new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                
              }
            });
        Button noticias = new Button("Notícias");
        Button eventos = new Button("Eventos");
        Button sair = new Button("Sair");
        
        sair.addSelectionListener( new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce)
			{
				((UsuarioAcademicoServiceAsync) GWT.create(UsuarioAcademicoService.class)).logout(new AsyncCallback<Void>() {
				     public void onFailure(Throwable error) {
					      // RootPanel.get().add(new LoginUI());
					      error.printStackTrace();
					     }
					 
					     @Override
					     public void onSuccess(Void result) {
					      //Window.alert("Sucessfully logged out");
					      RootPanel.get().clear();
					      RootPanel.get().add(new IndexPage());
					     }
					 
					    });
			}

		});
        
        Date today = new Date();
        String dia = "";
        DateTimeFormat dtf = DateTimeFormat.getFormat(", dd/MM/yyyy");
        DateTimeFormat dtf2 = DateTimeFormat.getFormat("EEEE");
        if (dtf2.format(today).equals("Monday")){
        	dia = "Segunda-feira";
        }else if(dtf2.format(today).equals("Tuesday")){
        	dia = "Terça-feira";
        }else if(dtf2.format(today).equals("Wednesday")){
        	dia = "Quarta-feira";
        }else if(dtf2.format(today).equals("Thursday")){
        	dia = "Quinta-feira";
        }else if(dtf2.format(today).equals("Friday")){
        	dia = "Sexta-feira";
        }else if(dtf2.format(today).equals("Monday")){
        	dia = "Sábado";
        }else if(dtf2.format(today).equals("Monday")){
        	dia = "Domingo";
        }
        
        HtmlContainer hc = new HtmlContainer(
                "<div class=text style='padding:0px'>"
                + "<p>" +
                dia +
                dtf.format(today) +
                		"</p>"
                + "</div>");
              
        		/*hc.add(new Button("Blink", new SelectionListener<ButtonEvent>() {  
                  public void componentSelected(ButtonEvent ce) {  
                        
                  }  
               }), "div.b1");*/
              hc.setBorders(false);
              hc.setSize(650, 10);
              hc.setPosition(0, 0);
        
              
              
        buttonBar.add(hc);
        
        buttonBar.add(inicio);
        buttonBar.add(noticias);
        buttonBar.add(eventos);
        buttonBar.add(sair);
        
        buttonBar.setAlignment(HorizontalAlignment.RIGHT);
        
        Button buscar = new Button("Buscar",
        		new SelectionListener<ButtonEvent>() {
    		
    	    @Override
    		public void componentSelected(ButtonEvent ce)
    		{
    	    	
    	    	if (!rightSidebarLayoutData.isHidden()){
    	    		layout.hide(LayoutRegion.values()[1]); //direito
    	    	}
    	    	
    	    	//perfilView.removeFromParent();
    	    	centerPanel.removeFromParent();
    	    	//usuarioOverviewView.removeFromParent();
    	    	
    	    	//tabPanel.removeFromParent();
    	    	UsuarioOverviewView usuarioOverviewView = new UsuarioOverviewView(buscarField.getValue());
				//CadastroForm c = new CadastroForm(layout);
    	    	//ContentPanel c = new ContentPanel();
    	    	//c.add(new HTML("teste"));
    	    	
	    		addTab("Usuários",usuarioOverviewView);
    	    	
				add(tabPanel, mainContentsLayoutData);
				
				layout();
				
    	    }		
        });
        
        /*HtmlContainer hc2 = new HtmlContainer("" +
        		"<div class='clearer'>&nbsp;</div>" +
        		"<div class='clearer'>&nbsp;</div>" +
                
        		"<div class='f1' > </div>" +
        		"<div class='b1' > </div>" +
    			
        		"");
        
        hc2.add(buscar, "div.b1");
        hc2.add(buscarField, "div.f1");*/
        
        buttonBar2.add(buscarField);
        buttonBar2.add(buscar);
        buttonBar2.setAlignment(HorizontalAlignment.RIGHT);
        
        bannerPanel.add(buttonBar);
        
        //bannerPanel.add(hc2);
        
        bannerPanel.add(new HTML("</br>"));
        
        bannerPanel.add(buttonBar2);
        
        bannerPanel.add(new HTML("</br>"));
        
        HtmlContainer hc2 = new HtmlContainer("" +
        		
        		"<div id='site-title'>" +
					"<h1><a href='#'>Rede Social Acadêmica</a></h1>" +
				"</div>" +	
        	"");
        
        bannerPanel.add(hc2);
        
        //Image im = new Image("resources/images/banner2.png");
        //bannerPanel.add(im);
        //bannerPanel.setAutoHeight(true);
        //bannerPanel.add(new Image("resources/images/social_web.jpg"));
        
        return bannerPanel;
    }

    public ContentPanel getLeftSideBar()
    {
        ContentPanel leftSidebarPanel = new ContentPanel();
        leftSidebarPanel.setHeading("Opções");
        leftSidebarPanel.setBodyBorder(true);
        
        //leftSidebarPanel.setLayout(new AccordionLayout());
        
        //ContentPanel setupContentPanel = new ContentPanel();
        //setupContentPanel.setHeading("Setup");
        //setupContentPanel.setLayout(new RowLayout());
        
        final AsyncCallback<String> callback =
			new AsyncCallback<String>()
			{
			MessageBox messageBox = new MessageBox();
			@Override
			public void onFailure(Throwable caught)
			{
				messageBox.setMessage("An error occured! Cannot make logout procedure - exception");
				messageBox.show();
			}
			@Override
			public void onSuccess(String result)
			{
				messageBox.setMessage("Logout procedure done successfully");
				
				/*if (result)
				{
					messageBox.setMessage("Login procedure done successfully");
				} else
				{
					messageBox.setMessage("An error occured! Cannot make Login procedure - false");
				}*/
				//messageBox.show();
				//layout.show(LayoutRegion.values()[1]); //direito
        		//layout.hide(LayoutRegion.values()[3]); //esquerdo
				RootPanel.get().remove(0);
        		RootPanel.get().add(new IndexPage());
			}
			};
        
        
        Button outButton = new Button("Sair",
        		new SelectionListener<ButtonEvent>() {
        		@Override
        		
        		public void componentSelected(ButtonEvent ce)
        		{
        			((GWTServiceAsync) GWT.create(GWTService.class)).sair(callback);
        		}
        		});
        
        //setupContentPanel.add(branchButton,new RowData(1,-1,
        	//	new Margins(5,5,5,5)));
        
        //leftSidebarPanel.add(setupContentPanel);
        outButton.setSize(140, 25);
        
        leftSidebarPanel.add(outButton,new RowData(1,-1,	new Margins(5,5,5,5)));
        
        return leftSidebarPanel;

    }

    public ContentPanel getRightSidebar()
    {
    	
        //rightSidebarPanel.setHeaderVisible(false);
        
        //se houver amigos:
    	
        FormPanel dialogVPanel = new FormPanel();
        dialogVPanel.setHeading("Amigos");
        dialogVPanel.add(new HTML("Você ainda não possui Amigos"));
        
        FormPanel tVirtual = new FormPanel();
        tVirtual.setHeading("Turmas Virtuais");
        tVirtual.add(new HTML("Você ainda não pussui Turmas."));
        
        rightSidebarPanel.add(dialogVPanel);
        rightSidebarPanel.add(tVirtual);
        
        return rightSidebarPanel;
    }

    public VerticalPanel getFooter()
    {

        VerticalPanel footerPanel = new VerticalPanel();
        footerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        Label label = new Label("Design by RSA. Copyright © UFRN.");
        footerPanel.add(label);

        return footerPanel;
    }

    public ContentPanel getMainContents()
    {
    	mainContentsPanel = new ContentPanel();
    	mainContentsPanel.setHeaderVisible(false);
    	
    	Image img  = new Image("resources/images/image0.jpg");
    	img.setStyleName("img-perfil2");
        
        mainContentsPanel.add(img, new RowData(1,-1,new Margins(10,10,10,10)));
        
        mainContentsPanel.add(new HTML("A RSA é uma rede social voltada para o campo " +
        		"acadêmico. O principal objetivo da RSA é proporcionar o compartilhamento " +
        		"de informações e arquivos entre estudantes e professores universitários." + 
        		"<br>Como seu objetivo é o compartilhamento de informação, o Site não tem nenhum fim " +
        		"lucrativo. Qualquer pessoa pode fazer parte da Rede, bastando apenas se " +
        		"cadastrar no Site.	<br>A RSA promove uma maior interação entre professores " +
        		"e alunos, além de possibilitar a discussão em volta de assuntos " +
        		"comuns à um grupo de usuários. <br><br>Se você já faz parte da nossa " +
        		"Rede, acesse sua página, informando seus dados no formulário ao lado. Se ainda " +
        		"não é membro da RSA, cadastre-se agora mesmo, acessando o link ao lado e " +
        		"informando seus dados pessoais."), new RowData(1,-1,new Margins(5,5,5,5)));
        
        //mainContentsPanel.setHeading("Main Contents");
        return mainContentsPanel;

    }

	public void setUserView(UsuarioAcademicoDTO user) {
		this.user = user;
		//Info.display("okay", "entrou em set");
		//getPerfilContentsFromHere();
	}

	public UsuarioAcademicoDTO getUserView() {
		return this.user;
	}
    

    
}
