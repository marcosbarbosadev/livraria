package br.com.caelum.livraria.modelo;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.caelum.livraria.dao.LivroDao;

public class LivroDataModal extends LazyDataModel<Livro> {

	private static final long serialVersionUID = 1L;

	private LivroDao livroDao;
	
	public LivroDataModal(LivroDao livroDao) {
		this.livroDao = livroDao;
		super.setRowCount(livroDao.quantidadeDeElementos());
	}
	
	@Override
	public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
		return livroDao.listaTodosPaginada(inicio, quantidade, campoOrdenacao, sentidoOrdenacao);
	}

}
