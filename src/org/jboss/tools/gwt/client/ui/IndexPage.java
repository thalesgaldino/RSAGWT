
package org.jboss.tools.gwt.client.ui;

import java.util.Date;



import org.jboss.tools.gwt.client.lists.UsuarioOverviewView;
import org.jboss.tools.gwt.client.ui.CadastroForm;
import org.jboss.tools.gwt.client.ui.LoginForm;

import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;

import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
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

public class IndexPage extends ContentPanel
{

	private TabPanel tabPanel = new TabPanel(); //em desuso
	private TabPanel tabPanel2 = new TabPanel();
	
	private BorderLayoutData rightSidebarLayoutData;
	private BorderLayoutData mainContentsLayoutData;
	private ContentPanel mainContentsPanel;
	
	private BorderLayout layout;	
	
	TextField<String> buscarField = new TextField<String>();
	
	public IndexPage()
	{
		addStyleName("home-page");
    	
        setSize(980,630);
        setHeaderVisible(false);
        
        layout = new BorderLayout();
        setLayout(layout);
        
        BorderLayoutData menubarLayoutData = new BorderLayoutData(LayoutRegion.NORTH, 25);
        menubarLayoutData.setMargins(new Margins(5));
        
        BorderLayoutData leftSidebarLayoutData = new BorderLayoutData(LayoutRegion.WEST, 150);
        leftSidebarLayoutData.setSplit(true);
        leftSidebarLayoutData.setCollapsible(false);
        leftSidebarLayoutData.setMargins(new Margins(0, 5, 0, 5));

        mainContentsLayoutData = new BorderLayoutData(LayoutRegion.CENTER);
        mainContentsLayoutData.setMargins(new Margins(0));

        rightSidebarLayoutData = new BorderLayoutData(LayoutRegion.EAST, 230);
        rightSidebarLayoutData.setCollapsible(false);
        rightSidebarLayoutData.setMargins(new Margins(0, 5, 0, 5));

        BorderLayoutData footerLayoutData = new BorderLayoutData(LayoutRegion.SOUTH, 20);
        footerLayoutData.setMargins(new Margins(5));

        setTopComponent(getBanner());
        
        tabPanel.setMinTabWidth(115);
	    tabPanel.setTabScroll(true);
	    tabPanel.setCloseContextMenu(true);
	    
	    tabPanel2.setMinTabWidth(115);
	    tabPanel2.setTabScroll(true);
	    tabPanel2.setCloseContextMenu(true);
        
	    TabItem item2 = new TabItem();
	    TabItem item3 = new TabItem();
	    TabItem item4 = new TabItem();
	    item2.setText("Seja Bem Vindo!");
	    item3.setText("Notícias");
	    item4.setText("Eventos");
	    item2.setClosable(false);
	    item3.setClosable(false);
	    item4.setClosable(false);
	    //item2.add(getMainContents());
	    //tabPanel2.add(item2);
	    //tabPanel2.add(item3);
	    //tabPanel2.add(item4);
	    tabPanel2.setSelection(item2);
	    
	    add(getMenuBar(), menubarLayoutData);
	    getMainContents();
	    //add(tabPanel2, mainContentsLayoutData);
	    
	    add(getRightSidebar(), rightSidebarLayoutData);
        add(getFooter(), footerLayoutData);
        
    }
	
