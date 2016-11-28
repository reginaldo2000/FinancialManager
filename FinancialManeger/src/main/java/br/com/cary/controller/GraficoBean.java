package br.com.cary.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.cary.dao.FinanceiroDAO;
import br.com.cary.entity.Financeiro;
import br.com.cary.filter.FinanceiroFilter;

@ManagedBean
@RequestScoped
public class GraficoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private BarChartModel barModel;
	private FinanceiroFilter filtro;

	// private HorizontalBarChartModel horizontalBarModel;

	public GraficoBean() {
		filtro = new FinanceiroFilter();
	}

	@PostConstruct
	public void init() {
		createBarModels();
		filtro = new FinanceiroFilter();
	}

	private BarChartModel initBarModel() {
		double receitas = 0.0;
		double despesas = 0.0;
		BarChartModel model = new BarChartModel();
		
		model.setAnimate(true);
				
		try {

			ChartSeries rec = new ChartSeries();
			ChartSeries desp = new ChartSeries();
			rec.setLabel("Receitas");
			desp.setLabel("Despesas");

			Calendar cal = GregorianCalendar.getInstance();
			Integer mes = cal.get(Calendar.MONTH)+1;
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			for (int i = mes - 4; i <= mes; i++) {
				rec.set(i+"/"+cal.get(Calendar.YEAR), 0.0);
				desp.set(i+"/"+cal.get(Calendar.YEAR), 0.0);
			}

			for (int i = mes - 4; i <= mes; i++) {
				String dataI = "01/" + i + "/" + cal.get(Calendar.YEAR);
				String dataF = "31/" + i + "/" + cal.get(Calendar.YEAR);
				if (i == 2) {
					dataF = "29/" + i + "/" + cal.get(Calendar.YEAR);
				}
				filtro.setDataInicial(df.parse(dataI));
				filtro.setDataFinal(df.parse(dataF));
				for (Financeiro f : FinanceiroDAO.getInstance().popularGrafico(filtro)) {
					if (f.getTipo().equals("Receita")) {
						receitas += f.getValor();
					} else {
						despesas += f.getValor();
					}
				}
				rec.set(i+"/"+cal.get(Calendar.YEAR), receitas);
				desp.set(i+"/"+cal.get(Calendar.YEAR), despesas);
				receitas = 0.0;
				despesas = 0.0;
			}

			model.addSeries(rec);
			model.addSeries(desp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	private void createBarModels() {
		createBarModel();
		// createHorizontalBarModel();
	}

	private void createBarModel() {
		barModel = initBarModel();

		barModel.setTitle("Receitas x Despesas");
		barModel.setLegendPosition("ne");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Mês");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Valor");
		yAxis.setMin(0);

	}

	public BarChartModel getBarModel() {
		return barModel;
	}

}
