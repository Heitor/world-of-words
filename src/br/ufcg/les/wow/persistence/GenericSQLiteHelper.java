package br.ufcg.les.wow.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_ANAGRAMS = "anagramas";
	public static final String COLUMN_WORDS_ANAGRAMS = "anagrama";
	
	public static final String TABLE_USERS = "users";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_GAME_TYPE= "gametype";
	public static final String COLUMN_POINTING = "pointing";
	public static final String COLUMN_TIME = "time";
	
	public static final String TABLE_ADEDONHA = "adedonha";
	public static final String COLUMN_WORDS_ADEDONHA = "words_adedonha";

	private static final String NOME_BD = "wow.db";
	public static final String COLUNA_ID = "_id";
	private static final int VERSAO_DB = 1;
	
	public static final String CREATE_DB_ADEDONHA = "create table "
			+ TABLE_ADEDONHA + "( " + COLUNA_ID + " integer primary key autoincrement, " 
			+ COLUMN_WORDS_ADEDONHA + " text not null);";

	public static final String CREATE_DB_ANAGRAMS = "create table "
			+ TABLE_ANAGRAMS + "( " + COLUNA_ID
			+ " integer primary key autoincrement, " + COLUMN_WORDS_ANAGRAMS
			+ " BLOB);";
	
	public static final String CREATE_DB_USERS = "create table "
			+ TABLE_USERS + "( " + COLUNA_ID + " integer primary key autoincrement, " 
			+ COLUMN_USERNAME + " text not null, " 
			+ COLUMN_GAME_TYPE + " integer, "
			+ COLUMN_POINTING + " integer, "
			+ COLUMN_TIME + " text" +");";

	public GenericSQLiteHelper(Context context) {
		super(context, NOME_BD, null, VERSAO_DB);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_DB_ADEDONHA);
		database.execSQL(CREATE_DB_USERS);
		
		database.execSQL(CREATE_DB_ANAGRAMS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADEDONHA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANAGRAMS);
		
		onCreate(db);
	}
}