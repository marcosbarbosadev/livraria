package br.com.caelum.livraria.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.caelum.livraria.modelo.GeneroLivro;

@FacesConverter("generoConverter")
public class GeneroConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value != null) {
			return GeneroLivro.valueOf(value);
		}
			
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if(value != null && value instanceof GeneroLivro) {
			GeneroLivro genero = (GeneroLivro) value;
			return genero.name();
		}
			
		return null;
		
	}

}
