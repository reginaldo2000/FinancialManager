package br.com.cary.jasper;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import br.com.cary.filter.FinanceiroFilter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;



public class Relatorios {

	private HttpServletResponse response;
	private FacesContext context;
	private ByteArrayOutputStream baos;
	private InputStream stream;
	private Connection con;

	public Relatorios() {
        this.context = FacesContext.getCurrentInstance();
        this.response = (HttpServletResponse) context.getExternalContext().getResponse();
    }

	public void gerarRelatorioReceitas(FinanceiroFilter filtro) {
		stream = this.getClass().getResourceAsStream("/report/receitas.jasper");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataI", filtro.getDataInicial());
		params.put("dataF", filtro.getDataFinal());
		baos = new ByteArrayOutputStream();

		try {
			JasperReport report = (JasperReport) JRLoader.loadObject(stream);
			JasperPrint print = JasperFillManager.fillReport(report, params, getConexao());
			JasperExportManager.exportReportToPdfStream(print, baos);

			response.reset();
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			response.setHeader("Content-disposition", "inline; filename=relatorio.pdf");
			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();

			context.responseComplete();
			fecharConexao();

		} catch (Exception ex) {
			Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
		} 
	}

	public Connection getConexao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/f_maneger", "root", "developer");
			return con;

		} catch (SQLException ex) {
			Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
		}

		return con;
	}

	public void fecharConexao() {
		try {
			con.close();
		} catch (SQLException ex) {
			Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
