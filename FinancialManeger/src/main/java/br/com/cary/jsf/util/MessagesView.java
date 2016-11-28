package br.com.cary.jsf.util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class MessagesView {

	private static MessagesView instance;
	
	public static MessagesView getInstance() {
		if(instance == null) {
			instance = new MessagesView();
		}
		return instance;
	}

	public void info(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,msg, ""));
	}
	
	public void alerta(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,msg, ""));
	}
	
	public void error(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, ""));
	}
}
