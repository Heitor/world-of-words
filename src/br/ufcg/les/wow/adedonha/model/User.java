package br.ufcg.les.wow.adedonha.model;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable {
	
	private static final long serialVersionUID = -6252383088422660695L;
	
	private String nome;
	private long id;
	private int pontuacao;
	private int gameType;
	private long tempo;
	
	public User() {}
	
	public User(String nome, int pontuacao, long tempo, int gameType) {
		setUserName(nome);
		setPointing(pontuacao);
		setTime(tempo);
		setTime(gameType);
	}
	
	public User(String nome, int pontuacao) {
		this();
		setUserName(nome);
		setPointing(pontuacao);
	}
	
	public void setUserName(String nome) {
		this.nome = nome;
	}
	
	public String getUserName() {
		return this.nome;
	}
	
	public void setPointing(int pontuacao) {
		this.pontuacao = pontuacao; 
	}
	
	public int getPointing() {
		return this.pontuacao;
	}
	
	public void setTime(long tempo) {
		this.tempo = tempo;
	}
	
	public long getTime() {
		return this.tempo;
	}

	public int compareTo(User usuario) {
		if(usuario.getPointing() == this.getPointing()) {
			return (int) (this.getTime() - usuario.getTime());
		} 
		
		return usuario.getPointing() - this.getPointing(); 
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return  this.nome + " --- "+ this.pontuacao + " pontos";
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gametype) {
		this.gameType = gametype;
	}
}