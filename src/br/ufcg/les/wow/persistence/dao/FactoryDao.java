package br.ufcg.les.wow.persistence.dao;

public class FactoryDao {
	
	private static RankingDAO instance;
	
	private static UsuarioDAO instanceUsuario;
	
	private FactoryDao() {
	}
	
	public static synchronized RankingDAO getRankingDaoInstance() {
		if (instance == null) {
			instance = new RankingDAO();
		}
		return instance;
	}
	
	public static synchronized UsuarioDAO getUsuarioDaoInstance() {
		if (instanceUsuario == null) {
			instanceUsuario = new UsuarioDAO(null);
		}
		return instanceUsuario;
	}

}
