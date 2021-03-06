package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.primefaces.model.SortOrder;

import br.com.caelum.livraria.log.LogLivraria;
import br.com.caelum.livraria.modelo.Livro;

public class LivroDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	private DAO<Livro> dao;
	
	@PostConstruct
	void init() {
		dao = new DAO<Livro>(em, Livro.class);
	}

	public void adiciona(Livro t) {
		dao.adiciona(t);
	}

	public void remove(Livro t) {
		dao.remove(t);
	}

	public void atualiza(Livro t) {
		dao.atualiza(t);
	}

	@LogLivraria
	public List<Livro> listaTodos() {
		return dao.listaTodos();
	}

	public Livro buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}

	@LogLivraria
	public List<Livro> listaTodosPaginada(int firstResult, int maxResults, String campoSort, SortOrder ordemSort, Map<String, Object> filtros) {
		return dao.listaTodosPaginada(firstResult, maxResults, campoSort, ordemSort, filtros);
	}

	public int quantidadeDeElementos() {
		return dao.quantidadeDeElementos();
	}
	
	public int quantidadeDeElementos(Map<String, Object> filtros) {
		return dao.quantidadeDeElementos(filtros);
	}
	
}
