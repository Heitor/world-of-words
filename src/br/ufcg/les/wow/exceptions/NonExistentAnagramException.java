package br.ufcg.les.wow.exceptions;

public class NonExistentAnagramException extends RuntimeException {
	private final static String MENSAGEM = "Esta palavra n�o � um anagrama: ";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7544954230200385910L;
	
	public NonExistentAnagramException(String palavra){
		super(MENSAGEM + palavra);
	}

}
