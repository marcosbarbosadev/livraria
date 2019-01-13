package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.primefaces.model.SortOrder;

import br.com.caelum.livraria.modelo.Autor;

public class AutorDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	private DAO<Autor> dao;
	
	
	@PostConstruct
	void init() {
		dao = new DAO<Autor>(em, Autor.class);
	}

	public void adiciona(Autor t) {
		dao.adiciona(t);
	}

	public void remove(Autor t) {
		dao.remove(t);
	}

	public void atualiza(Autor t) {
		dao.atualiza(t);
	}

	public List<Autor> listaTodos() {
		return dao.listaTodos();
	}

	public Autor buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}

	public List<Autor> listaTodosPaginada(int firstResult, int maxResults, String colunaSort, SortOrder ordemSort, Map<String, Object> filtros) {
		return dao.listaTodosPaginada(firstResult, maxResults, colunaSort, ordemSort, filtros);
	}

	public int quantidadeDeElementos() {
		return dao.quantidadeDeElementos();
	}
	
	
	
}
