package br.ufcg.les.wow.dicionario.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.ufcg.les.wow.persistence.GenericDAOImpl;
import br.ufcg.les.wow.persistence.GenericSQLiteHelper;

public class DicionarioDAOImpl extends GenericDAOImpl<WordDicionarioDAO> {
	private String[] ALL_WORDS = { GenericSQLiteHelper.COLUNA_ID,
			GenericSQLiteHelper.COLUMN_WORDS_ADEDONHA};
	
	public DicionarioDAOImpl(Context contexto) {
		super(contexto);
	}

	public void inserirObjeto(WordDicionarioDAO word) {
		ContentValues values = new ContentValues();
		values.put(GenericSQLiteHelper.COLUMN_WORDS_ADEDONHA, word.getWord());
		dataBase.insert(GenericSQLiteHelper.TABLE_ADEDONHA, null, values);
	}
	
	public void inserirObjeto(WordDicionarioDAO[] words) {
		for(WordDicionarioDAO word : words) {
			inserirObjeto(word);
		}		
	}

	public void deletarObjeto(Long idObj) {
		dataBase.delete(GenericSQLiteHelper.TABLE_ANAGRAMS, GenericSQLiteHelper.COLUNA_ID
				+ " = " + idObj, null);
	}

	public List<WordDicionarioDAO> listarObjetos() {
			List<WordDicionarioDAO> palavras = new ArrayList<WordDicionarioDAO>();
			
			Cursor cursor = dataBase.query(GenericSQLiteHelper.TABLE_ADEDONHA,
					ALL_WORDS, null, null, null, null, null);
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				WordDicionarioDAO palavra = cursorParaPalavra(cursor);
				palavras.add(palavra);
				cursor.moveToNext();
			}
			
			cursor.close();
			return palavras;
	}
	
	public void clear() {
		SQLiteDatabase bd = bdHelper.getWritableDatabase();
		bd.execSQL("DROP TABLE IF EXISTS " + GenericSQLiteHelper.TABLE_ADEDONHA);
		bd.execSQL(GenericSQLiteHelper.CREATE_DB_ADEDONHA);
		
	}
	
	public void inserirObjeto(List<WordDicionarioDAO> words) {
		Iterator<WordDicionarioDAO> itWords = words.iterator();
		while(itWords.hasNext()) {
			WordDicionarioDAO tmpWord = itWords.next();
			inserirObjeto(tmpWord);
		}
	}
	
	private WordDicionarioDAO cursorParaPalavra(Cursor cursor) {
		return new WordDicionarioDAO(cursor.getLong(0), cursor.getString(1));
	}
	
	public void atualizarObjeto(Long idObj) {
		// TODO Auto-generated method stub
		
	}
}
