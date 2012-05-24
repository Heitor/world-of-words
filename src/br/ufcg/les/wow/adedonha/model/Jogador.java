package br.ufcg.les.wow.adedonha.model;

import java.io.Serializable;
import java.util.HashMap;

public class Jogador implements Comparable<Jogador>, Serializable {
	public static final String JOGADOR = "jogador";
	
	private static final long serialVersionUID = -6252383088422660695L;
	private String nome;

	private int pontuacao;
	private Long tempo = 0L;
	private long id;
	private final HashMap<String, String> resultados;
	
	public Jogador(String nome) {
		this.nome = nome;
		this.resultados = new HashMap<String, String>();
	}
	
	public Jogador(String nome, int pontuacao) {
		this(nome);
		setPontuacao(pontuacao);
	}
	
	public Jogador(String nome, int pontuacao, long tempo) {
		this(nome, pontuacao);
		setTempo(tempo);
	}
	
	public void putResultado(String nome, String valor) {
		this.resultados.put(nome, valor);
	}
	
	public HashMap<String, String> resultado() {
		return this.resultados;
	}
	
	public String nome() {
		return this.nome;
	}
	
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao; 
	}
	
	public int pontuacao() {
		return this.pontuacao;
	}
	
	public void setTempo(long tempo) {
		this.tempo = tempo;
	}
	
	public Long tempo() {
		return this.tempo;
	}

	public int compareTo(Jogador jogador) {
		if(jogador.pontuacao() == this.pontuacao()) {
			return (int) (this.tempo() - jogador.tempo());
		} 
		
		return jogador.pontuacao() - this.pontuacao(); 
	}
	
	@Override
	public String toString() {
		return  this.nome + " --- "+ this.pontuacao + " pontos ";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}