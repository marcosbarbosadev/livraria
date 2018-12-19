package br.com.caelum.livraria.teste;

import java.util.Calendar;
import java.util.Random;

public class NumerosAleatorios {

	private Random gerador = new Random(System.currentTimeMillis());

	public void geradorInteiros() {

		int resultado = gerador.nextInt(1000);
		System.out.println(resultado);

		int resultado2 = gerador.nextInt(10);
		System.out.println(resultado2);
		
	}
	
	public void geradorBoolean() {
	
		boolean valor = gerador.nextBoolean();
		System.out.println(valor);

		boolean valor2 = gerador.nextBoolean();
		System.out.println(valor2);		
		
	}
	
}
