package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModal;
import br.com.caelum.livraria.tx.Transacional;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer autorId;
	private Integer livroId;
	private Livro livro = new Livro();
	private List<Livro> livros;
	private LivroDataModal livroDataModal;
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");

	@Inject
	private LivroDao livroDao;
	
	@Inject
	private AutorDao autorDao;
	
	@PostConstruct
	void init() {
		livroDataModal = new LivroDataModal(livroDao);
	}
	
	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}
	
	public void carregarLivroPorId() {
		livro = livroDao.buscaPorId(livroId);
	}
	
	@Transacional
	public void gravar() {

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if(this.livro.getId() == null) {
			livroDao.adiciona(this.livro);
		} else {
			livroDao.atualiza(this.livro);
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livro salvo com sucesso."));
		carregarLivros();
		this.livro = new Livro();
	}
	
	public void gravarAutor() {
		Autor autor = autorDao.buscaPorId(autorId);
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
		livros = livroDao.listaTodos();
	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		
		if(!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Deveria começar com 1", null));
		}
		
	}
	
	public String formAutor() {
		return "autor?faces-redirect=true";
	}
	
	@Transacional
	public void excluir(Livro livro) {
		livroDao.remove(livro);
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
