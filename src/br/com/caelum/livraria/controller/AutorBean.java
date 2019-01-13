package br.com.caelum.livraria.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.tx.Transacional;

@Named
@ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();
	
	@Inject
	private AutorDao dao;
	
	@Inject
	private FacesContext context;

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
	@Transacional
	public void gravar() {
		
		if(this.autor.getId() == null) {
			dao.adiciona(this.autor);
		} else {
			dao.atualiza(this.autor);
		}
		
		this.autor = new Autor();

	}
	
	public List<Autor> getAutores() {
		return dao.listaTodos();
	}
	
	@Transacional
	public void excluir(Autor autor) {
		dao.remove(autor);
		context.getCurrentInstance().addMessage("frmAutor", new FacesMessage("Autor excluído com sucesso!"));
	}
}
