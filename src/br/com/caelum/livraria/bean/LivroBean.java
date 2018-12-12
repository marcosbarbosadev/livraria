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
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Integer autorId;
	private Integer livroId;
	private Livro livro = new Livro();

	public Integer getAutorId() {
		return autorId;
	}
	
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public Integer getLivroId() {
		return livroId;
	}
	
	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}
	
	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void carregarLivroPorId() {
		livro = new DAO<Livro>(Livro.class).buscaPorId(livroId);
	}
	
	public void gravar() {

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if(this.livro.getId() == null) {
			new DAO<Livro>(Livro.class).adiciona(this.livro);
		} else {
			new DAO<Livro>(Livro.class).atualiza(this.livro);
		}
		
		
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
	
	public RedirectView excluir(Livro livro) {
		new DAO<Livro>(Livro.class).remove(livro);
		FacesContext.getCurrentInstance().addMessage("frmLivro", new FacesMessage("Livro excluído com sucesso!"));
		return new RedirectView("livro");
	}
	
	public void removerAutor(Autor autor) {
		this.livro.removerAutor(autor);
	}
	
	public void removerAutorFila() {
		Autor ultimoAutor = this.livro.getAutores().get(this.livro.getAutores().size() - 1);
		this.livro.removerAutor(ultimoAutor);
	}
	
}
