package br.ufcg.les.wow.anagrama.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import br.ufcg.les.wow.anagrama.model.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO extends GenericDAOImpl<Usuario> {
	public UsuarioDAO(Context contexto) {
		super(contexto);
	}

	private String[] todasOsUsuarios = { GenericDAOSQLiteHelper.COLUNA_ID,
			GenericDAOSQLiteHelper.COLUNA_USUARIOS,
			GenericDAOSQLiteHelper.COLUNA_PONTUACAO,
			GenericDAOSQLiteHelper.COLUNA_TEMPO};
	
	public void inserirObjeto(String obj, int obj2, long obj3) {
		ContentValues values = new ContentValues();
		values.put(GenericDAOSQLiteHelper.COLUNA_USUARIOS, obj);
		values.put(GenericDAOSQLiteHelper.COLUNA_PONTUACAO, obj2);
		values.put(GenericDAOSQLiteHelper.COLUNA_TEMPO, obj3);
		bancoDeDados.insert(GenericDAOSQLiteHelper.TABELA_USUARIOS, null,
				values);
	}

	public void deletarObjeto(Long idObj) {
		bancoDeDados.delete(GenericDAOSQLiteHelper.TABELA_USUARIOS,
				GenericDAOSQLiteHelper.COLUNA_ID
				+ " = " + idObj, null);
	}

	public List<Usuario> listarObjetos() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		Cursor cursor = bancoDeDados.query(GenericDAOSQLiteHelper.TABELA_USUARIOS,
				todasOsUsuarios, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Usuario palavra = cursorParaUsuario(cursor);
			usuarios.add(palavra);
			cursor.moveToNext();
		}
		
		cursor.close();
		return usuarios;
	}

	public void atualizarObjeto(Long idObj) {
		// TODO Auto-generated method stub
		
	}

	private Usuario cursorParaUsuario(Cursor cursor) {
		Usuario usuario = new Usuario("Unknown", 0, 0);
		usuario.setId(cursor.getLong(0));
		usuario.setNome(cursor.getString(1));
		usuario.setPontucao(cursor.getInt(2));
		usuario.setTempo(cursor.getLong(3));
		return usuario;
	}
	
	public void inserirObjeto(String obj) {
		// TODO Auto-generated method stub
		
	}
	
	public void inserirListaDeStrings(List<String> obj) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void limpar() {
		SQLiteDatabase bd = bdHelper.getWritableDatabase();
		bd.execSQL("DROP TABLE IF EXISTS " + GenericDAOSQLiteHelper.TABELA_USUARIOS);
		bd.execSQL(GenericDAOSQLiteHelper.CREATE_DB_USUARIOS);
	}
	
	public boolean isBdPopulated() {
		Cursor cursor = bancoDeDados.query(GenericDAOSQLiteHelper.TABELA_USUARIOS,
				todasOsUsuarios, null, null, null, null, null);
		if(cursor != null) {
			if(cursor.getCount() > 0) {
				cursor.close();
				return true;
			}
		}
		return false;
	}

}
