package br.com.cary.jsf.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.cary.dao.CategoriaDAO;
import br.com.cary.entity.Categoria;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		Categoria cat = null;
		if (value != null) {
			Long id = new Long(value);
			cat = CategoriaDAO.getInstance().porId(id);
		}
		return cat;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		Categoria categoria = (Categoria) value;
		if (categoria != null) {
			return categoria.getId().toString();
		} else {
			return null;
		}

	}

}
