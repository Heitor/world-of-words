package br.ufcg.les.wow.adedonha.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import br.ufcg.les.wow.adedonha.enums.Nivel;

public class ConfiguracaoParatida implements Serializable {
	public static final String CONFIGURACAO = "configuracao";
	
	private static final long serialVersionUID = -8818125099949625936L;
	private final Long tempo;
	private final List<Letra> itensDesejados;
	private final Letra letrasDesejadas;
	
	public ConfiguracaoParatida(Long tempo, Letra letraDesejada, List<Letra> itensDesejados) {
		this.tempo = tempo;
		this.letrasDesejadas = letraDesejada;
		this.itensDesejados = itensDesejados;
	}
	
	public ConfiguracaoParatida(Nivel nivel, Long tempo, Letra letrasDesejadas) {
		this(tempo, letrasDesejadas, new LinkedList<Letra>());
	}
	
	public Long tempo() {
		return this.tempo;
	}
	
	public List<Letra> itensDesejados() {
		return this.itensDesejados;
	}
	
	public Letra letraDaPartida() {
		return this.letrasDesejadas;
	}
}
