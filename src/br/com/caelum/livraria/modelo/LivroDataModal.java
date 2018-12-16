package br.com.caelum.livraria.modelo;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.caelum.livraria.dao.DAO;

public class LivroDataModal extends LazyDataModel<Livro> {

	private static final long serialVersionUID = 1L;
	
	private DAO dao = new DAO(Livro.class);
	
	public LivroDataModal() {
		super.setRowCount(dao.quantidadeDeElementos());
	}
	
	@Override
	public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
		String titulo = (String) filtros.get("titulo");
		String genero = (String) filtros.get("genero");
		
		List<Livro> listaPaginada = null;
		
		if(titulo != null || genero != null) {
			if(titulo != null) {
				listaPaginada = dao.listaTodosPaginada(inicio, quantidade, "titulo", titulo);
			}
			
			if(genero != null) {
				listaPaginada = dao.listaTodosPaginada(inicio, quantidade, "genero", genero);
			}
		} else {
			listaPaginada = dao.listaTodosPaginada(inicio, quantidade, "titulo", null);
		}
		
		
		return listaPaginada;
	}

}
