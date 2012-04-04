package br.ufcg.les.wow.anagrama.persistence;

import br.ufcg.les.wow.anagrama.persistence.dao.RankingDAO;

public class FactoryDao {
	
	private static RankingDAO instance;
	
	private FactoryDao() {
	}
	
	public static synchronized RankingDAO getRankingDaoInstance() {
		if (instance == null) {
			instance = new RankingDAO();
		}
		return instance;
	}

}
