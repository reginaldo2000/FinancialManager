package br.com.cary.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cary.bo.FinanceiroBO;
import br.com.cary.dao.FinanceiroDAO;
import br.com.cary.entity.Financeiro;
import br.com.cary.filter.FinanceiroFilter;
import br.com.cary.jsf.util.MessagesView;

@ManagedBean
@ViewScoped
public class ConsultaFinanceiroBean {

	private Financeiro financeiro;
	private FinanceiroFilter filtro;
	private List<Financeiro> listaLancamentos;
	private Integer mes;
	
	public ConsultaFinanceiroBean() {
		financeiro = new Financeiro();
		filtro = new FinanceiroFilter();
		consultaPorMes();
	}

	public void consultaPorMes() {
		listaLancamentos = new ArrayList<>();
		try {
			if (mes != null) {
				Calendar cal = GregorianCalendar.getInstance();
				System.out.println(cal.get(Calendar.YEAR));
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String dataI = "01/" + mes + "/" + cal.get(Calendar.YEAR);
				String dataF = "31/" + mes + "/" + cal.get(Calendar.YEAR);
				if (mes == 2) {
					dataF = "29/" + mes + "/" + cal.get(Calendar.YEAR);
				}
				filtro.setDataInicial(df.parse(dataI));
				filtro.setDataFinal(df.parse(dataF));
			}
			System.out.println(filtro.getDataInicial()+"  "+filtro.getDataFinal());
			listaLancamentos = FinanceiroDAO.getInstance().consultar(filtro);
			filtro = new FinanceiroFilter();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Double getSaldo() {
		double despesa = 0.0;
		double receita = 0.0;
		for(Financeiro f : listaLancamentos){
			if(f.getTipo().equals("Receita")) {
				receita += f.getValor();
			} else if(f.getTipo().equals("Despesa")) {
				despesa += f.getValor();
			}
		}
		return receita - despesa;	
	}
	
	public void editar() {
		try {
			FinanceiroBO.getInstance().salvarOuAtualizar(financeiro);
			MessagesView.getInstance().info("Movimentação financeira atualizada com sucesso!");
			financeiro = new Financeiro();
		} catch (Exception e) {
			e.printStackTrace();
			MessagesView.getInstance().error(e.getMessage());
		}
	}
	
	public void excluir() {
		try {
			FinanceiroBO.getInstance().excluir(financeiro);
			MessagesView.getInstance().info("Movimentação financeira excluída com sucesso!");
			consultaPorMes();
		} catch (Exception e) {
			MessagesView.getInstance().error(e.getMessage());
		}
	}

	public List<Financeiro> lista() {
		return listaLancamentos;
	}

	public Financeiro getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(Financeiro financeiro) {
		this.financeiro = financeiro;
	}

	public FinanceiroFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(FinanceiroFilter filtro) {
		this.filtro = filtro;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public List<Financeiro> getListaLancamentos() {
		return listaLancamentos;
	}

	public void setListaLancamentos(List<Financeiro> listaLancamentos) {
		this.listaLancamentos = listaLancamentos;
	}

}
