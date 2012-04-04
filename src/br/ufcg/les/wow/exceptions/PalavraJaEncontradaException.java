package br.ufcg.les.wow.exceptions;

public class PalavraJaEncontradaException extends RuntimeException {
	private final static String MENSAGEM = "Esta palavra já foi encontrada: ";
	
	private static final long serialVersionUID = -869826511138338983L;
	
	public PalavraJaEncontradaException(String palavra){
		super(MENSAGEM + palavra);
	}

}
