package br.com.caelum.livraria.modelo;

public enum GeneroLivro {
	ROMANCE("Romance"),
	DRAMA("Drama"),
	ACAO("Ação");
	
	private String descricao;
	
	GeneroLivro(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
