package br.com.cary.bo;

import br.com.cary.dao.FinanceiroDAO;
import br.com.cary.entity.Financeiro;

public class FinanceiroBO {

	private static FinanceiroBO instance;

	public static FinanceiroBO getInstance() {
		if(instance == null) {
			instance = new FinanceiroBO();
		}
		return instance;
	}
	
	public void salvarOuAtualizar(Financeiro f) throws Exception {
		if(f.getDescricao().replace(" ", "").equals("")) {
			throw new Exception("Por favor preencha o campo descrição!");
		}
		FinanceiroDAO.getInstance().salvarOuAtualizar(f);
	}
	
	public void excluir(Financeiro f) throws Exception {
		if(f.getId() == null) {
			throw new Exception("Selecione um lançamento para excluí-lo!");
		} 
		FinanceiroDAO.getInstance().deletar(f);
	}
}
