package br.ufcg.les.wow.anagrama.persistence.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.ufcg.les.wow.anagrama.enummeration.Nivel;
import br.ufcg.les.wow.anagrama.model.Palavras;
import br.ufcg.les.wow.exceptions.TamanhoDaPalavraInvalidoException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PalavrasDAO extends GenericDAOImpl<Palavras> {
	
	protected String[] todasAsPalavras = { GenericDAOSQLiteHelper.COLUNA_ID,
			GenericDAOSQLiteHelper.COLUNA_PALAVRAS };
	private Map<Nivel, List<List<String>>> palavrasPorNivel;
	
	
	// ---------------------------------------
	
	public PalavrasDAO(Context contexto) {
		super(contexto);
		palavrasPorNivel = new HashMap<Nivel, List<List<String>>>(); 
	}

	public List<List<String>> getPalavrasPorNivel(Nivel nivel) {
		return palavrasPorNivel.get(nivel);
	}

	public Map<Nivel, List<List<String>>> getPalavrasPorNivel() {
		return palavrasPorNivel;
	}

	public void setPalavrasPorNivel(Map<Nivel, List<List<String>>> palavrasPorNivel) {
		this.palavrasPorNivel = palavrasPorNivel;
	}
	
	public void carregarPalavras() {
		List<Palavras> palavras = listarObjetos();
		
		List<List<String>> listaFacil = new ArrayList<List<String>>();
		List<List<String>> listaNormal = new ArrayList<List<String>>();
		List<List<String>> listaDificil = new ArrayList<List<String>>();
		
		while (!palavras.isEmpty()) {
			int posicao = getPosicaoAleatoria(palavras);
			Palavras palavra = palavras.remove(posicao);
			
			if(palavra.getTamanhoDaPalavra() >= 3 && palavra.getTamanhoDaPalavra() <= 4) {
				listaFacil.add(palavra.getPalavras());
			} else if (palavra.getTamanhoDaPalavra() >= 5 && palavra.getTamanhoDaPalavra() <= 6) {
				listaNormal.add(palavra.getPalavras());
			} else if (palavra.getTamanhoDaPalavra() >= 7) {
				listaDificil.add(palavra.getPalavras());
			} else {
				throw new TamanhoDaPalavraInvalidoException(palavra.getTamanhoDaPalavra());
			}
				
		}
		
		palavrasPorNivel.put(Nivel.FACIL, listaFacil); // 3 e 4 letras
		palavrasPorNivel.put(Nivel.NORMAL, listaNormal); // 5 e 6 letras
		palavrasPorNivel.put(Nivel.DIFICIL, listaDificil); // >= 7 letras
	}
	
	private int getPosicaoAleatoria(List<Palavras> palavras) {
		int tamanho = palavras.size();
		Random gerador = new Random();
		int posicao = gerador.nextInt(tamanho);
		
		return posicao;
	}
	
	public void criarPalavras() {
		
		ArrayList<String> anagramasAmor = carregaAnagramaAmor();
		ArrayList<String> anagramasRato = carregaAnagramaRato();
		ArrayList<String> anagramasFio = carregaAnagramaFio();
		
		ArrayList<String> anagramasPrato = carregaAnagramaPrato();
		ArrayList<String> anagramasMorto = carregaAnagramaMorto();
		ArrayList<String> anagramasOlhar = carregaAnagramaOlhar();
		ArrayList<String> anagramasBolero = carregaAnagramaBolero();
		ArrayList<String> anagramasRancor = carregaAnagramaRancor();
		ArrayList<String> anagramasSacar = carregaAnagramaSacar();
		
		ArrayList<String> anagramasGerador = carregaAnagramaGerador();
		ArrayList<String> anagramasRasteira = carregaAnagramaRasteira();
		ArrayList<String> anagramasEstragar = carregaAnagramaEstragar();
		
		inserirListaDeStrings(anagramasAmor);
		inserirListaDeStrings(anagramasRato);
		inserirListaDeStrings(anagramasFio);
		inserirListaDeStrings(anagramasPrato);
		inserirListaDeStrings(anagramasMorto);
		inserirListaDeStrings(anagramasOlhar);
		inserirListaDeStrings(anagramasBolero);
		inserirListaDeStrings(anagramasRancor);
		inserirListaDeStrings(anagramasSacar);
		inserirListaDeStrings(anagramasGerador);
		inserirListaDeStrings(anagramasRasteira);
		inserirListaDeStrings(anagramasEstragar);
	}
	
	private ArrayList<String> carregaAnagramaGerador() {
		ArrayList<String> anagramasGerador = new ArrayList<String>();
		anagramasGerador.add("gerador");
		anagramasGerador.add("regador");
		anagramasGerador.add("regrado");
		anagramasGerador.add("gado");
		anagramasGerador.add("rega");
		anagramasGerador.add("gera");
		anagramasGerador.add("regado");
		anagramasGerador.add("gerado");
		anagramasGerador.add("gerador");
		anagramasGerador.add("dor");
		return anagramasGerador;
	}
	
	private ArrayList<String> carregaAnagramaRasteira() {
		ArrayList<String> anagramasRasteira = new ArrayList<String>();
		anagramasRasteira.add("rasteira");
		anagramasRasteira.add("traseira");
		return anagramasRasteira;
	}
	
	private ArrayList<String> carregaAnagramaEstragar() {
		ArrayList<String> anagramasRasteira = new ArrayList<String>();
		anagramasRasteira.add("estragar");
		anagramasRasteira.add("resgatar");
		anagramasRasteira.add("gata");
		anagramasRasteira.add("tragar");
		anagramasRasteira.add("reatar");
		anagramasRasteira.add("atar");
		anagramasRasteira.add("seta");
		anagramasRasteira.add("ser");
		anagramasRasteira.add("ter");
		anagramasRasteira.add("estar");
		return anagramasRasteira;
	}
	
	private ArrayList<String> carregaAnagramaMorto() {
		ArrayList<String> anagramasMorto = new ArrayList<String>();
		anagramasMorto.add("morto");
		anagramasMorto.add("motor");
		anagramasMorto.add("moto");
		anagramasMorto.add("tom");
		return anagramasMorto;
	}
	
	private ArrayList<String> carregaAnagramaSacar() {
		ArrayList<String> anagramasMorto = new ArrayList<String>();
		anagramasMorto.add("sacar");
		anagramasMorto.add("casar");
		anagramasMorto.add("sacra");
		anagramasMorto.add("cara");
		anagramasMorto.add("casa");
		anagramasMorto.add("caras");
		return anagramasMorto;
	}
	
	private ArrayList<String> carregaAnagramaBolero() {
		ArrayList<String> anagramasBolero = new ArrayList<String>();
		anagramasBolero.add("rebolo");
		anagramasBolero.add("bolero");
		anagramasBolero.add("bolo");
		anagramasBolero.add("rolo");
		anagramasBolero.add("role");
		return anagramasBolero;
	}
	
	private ArrayList<String> carregaAnagramaRancor() {
		ArrayList<String> anagramasBolero = new ArrayList<String>();
		anagramasBolero.add("rancor");
		anagramasBolero.add("roncar");
		anagramasBolero.add("onca");
		anagramasBolero.add("carro");
		anagramasBolero.add("corra");
		anagramasBolero.add("cor");
		return anagramasBolero;
	}
	
	private ArrayList<String> carregaAnagramaOlhar() {
		ArrayList<String> anagramasOlhar = new ArrayList<String>();
		anagramasOlhar.add("olhar");
		anagramasOlhar.add("ralho");
		anagramasOlhar.add("rolha");
		anagramasOlhar.add("olha");
		anagramasOlhar.add("ola");
		anagramasOlhar.add("alo");
		anagramasOlhar.add("ralo");
		return anagramasOlhar;
	}

	private ArrayList<String> carregaAnagramaPrato() {
		ArrayList<String> anagramasPrato = new ArrayList<String>();
		anagramasPrato.add("trapo");
		anagramasPrato.add("tropa");
		anagramasPrato.add("parto");
		anagramasPrato.add("porta");
		anagramasPrato.add("rapto");
		anagramasPrato.add("topar");
		anagramasPrato.add("prato");
		anagramasPrato.add("optar");
		anagramasPrato.add("ato");
		anagramasPrato.add("pato");
		return anagramasPrato;
	}

	private ArrayList<String> carregaAnagramaAmor() {
		ArrayList<String> anagramasAmor = new ArrayList<String>();
		anagramasAmor.add("amor");
		anagramasAmor.add("roma");
		anagramasAmor.add("mora");
		anagramasAmor.add("ramo");
		anagramasAmor.add("amo");
		anagramasAmor.add("mar");
		return anagramasAmor;
	}
	
	private ArrayList<String> carregaAnagramaRato() {
		ArrayList<String> anagramasRato = new ArrayList<String>();
		anagramasRato.add("rato");
		anagramasRato.add("rota");
		anagramasRato.add("toar");
		anagramasRato.add("tora");
		anagramasRato.add("ator");
		anagramasRato.add("ato");
		anagramasRato.add("orta");
		return anagramasRato;
	}
	
	private ArrayList<String> carregaAnagramaFio() {
		ArrayList<String> anagramasFio = new ArrayList<String>();
		anagramasFio.add("fio");
		anagramasFio.add("foi");
		anagramasFio.add("oi");
		return anagramasFio;
	}
	
	public void inserirListaDeStrings(List<String> obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		for (String element : obj) {
		    try {
				out.writeUTF(element);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		byte[] bytes = baos.toByteArray();

		ContentValues values = new ContentValues();
		values.put(GenericDAOSQLiteHelper.COLUNA_PALAVRAS, bytes);
		bancoDeDados.insert(GenericDAOSQLiteHelper.TABELA_PALAVRAS, null, values);
	}

	public void deletarObjeto(Long idObj) {
		bancoDeDados.delete(GenericDAOSQLiteHelper.TABELA_PALAVRAS, GenericDAOSQLiteHelper.COLUNA_ID
				+ " = " + idObj, null);
	}

	public List<Palavras> listarObjetos() {
		List<Palavras> palavras = new ArrayList<Palavras>();
		
		Cursor cursor = bancoDeDados.query(GenericDAOSQLiteHelper.TABELA_PALAVRAS,
				todasAsPalavras, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Palavras palavra = cursorParaPalavra(cursor);
			palavras.add(palavra);
			cursor.moveToNext();
		}
		
		cursor.close();
		return palavras;
	}
	
	public boolean isBdPopulated() {
		Cursor cursor = bancoDeDados.query(GenericDAOSQLiteHelper.TABELA_PALAVRAS,
				todasAsPalavras, null, null, null, null, null);
		if(cursor != null) {
			if(cursor.getCount() > 0) {
				cursor.close();
				return true;
			}
		}
		
		return false;
	}

	public void atualizarObjeto(Long idObj) {
		// TODO Auto-generated method stub
	}
	
	private Palavras cursorParaPalavra(Cursor cursor) {
		Palavras palavras = new Palavras();
		
		palavras.setId(cursor.getLong(0));
		
		byte[] bytes = cursor.getBlob(1);
		palavras.setPalavras(getListaDePalavras(bytes));
		
		return palavras;
	}

	private List<String> getListaDePalavras(byte[] bytes) {
		List<String> palavras = new ArrayList<String>();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		DataInputStream in = new DataInputStream(bais);
		
		try {
			while (in.available() > 0) {
			    String element = in.readUTF();
			    palavras.add(element);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return palavras;
	}

	public void inserirObjeto(String obj) {
		// TODO Auto-generated method stub
	}

	public void inserirObjeto(String obj, int obj2, long obj3) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void limpar() {
		SQLiteDatabase bd = bdHelper.getWritableDatabase();
		bd.execSQL("DROP TABLE IF EXISTS " + GenericDAOSQLiteHelper.TABELA_PALAVRAS);
		bd.execSQL(GenericDAOSQLiteHelper.CREATE_DB_PALAVRAS);
	}
}
