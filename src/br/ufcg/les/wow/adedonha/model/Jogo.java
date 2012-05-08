package br.ufcg.les.wow.adedonha.model;

import java.io.Serializable;
import java.util.List;

import br.ufcg.les.wow.adedonha.model.Letra;
import br.ufcg.les.wow.adedonha.enums.Nivel;


public class Jogo implements Serializable {
	
	private static final long serialVersionUID = -5011731639297651726L;
	
	private String nomeJogador;
	private int pontuacao;
	private Nivel nivel = Nivel.NORMAL;
	private String nivelString = "Normal";
	private Long tempo;
	
	private List<Letra> letrasDesejadas;
	private List<Letra> itensDesejados;
	
	public Jogo(String nomeJogador, Nivel nivel, List<Letra> letrasDesejadas) {
		setNivel(nivel);
		setNomeJogador(nomeJogador);
		this.letrasDesejadas = letrasDesejadas;
	}

	public Jogo(String nomeJogador, String nivel, List<Letra> letrasDesejadas,
			List<Letra> itensDesejados) {
		setNomeJogador(nomeJogador);
		this.nivelString = nivel;
		this.letrasDesejadas = letrasDesejadas;
		this.itensDesejados = itensDesejados;
	}

	public String getNomeJogador() {
		return nomeJogador;
	}

	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		if (nivel != null) {
			this.nivel = nivel;
		}
	}

	public Long getTempo() {
		return tempo;
	}

	public void setTempo(Long tempo) {
		this.tempo = tempo;
	}

	public List<Letra> getLetrasDesejadas() {
		return letrasDesejadas;
	}

	public void setLetrasDesejadas(List<Letra> letrasDesejadas) {
		this.letrasDesejadas = letrasDesejadas;
	}

	public String getNivelString() {
		return nivelString;
	}

	public void setNivelString(String nivelString) {
		this.nivelString = nivelString;
	}

	public List<Letra> getItensDesejados() {
		return itensDesejados;
	}

	public void setItensDesejados(List<Letra> itensDesejados) {
		this.itensDesejados = itensDesejados;
	}
}
