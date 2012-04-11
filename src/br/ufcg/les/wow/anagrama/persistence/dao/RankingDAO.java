package br.ufcg.les.wow.anagrama.persistence.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufcg.les.wow.persistence.User;

public class RankingDAO implements Serializable {

	private static final long serialVersionUID = -7056832528147460984L;
	
	private List<User> listUsuario;
	private static final int TAMANHO_DO_RANKING = 5; 
	
	public RankingDAO() {
		listUsuario = new ArrayList<User>();
	}
	
	public void carregaRanking(List<User> usuarios) {
		listUsuario = usuarios;
		Collections.sort(listUsuario);
	}
	
	public List<User> getRanking() {
		return listUsuario;
	}
	
	public boolean addUsuario(User usuario) {
		listUsuario.add(usuario);
		Collections.sort(listUsuario);
		if(listUsuario.size() < 5) {
			return listUsuario.contains(usuario);
		} else {
			return listUsuario.subList(0, 4).contains(usuario);
		}
	}
	
	public String toString() {
		return listUsuario.toString();
	}
	
	@Deprecated
	public List<User> carregaRankingDefault() {
		List<User> listaUsuariosDefault = new ArrayList<User>(TAMANHO_DO_RANKING);
		for (int i = 0; i < TAMANHO_DO_RANKING; i++) {
			listaUsuariosDefault.add(new User("Default0"+i, i * 10));
		}
		
		return listaUsuariosDefault;
	}
}