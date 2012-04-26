package br.ufcg.les.wow.adedonha.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import br.ufcg.les.wow.adedonha.model.Letra;
import br.ufcg.les.wow.util.InteractiveArrayAdapter;

public class ListaItemActivity extends ListActivity {
	
	public static ArrayAdapter<Letra> adapterItens;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (adapterItens == null) {
			adapterItens = new InteractiveArrayAdapter(this,
				getItens());
			
		}
		setListAdapter(adapterItens);
	}
	
	private List<Letra> povoaItens() {
		List<Letra> itens = new ArrayList<Letra>();
		itens.add(new Letra("Nome"));
		itens.add(new Letra("Objeto"));
		itens.add(new Letra("Animal"));
		itens.add(new Letra("Fruta"));
		itens.add(new Letra("Profissão"));
		itens.add(new Letra("Carro"));
		itens.add(new Letra("País"));
		itens.add(new Letra("Cidade"));
		itens.add(new Letra("Serie"));
		itens.add(new Letra("Novela"));
		itens.add(new Letra("Filme"));
		itens.add(new Letra("Ator"));
		itens.add(new Letra("Atriz"));
		itens.add(new Letra("Cor"));
		itens.add(new Letra("Jogo"));
		itens.add(new Letra("Time"));
		itens.add(new Letra("Jogador"));
	
		return itens;
	}

	private List<Letra> getItens() {
		List<Letra> listaItens = povoaItens();
		return listaItens;
	}

}
