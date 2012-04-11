package br.ufcg.les.wow.exceptions;

public class NoInternetAccessException extends RuntimeException {
	
	private static final long serialVersionUID = -8213961371951881737L;

	public NoInternetAccessException(String msg) {
		super(msg);
	}

}
