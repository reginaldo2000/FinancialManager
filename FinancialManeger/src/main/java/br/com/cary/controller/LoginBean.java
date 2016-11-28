package br.com.cary.controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.cary.dao.UsuarioDAO;
import br.com.cary.entity.Usuario;
import br.com.cary.jsf.util.MessagesView;

@ManagedBean
@SessionScoped
public class LoginBean {

	private Usuario usuario;

	public LoginBean() {
		usuario = new Usuario();
	}
	
	public void autenticarUsuario() {
		Usuario temp = UsuarioDAO.getInstance().getUsuario(usuario);
		if(usuario.getUsuario().equals("") || usuario.getSenha().equals("")) {
			MessagesView.getInstance().alerta("Preencha os campos marcados com (*)!");
		}
		if(temp != null) {
			try {
				usuario = temp;
				FacesContext.getCurrentInstance().getExternalContext().redirect("inicial.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			MessagesView.getInstance().error("Usuário ou senha incorretos!");
		}
	}
	
	public void validar() {
		if(usuario.getId() == null) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("403.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}
	
	public void sair() {
		usuario = new Usuario();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void error404() {
		try {
			if(usuario.getId() != null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("inicial.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
