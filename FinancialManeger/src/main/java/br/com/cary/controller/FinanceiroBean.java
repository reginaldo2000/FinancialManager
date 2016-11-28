package br.com.cary.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cary.bo.FinanceiroBO;
import br.com.cary.dao.CategoriaDAO;
import br.com.cary.dao.FinanceiroDAO;
import br.com.cary.entity.Categoria;
import br.com.cary.entity.Financeiro;
import br.com.cary.filter.FinanceiroFilter;
import br.com.cary.jsf.util.MessagesView;

@ManagedBean
@ViewScoped
public class FinanceiroBean {

	private FinanceiroFilter filtro;
	private Financeiro financeiro;
	private List<Financeiro> lista;
	private List<Categoria> listaCategorias;
	private Categoria categoria;

	public FinanceiroBean() {
		novo();
	}

	public void novo() {
		financeiro = new Financeiro();
		filtro = new FinanceiroFilter();
	}

	public void salvarOuAtualizar() {
		try {
			if (financeiro.getId() != null) {
				FinanceiroBO.getInstance().salvarOuAtualizar(financeiro);
				novo();
				MessagesView.getInstance().info("Dados atualizados com sucesso!");
			} else {
				FinanceiroBO.getInstance().salvarOuAtualizar(financeiro);
				novo();
				MessagesView.getInstance().info("Movimentação financeira realizada com sucesso!");
			}
		} catch (Exception e) {
			MessagesView.getInstance().error(e.getMessage());
			e.printStackTrace();
		}

	}

	public void excluir() {
		try {
			FinanceiroBO.getInstance().excluir(financeiro);
			novo();
			MessagesView.getInstance().info("Lançamento excluído com sucesso!");
		} catch (Exception e) {
			MessagesView.getInstance().error(e.getMessage());
		}
	}

	public List<Categoria> getListaCategorias() {
		return CategoriaDAO.getInstance().listar("");
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public FinanceiroFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(FinanceiroFilter filtro) {
		this.filtro = filtro;
	}

	public Financeiro getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(Financeiro financeiro) {
		this.financeiro = financeiro;
	}

	public List<Financeiro> getLista() {
		return FinanceiroDAO.getInstance().listar(filtro);
	}

	public void setLista(List<Financeiro> lista) {
		this.lista = lista;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
