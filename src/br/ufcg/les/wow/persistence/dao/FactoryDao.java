package br.ufcg.les.wow.persistence.dao;

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
