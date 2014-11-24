package org.jboss.tools.gwt.client.ui;

import com.extjs.gxt.ui.client.core.Template;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.Element;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Util;

public class PerfilView extends ContentPanel{
	
	private final HTML html = new HTML();

	public void displayUser(BeanModel user) {
		setLayout(new FitLayout());
		setSize(740,180);
		setHeading("Perfil");
		Template template = new Template(getTemplate());
		html.setHTML(template.applyTemplate(Util.getJsObject(user, 1)));
	}

	private String getTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append("<div>");
		sb.append("<p> <img class=\"left bordered\" src='resources/images/image0.jpg' width='70' height='90'> </p>");
		sb.append("<p class='large'><b>{nome}</b></p>");
		sb.append("<p><i>{universidade}</i></p>");
		sb.append("<p>{curso}</p>");
		sb.append("<p>{email}</p>");
		sb.append("</div>");
		//sb.append("<hr/>"); //uma linha separadora
		
		
		return sb.toString();
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		setHeaderVisible(false);
		//setHeading("User");
		setLayout(new FitLayout());
		html.setStylePrimaryName("perfilview");
		add(html, new RowData(1,-1,new Margins(5,5,5,5)));
	}
	
}
