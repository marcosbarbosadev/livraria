package br.com.caelum.livraria.modelo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.caelum.livraria.dao.LivroDao;

public class LivroDataModal extends LazyDataModel<Livro> {

	private static final long serialVersionUID = 1L;

	private LivroDao livroDao;
	private Map<String, Object> filtrosPesquisa = new HashMap<>();
	
	public LivroDataModal(LivroDao livroDao) {
		this.livroDao = livroDao;
		setRowCount(livroDao.quantidadeDeElementos());
	}
	
	@Override
	public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
		List<Livro> results = livroDao.listaTodosPaginada(inicio, quantidade, campoOrdenacao, sentidoOrdenacao, filtrosPesquisa);
		
		if(!filtrosPesquisa.isEmpty()) {
			setRowCount(livroDao.quantidadeDeElementos(filtrosPesquisa));
		} else {
			super.setRowCount(livroDao.quantidadeDeElementos());
		}
		
		return results;
	}
	
	public Map<String, Object> getFiltrosPesquisa() {
		return filtrosPesquisa;
	}
	
	public void setFiltrosPesquisa(Map<String, Object> filtrosPesquisa) {
		this.filtrosPesquisa = filtrosPesquisa;
	}

}
