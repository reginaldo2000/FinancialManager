package br.com.cary.bo;

import br.com.cary.dao.CategoriaDAO;
import br.com.cary.entity.Categoria;


public class CategoriaBO {

	private static CategoriaBO instance;
	
	
	public static CategoriaBO getInstance() {
		if(instance == null) {
			instance = new CategoriaBO();
		}
		return instance;
	}

	public void salvar(Categoria cat) throws Exception {
		if(cat.getNomeCategoria().replace(" ", "").equals("")) {
			throw new Exception("Por favor preencha o campo 'Nome da Categoria'!");
		}
		CategoriaDAO.getInstance().salvar(cat);
	}
	
	public void atualizar(Categoria cat) throws Exception {
		if(cat.getNomeCategoria().replace(" ", "").equals("")) {
			throw new Exception("Por favor preencha o campo 'Nome da Categoria'!");
		}
		CategoriaDAO.getInstance().atualizar(cat);
	}
	
}
