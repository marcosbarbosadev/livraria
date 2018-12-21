package br.com.caelum.livraria.log;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@LogLivraria
@Interceptor
public class TempoDeExecucaoInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@AroundInvoke
	public Object verificaTempoDeExecucao(InvocationContext context) throws Exception {
		
		long antes = System.currentTimeMillis();
		
		Object retorno = context.proceed();
		
		long depois = System.currentTimeMillis();
		
		long tempoGasto = depois - antes;
		System.out.println("Tempo gasto: " + tempoGasto);
		System.out.println("Método executado: " + context.getMethod().getName());
		
		return retorno;
	}
	
}
