package org.jboss.tools.gwt.client.windows;

import org.jboss.tools.gwt.client.ui.RecadosForm;

import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;

public class RecadosWindow  extends Window {
	
	private final RecadosForm feedForm = new RecadosForm();
	
	public RecadosWindow() {
		//setHeading("Feed");
		setWidth(350);
		setHeight(200);
		setResizable(false);
		setLayout(new FitLayout());

		final Button btnSave = new Button("Save");
		btnSave.setIconStyle("save");
		
		addButton(btnSave);
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);

		add(feedForm);
	}
	
}
