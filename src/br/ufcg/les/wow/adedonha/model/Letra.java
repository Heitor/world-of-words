package br.ufcg.les.wow.adedonha.model;

import java.io.Serializable;

public class Letra implements Serializable {

	private static final long serialVersionUID = -1799141069758085073L;
	
	
	private String letra;
	private boolean selecioada;
	
	public Letra(String letra) {
		this.letra = letra;
		this.selecioada = false;
	}
	
	
	public String getLetra() {
		return letra;
	}
	
	public void setLetra(String letra) {
		this.letra = letra;
	}
	
	public boolean isSelecioada() {
		return selecioada;
	}
	
	public void setSelecioada(boolean selecioada) {
		this.selecioada = selecioada;
	}
}
