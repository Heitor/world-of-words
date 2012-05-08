package br.ufcg.les.wow.adedonha.model;

import java.io.Serializable;
import java.util.List;

public class Palavras implements Serializable{
	
	private static final long serialVersionUID = 1359788216720090824L;
	private long id;
	private String anagramas;
	private List<String> palavras;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setAnagramas(String anagramas) {
		this.anagramas = anagramas;
	}
	
	public String getAnagramas() {
		return this.anagramas;
	}
	
	public boolean addPalavra(String palavra) {
		return this.palavras.add(palavra);
	}
	
	public void setPalavras(List<String> palavras) {
		this.palavras = palavras;
	}
	
	public List<String> getPalavras() {
		return this.palavras;
	}
	
	public int getTamanhoDaPalavra() {
		return this.palavras.get(0).length();
	}
	
	@Override
	public String toString() {
		return this.anagramas;
	}
}
