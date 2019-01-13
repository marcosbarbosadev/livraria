package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

import br.com.caelum.livraria.modelo.GeneroLivro;

public class DAO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Class<T> classe;
	private EntityManager em;

	public DAO(EntityManager em, Class<T> classe) {
		this.em = em;
		this.classe = classe;
	}

	public void adiciona(T t) {
		em.persist(t);
	}

	public void remove(T t) {
		em.remove(em.merge(t));
	}

	public void atualiza(T t) {
		em.merge(t);
	}

	public List<T> listaTodos() {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		return lista;
	}

	public T buscaPorId(Integer id) {
		T instancia = em.find(classe, id);
		return instancia;
	}

	public int contaTodos() {
		long result = (Long) em.createQuery("select count(n) from livro n")
				.getSingleResult();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults, String colunaSort, SortOrder ordemSort, Map<String, Object> filtros) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(classe);
		Root<T> root = query.from(classe);

		if(!filtros.isEmpty()) {
			List<Predicate> predicates = aplicarFiltros(filtros, builder, root);
			query.where(predicates.toArray(new Predicate[predicates.size()]));
		}
		
		if(colunaSort != null && ordemSort != null) {
			
			Order order = null;
			
			if(ordemSort == SortOrder.ASCENDING) {
				order = builder.asc(root.get(colunaSort));
			} else {
				order = builder.desc(root.get(colunaSort));
			}
			
			query.orderBy(order);
		}
		
		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

		
		return lista;
	}

	private List<Predicate> aplicarFiltros(Map<String, Object> filtros, CriteriaBuilder builder, Root<T> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(filtros.containsKey("titulo") && !filtros.get("titulo").toString().isEmpty()) {
			String titulo = filtros.get("titulo").toString().toLowerCase();
			Predicate like = builder.like(builder.lower(root.<String>get("titulo")), "%" + titulo + "%");
			predicates.add(like);
		}
		
		if(filtros.containsKey("genero")) {
			GeneroLivro genero = (GeneroLivro) filtros.get("genero");
			predicates.add(builder.equal(root.<GeneroLivro>get("genero"), genero));
		}
		
		if(filtros.containsKey("isbn") && !filtros.get("isbn").toString().isEmpty()) {
			String isbn = filtros.get("isbn").toString();
			predicates.add(builder.like(builder.lower(root.<String>get("isbn")), "%" + isbn + "%"));
		}

		if(filtros.containsKey("lancamentoInicio") && filtros.containsKey("lancamentoInicio")) {
			Date dataInicio = (Date) filtros.get("lancamentoInicio");
			Date dataFim = (Date) filtros.get("lancamentoFim");
			predicates.add(builder.between(root.<Date>get("dataLancamento"), dataInicio, dataFim));
		}
		
		if(filtros.containsKey("preco")) {
			Double preco = (Double) filtros.get("preco");
			predicates.add(builder.equal(root.<Double>get("preco"), preco));
		}
		return predicates;
	}
	
	public int quantidadeDeElementos() {
		return quantidadeDeElementos(new HashMap<>());
    }
	
	public int quantidadeDeElementos(Map<String, Object> filtros) {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<T> root = query.from(classe);
		query.select(builder.count(root));
		
		if(!filtros.isEmpty()) {
			List<Predicate> predicates = aplicarFiltros(filtros, builder, root);
			query.where(predicates.toArray(new Predicate[predicates.size()]));
		}
		
		TypedQuery<Long> hql = em.createQuery(query);
		Long result = hql.getSingleResult();
		
		return result.intValue();
		
	}

}
