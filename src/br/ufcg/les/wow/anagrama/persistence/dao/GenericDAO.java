package br.ufcg.les.wow.anagrama.persistence.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
	
	public void inserirObjeto(String obj);
	
	public void inserirObjeto(String obj, int obj2, long obj3);
	
	public void  deletarObjeto(Long idObj);
	
	public List<T> listarObjetos();
	
	public void atualizarObjeto(Long idObj);
	
	public void open() throws SQLException;
	
	public void close();
	
	public void inserirListaDeStrings(List<String> obj);
	
	public void limpar();
}
