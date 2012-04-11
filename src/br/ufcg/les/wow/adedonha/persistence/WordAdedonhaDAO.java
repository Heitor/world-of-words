package br.ufcg.les.wow.adedonha.persistence;

public class WordAdedonhaDAO {
	private long id;
	private String palavra;
	
	public WordAdedonhaDAO(String word) {
		setWord(word);
	}
	
	public WordAdedonhaDAO(long id, String word) {
		this(word);
		setId(id);
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

}
