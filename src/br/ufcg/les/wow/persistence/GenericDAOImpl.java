package br.ufcg.les.wow.persistence;

import java.sql.SQLException;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class GenericDAOImpl<T> implements GenericDAO<T> {
	
	protected SQLiteDatabase dataBase;
	protected GenericSQLiteHelper bdHelper;

	public GenericDAOImpl(Context contexto) {
		bdHelper = new GenericSQLiteHelper(contexto);
	}

	public void open() throws SQLException {
		dataBase = bdHelper.getWritableDatabase();
	}

	public void close() {
		bdHelper.close();
	}
}
