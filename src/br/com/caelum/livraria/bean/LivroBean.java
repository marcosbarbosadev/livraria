package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Integer autorId;
	private Livro livro = new Livro();

	public Integer getAutorId() {
		return autorId;
	}
	
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public Livro getLivro() {
		return livro;
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		new DAO<Livro>(Livro.class).adiciona(this.livro);
		
		this.livro = new Livro();
	}
	
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
		this.livro.adicionaAutor(autor);
	}
	
	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}
	
	public List<Livro> getListaLivros() {
		List<Livro> livros = new DAO<Livro>(Livro.class).listaTodos();
		return livros;
	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		
		if(!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
		
	}
	
	public String formAutor() {
		return "autor?faces-redirect=true";
	}

}