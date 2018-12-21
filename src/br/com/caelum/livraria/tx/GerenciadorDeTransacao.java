package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transacional
@Interceptor
public class GerenciadorDeTransacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager manager;
	
	@AroundInvoke
    public Object executaTX(InvocationContext context) throws Exception {
		
		System.out.println("Iniciando transação");
        manager.getTransaction().begin();

        System.out.println("Chamando método " + context.getMethod().getName());
        //chama o método do DAO que precisa de TX
        Object retorno = context.proceed();

		System.out.println("Fazendo commit");
        manager.getTransaction().commit();
        
        return retorno;
    }
}