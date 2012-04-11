package br.ufcg.les.wow.adedonha.persistence;

public class WordAdedonhaDAO {
	private long id;
	private String palavra;
	private String level;
	
	public WordAdedonhaDAO(String word) {
		setWord(word);
	}
	
	public WordAdedonhaDAO(long id, String word) {
		this(word);
		setId(id);
	}
	
	public WordAdedonhaDAO(String word, String level) {
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
