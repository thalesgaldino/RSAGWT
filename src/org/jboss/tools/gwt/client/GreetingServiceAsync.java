package org.jboss.tools.gwt.client;

import org.jboss.tools.gwt.client.dto.AlunoDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(AlunoDTO alunoDTO, String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
