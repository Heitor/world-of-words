package br.ufcg.les.wow.anagrama.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import br.ufcg.les.wow.persistence.GenericDAOImpl;
import br.ufcg.les.wow.persistence.GenericSQLiteHelper;
import br.ufcg.les.wow.persistence.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO extends GenericDAOImpl<User> {
	public UsuarioDAO(Context contexto) {
		super(contexto);
	}

	private String[] todasOsUsuarios = { GenericSQLiteHelper.COLUNA_ID,
			GenericSQLiteHelper.COLUMN_USERNAME,
			GenericSQLiteHelper.COLUMN_POINTING,
			GenericSQLiteHelper.COLUMN_TIME};
	
	public void inserirObjeto(String obj, int obj2, long obj3) {
		ContentValues values = new ContentValues();
		values.put(GenericSQLiteHelper.COLUMN_USERNAME, obj);
		values.put(GenericSQLiteHelper.COLUMN_POINTING, obj2);
		values.put(GenericSQLiteHelper.COLUMN_TIME, obj3);
		dataBase.insert(GenericSQLiteHelper.TABLE_USERS, null,
				values);
	}

	public void deletarObjeto(Long idObj) {
		dataBase.delete(GenericSQLiteHelper.TABLE_USERS,
				GenericSQLiteHelper.COLUNA_ID
				+ " = " + idObj, null);
	}

	public List<User> listarObjetos() {
		List<User> usuarios = new ArrayList<User>();
		
		Cursor cursor = dataBase.query(GenericSQLiteHelper.TABLE_USERS,
				todasOsUsuarios, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User palavra = cursorParaUsuario(cursor);
			usuarios.add(palavra);
			cursor.moveToNext();
		}
		
		cursor.close();
		return usuarios;
	}

	public void atualizarObjeto(Long idObj) {
		// TODO Auto-generated method stub
		
	}

	private User cursorParaUsuario(Cursor cursor) {
		User usuario = new User();
		usuario.setId(cursor.getLong(0));
		usuario.setUserName(cursor.getString(1));
		usuario.setPointing(cursor.getInt(2));
		usuario.setTime(cursor.getLong(3));
		// TODO set game type
		return usuario;
	}
	
	public void inserirObjeto(String obj) {
		// TODO Auto-generated method stub
		
	}
	
	public void inserirListaDeStrings(List<String> obj) {
		// TODO Auto-generated method stub
		
	}
	
	public void clear() {
		SQLiteDatabase bd = bdHelper.getWritableDatabase();
		bd.execSQL("DROP TABLE IF EXISTS " + GenericSQLiteHelper.TABLE_USERS);
		bd.execSQL(GenericSQLiteHelper.CREATE_DB_USERS);
	}
	
	public boolean isBdPopulated() {
		Cursor cursor = dataBase.query(GenericSQLiteHelper.TABLE_USERS,
				todasOsUsuarios, null, null, null, null, null);
		if(cursor != null) {
			if(cursor.getCount() > 0) {
				cursor.close();
				return true;
			}
		}
		return false;
	}

	public void inserirObjeto(String[] obj) {
		// TODO Auto-generated method stub
		
	}

	public void inserirObjeto(User obj) {
		// TODO Auto-generated method stub
		
	}

	public void inserirObjeto(User[] obj) {
		// TODO Auto-generated method stub
		
	}

	public void inserirObjeto(List<User> obj) {
		// TODO Auto-generated method stub
		
	}

}
