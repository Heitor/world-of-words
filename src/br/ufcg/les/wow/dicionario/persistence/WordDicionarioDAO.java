package br.ufcg.les.wow.dicionario.persistence;

public class WordDicionarioDAO {
	private long id;
	private String palavra;
	private String level;
	
	public WordDicionarioDAO(String word) {
		setWord(word);
	}
	
	public WordDicionarioDAO(long id, String word) {
		this(word);
		setId(id);
	}
	
	public WordDicionarioDAO(String word, String level) {
		this(word);
		setLevel(level);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getWord() {
		return palavra;
	}
	public void setWord(String palavra) {
		this.palavra = palavra;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
