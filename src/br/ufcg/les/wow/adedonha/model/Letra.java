package br.ufcg.les.wow.adedonha.model;

import java.io.Serializable;


public class Letra implements Serializable {

	private static final long serialVersionUID = -1799141069758085073L;
	
	private int idImg;
	private String descricao;
	private boolean selecioada;
	
	public Letra(String descricao) {
		this.setDescricao(descricao);
		this.selecioada = false;
	}
	
	public Letra(String descricao, int idImg) {
		this.setDescricao(descricao);
		this.selecioada = false;
		this.idImg = idImg;
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

	public int getIdImg() {
		return idImg;
	}

	public void setIdImg(int idImg) {
		this.idImg = idImg;
	}
}
