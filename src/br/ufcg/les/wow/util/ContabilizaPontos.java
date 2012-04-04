package br.ufcg.les.wow.util;

import java.util.Iterator;
import java.util.List;

public class ContabilizaPontos {
	
	private static final int TAMANHO_MINIMO_DA_PALAVRA = 3;
	private static final int PONTUACAO_MINIMA = 50;
	private static final int VALOR_DA_LETRA = 25;
	private static final int VALOR_PALAVRA_INVALIDA = 0;
	private static final int VALOR_ANAGRAMAS_INVALIDOS = 0;
	
	public static int contabilizaPontosDaPalavra(String palavra) {
		if(palavra == null) {
			return VALOR_PALAVRA_INVALIDA;
		}
		
		int pontuacao = PONTUACAO_MINIMA;
		int tamanhoPalavra = palavra.length();
		
		if(palavra.length() < TAMANHO_MINIMO_DA_PALAVRA) {
			return VALOR_PALAVRA_INVALIDA;
		} else {
			for (int i = 3; i < tamanhoPalavra; i++) {
				pontuacao += VALOR_DA_LETRA;
			}
		}
		
		return pontuacao;
	}
	
	public static int contabilizaPontuacaoTotal(List<String> anagramasEncontrados) {
		if(anagramasEncontrados == null) {
			return VALOR_ANAGRAMAS_INVALIDOS;
		}
		
		int pontuacaoTotal = 0;
		Iterator<String> itAnagramas = anagramasEncontrados.iterator();
		
		while(itAnagramas.hasNext()) {
			String palavra = itAnagramas.next();
			pontuacaoTotal += contabilizaPontosDaPalavra(palavra);
		}
		
		return pontuacaoTotal;
	}
}
