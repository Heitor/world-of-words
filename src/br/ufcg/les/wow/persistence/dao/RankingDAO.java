package br.ufcg.les.wow.persistence.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufcg.les.wow.adedonha.model.Jogador;

public class RankingDAO implements Serializable {
	
	private static final long serialVersionUID = 7679503695040563323L;
	
	private List<Jogador> listJogador;
	private static final int TAMANHO_DO_RANKING = 10; 
	
	public RankingDAO() {
		listJogador = new ArrayList<Jogador>();
	}
	
	public void carregaRanking(List<Jogador> Jogadors) {
		listJogador = Jogadors;
		Collections.sort(listJogador);
	}
	
	public void sortRanking() {
		Collections.sort(listJogador);
	}
	
	public List<Jogador> getRanking() {
		return listJogador;
	}
	
	public boolean addJogador(Jogador Jogador) {
		listJogador.add(Jogador);
		Collections.sort(listJogador);
		if(listJogador.size() < 5) {
			return listJogador.contains(Jogador);
		} else {
			return listJogador.subList(0, 4).contains(Jogador);
		}
	}
	
	public String toString() {
		return listJogador.toString();
	}
	
	//@Deprecated
	public void carregaRankingDefault() {
		listJogador = new ArrayList<Jogador>(TAMANHO_DO_RANKING);
		for (int i = 0; i < TAMANHO_DO_RANKING; i++) {
			listJogador.add(new Jogador("Default0"+i, i * 10));
		}
		sortRanking();
	}
}