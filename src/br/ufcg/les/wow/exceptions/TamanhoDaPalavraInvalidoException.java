package br.ufcg.les.wow.exceptions;

public class TamanhoDaPalavraInvalidoException extends RuntimeException  {
	private final static String MENSAGEM = "Esta palavra é pequena ou grande demais: ";

	private static final long serialVersionUID = 3509993377207172545L;
	
	public TamanhoDaPalavraInvalidoException(int tamanho) {
		super(MENSAGEM + "Tamanho = "+ tamanho);
	}
	
	public TamanhoDaPalavraInvalidoException(String palavra) {
		super(MENSAGEM + palavra + " Tamanho: = " + palavra.length());
	}

}
