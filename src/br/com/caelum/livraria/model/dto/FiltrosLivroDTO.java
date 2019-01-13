package br.com.caelum.livraria.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.com.caelum.livraria.modelo.GeneroLivro;

public class FiltrosLivroDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String titulo;
	private GeneroLivro genero;
	private String isbn;
	private Date lancamentoInicio;
	private Date lancamentoFim;
	private Double preco;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public GeneroLivro getGenero() {
		return genero;
	}

	public void setGenero(GeneroLivro genero) {
		this.genero = genero;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getLancamentoInicio() {
		return lancamentoInicio;
	}

	public void setLancamentoInicio(Date lancamentoInicio) {
		this.lancamentoInicio = lancamentoInicio;
	}

	public Date getLancamentoFim() {
		return lancamentoFim;
	}

	public void setLancamentoFim(Date lancamentoFim) {
		this.lancamentoFim = lancamentoFim;
	}
	
	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
}
