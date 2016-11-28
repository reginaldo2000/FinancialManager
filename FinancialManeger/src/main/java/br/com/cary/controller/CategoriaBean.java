package br.com.cary.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cary.bo.CategoriaBO;
import br.com.cary.dao.CategoriaDAO;
import br.com.cary.entity.Categoria;
import br.com.cary.jsf.util.MessagesView;

@ManagedBean
@ViewScoped
public class CategoriaBean {

	private Categoria categoria;
	private List<Categoria> listaCategorias;
	private String consulta;

	public CategoriaBean() {
		novo();
	}

	public void novo() {
		categoria = new Categoria();
		consulta = "";
	}

	public void salvarOuAtualizar() {
		try {
			if (categoria.getId() == null) {
				CategoriaBO.getInstance().salvar(categoria);
				MessagesView.getInstance().info("Categoria cadastrada com sucesso!");
				novo();
			} else {
				CategoriaBO.getInstance().atualizar(categoria);
				MessagesView.getInstance().info("Dados atualizados com sucesso!");
				novo();
			}
		} catch (Exception e) {
			MessagesView.getInstance().error(e.getMessage());
			e.printStackTrace();
		}

	}
	
	public void consultar() {
		listaCategorias = CategoriaDAO.getInstance().listar(consulta);
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getListaCategorias() {
		return CategoriaDAO.getInstance().listar(consulta);
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

}
