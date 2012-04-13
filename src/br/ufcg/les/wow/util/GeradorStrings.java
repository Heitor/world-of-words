package br.ufcg.les.wow.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.location.Criteria;
import br.ufcg.les.wow.adedonha.persistence.Palavra;

public class GeradorStrings {
	
	public static String embaralhaPalavra(String palavra) {
		int tamanhoPalavra = palavra.length();
		LinkedList<Integer> posicoesAleatorias = getPosicoesAleatorias(tamanhoPalavra);
		
		char[] palavraEmbaralhada = new char[tamanhoPalavra];
		
		for (int i = 0; i < palavraEmbaralhada.length; i++) {
			palavraEmbaralhada[i] = palavra.charAt(posicoesAleatorias.poll());
		}
		return String.copyValueOf(palavraEmbaralhada);
	}
	
	private static LinkedList<Integer> getPosicoesAleatorias(int lenght) {
		Random gerador = new Random();
		LinkedList<Integer> posicoesAleatorias = new LinkedList<Integer>();
		
		for (int i = 0; i < lenght; i++) {
			Integer pos = gerador.nextInt(lenght);
			while (posicoesAleatorias.contains(pos)) {
				pos = gerador.nextInt(lenght);
			}
			posicoesAleatorias.addLast(pos);
		}
		return posicoesAleatorias;
	}
	
	//FIXME Reduzi o tamanho de letras.
	public static String retornaLetra(List<String> letrasDoTema) {
		Random posicao = new Random();
		return letrasDoTema.get(posicao.nextInt(3));
	}

	@Deprecated
	public static List<Palavra> povoaBanco() {
		List<Palavra> palavras = new ArrayList<Palavra>();
		
		//Nomes ----
		palavras.add(criaPalavra("Alan"));palavras.add(criaPalavra("Allan"));
		palavras.add(criaPalavra("Alana"));palavras.add(criaPalavra("Aline"));
		palavras.add(criaPalavra("Alisson"));palavras.add(criaPalavra("Almir"));
		
		palavras.add(criaPalavra("bruno"));palavras.add(criaPalavra("bruna"));
		palavras.add(criaPalavra("breno"));palavras.add(criaPalavra("biu"));
		palavras.add(criaPalavra("borba"));palavras.add(criaPalavra("brenda"));
		
		
		
		//Objetos
		palavras.add(criaPalavra("Arpa"));palavras.add(criaPalavra("Anel"));
		
		palavras.add(criaPalavra("botão"));palavras.add(criaPalavra("bucha"));
		
		
		//animais
		palavras.add(criaPalavra("Aguia"));palavras.add(criaPalavra("Anta"));
		palavras.add(criaPalavra("arara"));palavras.add(criaPalavra("avestruz"));
		palavras.add(criaPalavra("alce"));
		
		palavras.add(criaPalavra("bufalo"));palavras.add(criaPalavra("baleia"));
		palavras.add(criaPalavra("bode"));palavras.add(criaPalavra("burro"));
		palavras.add(criaPalavra("boi"));
		
		//fruta
		palavras.add(criaPalavra("açaí"));palavras.add(criaPalavra("Abacate"));
		palavras.add(criaPalavra("Alexia"));palavras.add(criaPalavra("Acerola"));
		palavras.add(criaPalavra("Amendoim"));palavras.add(criaPalavra("Abacate"));
		palavras.add(criaPalavra("abacaxi"));palavras.add(criaPalavra("Abacate"));
		
		palavras.add(criaPalavra("banana"));palavras.add(criaPalavra("Babaçu"));
		
		//profissao
		palavras.add(criaPalavra("arquiteto"));palavras.add(criaPalavra("administrador"));
		palavras.add(criaPalavra("advogado"));palavras.add(criaPalavra("agente"));
		
		palavras.add(criaPalavra("bombeiro"));palavras.add(criaPalavra("barbeiro"));
		
		//carro
		palavras.add(criaPalavra("apollo"));palavras.add(criaPalavra("alfa-romeu"));
		palavras.add(criaPalavra("audi"));palavras.add(criaPalavra("aston martin"));
		palavras.add(criaPalavra("astra"));palavras.add(criaPalavra("acorde"));
		palavras.add(criaPalavra("audi"));palavras.add(criaPalavra("aston martin"));
		palavras.add(criaPalavra("agrale"));palavras.add(criaPalavra("agile"));
		palavras.add(criaPalavra("azera"));
		
		palavras.add(criaPalavra("belina"));palavras.add(criaPalavra("brasília"));
		palavras.add(criaPalavra("blazer"));palavras.add(criaPalavra("bmw"));
		palavras.add(criaPalavra("besta"));palavras.add(criaPalavra("bora"));
		palavras.add(criaPalavra("brava"));palavras.add(criaPalavra("Boxer"));
		
		
		//cidade
		palavras.add(criaPalavra("araruna"));palavras.add(criaPalavra("areia"));
		palavras.add(criaPalavra("aguiar"));palavras.add(criaPalavra("alagoa grande"));
		palavras.add(criaPalavra("areial"));palavras.add(criaPalavra("assunção"));
		
		palavras.add(criaPalavra("bananeiras"));palavras.add(criaPalavra("barauna"));
		palavras.add(criaPalavra("bayeux"));palavras.add(criaPalavra("belem"));
		palavras.add(criaPalavra("boa vista"));palavras.add(criaPalavra("bom jesus"));
		
		//serie
		palavras.add(criaPalavra("American Psycho"));palavras.add(criaPalavra("American Dad"));
		palavras.add(criaPalavra("Archer"));palavras.add(criaPalavra("Army Wives"));
		
		
		
		
		return palavras;
	}
	
	public static Palavra criaPalavra(String palavra) {
		return new Palavra(palavra);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}