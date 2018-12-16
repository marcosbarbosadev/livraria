package br.com.caelum.livraria.bean;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModal;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Integer autorId;
	private Integer livroId;
	private Livro livro = new Livro();
	private List<Livro> livros;
	private LivroDataModal livroDataModal = new LivroDataModal();
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");


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
		
		carregarLivros();
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
		
		if(livros == null)
			carregarLivros();
		
		return livros;
	}

	private void carregarLivros() {
		livros = new DAO<Livro>(Livro.class).listaTodos();
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
	
	public void excluir(Livro livro) {
		new DAO<Livro>(Livro.class).remove(livro);
		FacesContext.getCurrentInstance().addMessage("frmLivro", new FacesMessage("Livro excluído com sucesso!"));
		carregarLivros();
	}
	
	public void removerAutor(Autor autor) {
		this.livro.removerAutor(autor);
	}
	
	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) { // java.util.Locale

        //tirando espaços do filtro
        String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

        System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);

        // o filtro é nulo ou vazio?
        if (textoDigitado == null || textoDigitado.equals("")) {
            return true;
        }

        // elemento da tabela é nulo?
        if (valorColuna == null) {
            return false;
        }

        try {
            // fazendo o parsing do filtro para converter para Double
            Double precoDigitado = Double.valueOf(textoDigitado);
            Double precoColuna = (Double) valorColuna;

            // comparando os valores, compareTo devolve um valor negativo se o value é menor do que o filtro
            return precoColuna.compareTo(precoDigitado) < 0;

        } catch (NumberFormatException e) {

            // usuario nao digitou um numero
            return false;
        }
	}	
	
	
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
	
	public LivroDataModal getLivroDataModal() {
		return livroDataModal;
	}
	
	public void setLivroDataModal(LivroDataModal livroDataModal) {
		this.livroDataModal = livroDataModal;
	}
	
	public List<String> getGeneros() {
		return generos;
	}
	
}
