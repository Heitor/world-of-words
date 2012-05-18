package br.ufcg.les.wow.adedonha.model;

import java.io.Serializable;


public class Letra implements Serializable {

	private static final long serialVersionUID = -1799141069758085073L;
	
	
	private String descricao;
	private boolean selecioada;
	
	public Letra(String descricao) {
		this.setDescricao(descricao);
		this.selecioada = false;
	}
	
	public boolean isSelecionada() {
		return selecioada;
	}
	
	public void setSelecioada(boolean selecioada) {
		this.selecioada = selecioada;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
}
