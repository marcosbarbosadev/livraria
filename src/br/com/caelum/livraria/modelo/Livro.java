package br.com.caelum.livraria.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Livro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	private String titulo;
	private String isbn;
	private double preco;
	private String genero;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataLancamento = Calendar.getInstance();

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Autor> autores = new ArrayList<Autor>();

	public Livro() {}
	
	public void adicionaAutor(Autor autor) {
		this.autores.add(autor);
	}

	public void removerAutor(Autor autor) {
		this.autores.remove(autor);
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getGenero() {
		return genero;
	}
	
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar calendar) {
		this.dataLancamento = calendar;
	}
	
	public List<Autor> getAutores() {
		return autores;
	}
	
}