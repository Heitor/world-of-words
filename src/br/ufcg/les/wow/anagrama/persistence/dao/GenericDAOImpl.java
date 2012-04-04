package br.ufcg.les.wow.anagrama.persistence.dao;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class GenericDAOImpl<T> implements GenericDAO<T> {
	
	protected SQLiteDatabase bancoDeDados;
	protected GenericDAOSQLiteHelper bdHelper;

	public GenericDAOImpl(Context contexto) {
		bdHelper = new GenericDAOSQLiteHelper(contexto);
	}

	public void open() throws SQLException {
		bancoDeDados = bdHelper.getWritableDatabase();
	}

	public void close() {
		bdHelper.close();
	}

	public void limpar() {
		SQLiteDatabase bd = bdHelper.getWritableDatabase();
		bd.execSQL("DROP TABLE IF EXISTS " + GenericDAOSQLiteHelper.TABELA_PALAVRAS);
		bd.execSQL("DROP TABLE IF EXISTS " + GenericDAOSQLiteHelper.TABELA_USUARIOS);
		bdHelper.onCreate(bd);
	}
}