	 private void addTab(String text, ContentPanel contentPanel)
	    {
	    	TabItem item = new TabItem();
		    item.setText(text);
		    //item.setClosable(true);
		    item.add(contentPanel);
		    item.setClosable(true);
		    tabPanel2.add(item);
		    tabPanel2.setSelection(item);
		    
		       
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
              hc.setSize(730, 10);
              hc.setPosition(0, 0);
        
              
              
        buttonBar.add(hc);
        
        buttonBar.add(inicio);
        buttonBar.add(noticias);
        buttonBar.add(eventos);
        
        buttonBar.setAlignment(HorizontalAlignment.RIGHT);
        
        final AsyncCallback<String> callback =
			new AsyncCallback<String>()
			{
			MessageBox messageBox = new MessageBox();
			@Override
			public void onFailure(Throwable caught)
			{
				messageBox.setMessage("An error occured! Cannot do search procedure - exception");
				messageBox.show();
			}
			@Override
			public void onSuccess(String result)
			{
				messageBox.setMessage("search procedure done successfully");
				
				layout.hide(LayoutRegion.values()[1]); //direito
				
				tabPanel2.removeFromParent();
    	    	ContentPanel listaUsuarios = new ContentPanel();
    	    	listaUsuarios.setHeading("Usuários Cadastrados");
    	    	add(listaUsuarios, mainContentsLayoutData);
    	    	
    	    	layout();
				
    	    	messageBox.show();
				
			}
			};
        
        Button buscar = new Button("Buscar",
        		new SelectionListener<ButtonEvent>() {
    		
    	    @Override
    		public void componentSelected(ButtonEvent ce)
    		{
    	    	layout.hide(LayoutRegion.values()[1]); //direito
				
    	        /*mainContentsPanel.removeFromParent();
    	        
    	        add(tabPanel2, mainContentsLayoutData);
    	    	
    	        layout();*/
    	        
				//tabPanel2.removeFromParent();
    	    	mainContentsPanel.removeFromParent();
    	    	
    	    	UsuarioOverviewView usuarioOverviewView = new UsuarioOverviewView(buscarField.getValue());
				//CadastroForm c = new CadastroForm(layout);
    	    	//ContentPanel c = new ContentPanel();
    	    	//usuarioOverviewView.add(new HTML("teste"));
    	    	
    	    	addTab("Usuários",usuarioOverviewView);
    	    	
				add(tabPanel2, mainContentsLayoutData);
				
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

    public ToolBar getMenuBar()
    {
    	ToolBar toolBar = new ToolBar();
    	
    	Button home = new Button("Home",
        		new SelectionListener<ButtonEvent>() {
        		
        	    @Override
        		public void componentSelected(ButtonEvent ce)
        		{
        	    	Window.Location.assign(GWT.getHostPageBaseURL());
        	    	/*RootPanel.get().remove(0);
	        		RootPanel.get().add(new IndexPage());*/
	        	}		
        });
        
    	Button buttonPerfil = new Button("Perfil");
    	Button buttonRecados = new Button("Recados");
    	Button buttonAmigos = new Button("Amigos");
    	Button buttonTurmas = new Button("Turmas");
    	Button buttonTVirtuais = new Button("Trabalhos Virtuais");
    	
    	
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
        
        return toolBar;

    }
    
    public void getMainContents()
    {
    	
    	
        mainContentsPanel = new ContentPanel();
        mainContentsPanel.setHeaderVisible(false);
        mainContentsPanel.setBorders(false);
        
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
        
        add(mainContentsPanel, mainContentsLayoutData);
        
    }
    
    public ContentPanel getRightSidebar()
    {
    	final ContentPanel rightSidebarPanel = new ContentPanel();
        rightSidebarPanel.setHeading("Cadastre-se!");
        
        Button cadastroButton = new Button("Inscreva-se agora!",
        		new SelectionListener<ButtonEvent>() {
        		@Override
	        		public void componentSelected(ButtonEvent ce)
	        		{
	        			CadastroForm cadastroForm = new CadastroForm();
		    			layout.hide(LayoutRegion.values()[1]); //direito
	        			
	        			//tabPanel2.remove(tabPanel2.getItem(1));// 0 eh o primeiro
		        		
	        			mainContentsPanel.removeFromParent();
	        			
	        			//addTab("Inscrição",cadastroForm);
		        		
	        			add(cadastroForm, mainContentsLayoutData);
	    				
	    				layout();
	        			
		        	}
        		});
        
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.add(new HTML("Deseja fazer parte da rede?"));
        dialogVPanel.add(new HTML("<br>Não perca tempo!<br><br>"));
        
        dialogVPanel.add(cadastroButton);
        
        //leftSidebarPanel.add(setupContentPanel);
        cadastroButton.setWidth(220);
        
        
        rightSidebarPanel.add(dialogVPanel, new RowData(1,-1,new Margins(5,5,5,5)));
        
        LoginForm loginForm = new LoginForm(layout);
        rightSidebarPanel.add(loginForm);
        
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

}