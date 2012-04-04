package br.ufcg.les.wow.anagrama.model;

import java.io.Serializable;

public class Usuario implements Comparable<Usuario>, Serializable {
	
	private static final long serialVersionUID = -6252383088422660695L;
	
	private String nome;
	private long id;
	private int pontuacao;
	private long tempo;
	private static final long TEMPO_DEFAULT = 0;
	
	public Usuario(String nome, int pontuacao, long tempo) {
		setNome(nome);
		setPontucao(pontuacao);
		setTempo(tempo);
	}
	
	public Usuario(String nome, int pontuacao) {
		this(nome,pontuacao,TEMPO_DEFAULT);
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setPontucao(int pontuacao) {
		this.pontuacao = pontuacao; 
	}
	
	public int getPontucao() {
		return this.pontuacao;
	}
	
	public void setTempo(long tempo) {
		this.tempo = tempo;
	}
	
	public long getTempo() {
		return this.tempo;
	}

	public int compareTo(Usuario usuario) {
		if(usuario.getPontucao() == this.getPontucao()) {
			return (int) (this.getTempo() - usuario.getTempo());
		} 
		
		return usuario.getPontucao() - this.getPontucao(); 
	}
	
	@Override
	public String toString() {
		return  this.nome + " --- "+ this.pontuacao + " pontos";
			//	+ " --- Tempo: " + converteLongParaString();
	}
	
//	public String converteLongParaString() {
//		Chronometer cronomentro = new Chronometer(null);
//		cronomentro.setBase(tempo);
//		return (String) cronomentro.getText();
//	}

	public void setId(long id) {
		this.id = id;
	}
}