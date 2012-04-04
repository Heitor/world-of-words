package br.ufcg.les.wow.exceptions;

public class AnagramaNaoExistenteException extends RuntimeException {
	private final static String MENSAGEM = "Esta palavra não é um anagrama: ";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7544954230200385910L;
	
	public AnagramaNaoExistenteException(String palavra){
		super(MENSAGEM + palavra);
	}

}
