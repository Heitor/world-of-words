package br.ufcg.les.wow.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsersDAOImpl extends GenericDAOImpl<User>{
	private String[] ALL_USERS = { GenericSQLiteHelper.COLUNA_ID,
			GenericSQLiteHelper.COLUMN_USERNAME,
			GenericSQLiteHelper.COLUMN_GAME_TYPE,
			GenericSQLiteHelper.COLUMN_POINTING,
			GenericSQLiteHelper.COLUMN_TIME};

	public UsersDAOImpl(Context contexto) {
		super(contexto);
	}
	
	public void inserirObjeto(User user) {
		ContentValues values = new ContentValues();
		values.put(GenericSQLiteHelper.COLUMN_USERNAME, user.getUserName());
		values.put(GenericSQLiteHelper.COLUMN_GAME_TYPE, user.getGameType());
		values.put(GenericSQLiteHelper.COLUMN_POINTING, user.getPointing());
		values.put(GenericSQLiteHelper.COLUMN_TIME, user.getTime());
		dataBase.insert(GenericSQLiteHelper.TABLE_USERS, null, values);
	}
	
	public void inserirObjeto(User[] users) {
		for(User user : users) {
			inserirObjeto(user);
		}
	}

	public void deletarObjeto(Long idObj) {
		dataBase.delete(GenericSQLiteHelper.TABLE_USERS, GenericSQLiteHelper.COLUNA_ID
				+ " = " + idObj, null);
	}

	public List<User> listarObjetos() {
		List<User> users = new ArrayList<User>();
		
		Cursor cursor = dataBase.query(GenericSQLiteHelper.TABLE_ADEDONHA,
				ALL_USERS, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User user = cursorParaPalavra(cursor);
			users.add(user);
			cursor.moveToNext();
		}
		
		cursor.close();
		return users;
	}

	public void clear() {
		SQLiteDatabase bd = bdHelper.getWritableDatabase();
		bd.execSQL("DROP TABLE IF EXISTS " + GenericSQLiteHelper.TABLE_USERS);
		bd.execSQL(GenericSQLiteHelper.CREATE_DB_USERS);
	}

	public void inserirObjeto(List<User> users) {
		Iterator<User> itUsers = users.iterator();
		while(itUsers.hasNext()) {
			User tmpUser = itUsers.next();
			inserirObjeto(tmpUser);
		}		
	}
	
	public void atualizarObjeto(Long idObj) {
		// TODO Auto-generated method stub		
	}
	
	private User cursorParaPalavra(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getLong(0));
		user.setUserName(cursor.getString(1));
		user.setGameType(cursor.getInt(2));
		user.setPointing(cursor.getInt(3));
		user.setTime(cursor.getLong(4));
		return user;
	}
}
