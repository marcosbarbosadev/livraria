package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Venda;

public class VendasDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager manager;
	
	public List<Venda> listarTodos() {
		TypedQuery<Venda> query = manager.createQuery("select v from Venda v", Venda.class);
		return query.getResultList();
	}
	
}
