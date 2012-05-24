package br.ufcg.les.wow.persistence.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDAOSQLiteHelper extends SQLiteOpenHelper {

	public static final String COLUNA_ID = "_id";
	public static final String TABELA_USUARIOS = "usuarios";
	public static final String COLUNA_USUARIOS = "usuario";
	public static final String COLUNA_PONTUACAO = "pontuacao";
	public static final String COLUNA_TEMPO = "tempo";

	private static final String NOME_BD = "adedonha.db";
	private static final int VERSAO_DB = 1;
	
	public static final String CREATE_DB_USUARIOS = "create table "
			+ TABELA_USUARIOS + "( " + COLUNA_ID
			+ " integer primary key autoincrement, " + COLUNA_USUARIOS
			+ " text not null, " + COLUNA_PONTUACAO + " text not null, "+ COLUNA_TEMPO + 
			" text not null" +");";

	public GenericDAOSQLiteHelper(Context context) {
		super(context, NOME_BD, null, VERSAO_DB);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		System.out.println(CREATE_DB_USUARIOS);
		database.execSQL(CREATE_DB_USUARIOS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIOS);
		onCreate(db);
	}
}