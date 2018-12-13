package br.com.caelum.livraria.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDAO {

	public boolean existe(Usuario usuario) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u "
				+ " where u.email = :pEmail and u.senha = :pSenha", Usuario.class);
		
		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		
		Usuario result = null;
		
		try {
			result = query.getSingleResult();
			em.close();
		} catch(NoResultException ex) {
			return false;
		}
		
		return result != null;
		
	}
	
}
