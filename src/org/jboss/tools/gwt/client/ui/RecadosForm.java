package org.jboss.tools.gwt.client.ui;

import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.google.gwt.user.client.Element;

public class RecadosForm  extends FormPanel {
	
	private final TextArea taDescription = new TextArea();
	
	public RecadosForm() {
		setHeaderVisible(false);
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);

		taDescription.setFieldLabel("Escreva:");
		taDescription.setAllowBlank(false);
		//taDescription.getMessages().setBlankText("Description is required");

		/*tfLink.setFieldLabel("Link");
		tfLink.setAllowBlank(false);
		tfLink.setRegex("^http\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?$");
		tfLink.getMessages().setBlankText("Link is required");
		tfLink.getMessages()
				.setRegexText(
						"The link field must be a URL e.g. http://www.example.com/rss.xml");*/

		//add(tfTitle);
		add(taDescription);
		//add(tfLink);
	}
	
}
