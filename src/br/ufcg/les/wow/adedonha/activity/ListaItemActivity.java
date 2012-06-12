package br.ufcg.les.wow.adedonha.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.model.Letra;
import br.ufcg.les.wow.util.InteractiveArrayAdapter;

public class ListaItemActivity extends ListActivity {
	
	public static ArrayAdapter<Letra> adapterItens;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		inicializaItens();
	}

	private void inicializaItens() {
		if (adapterItens == null) {
			adapterItens = new InteractiveArrayAdapter(this,
				getItens());
			
		}
		setListAdapter(adapterItens);
	}
	

	public List<Letra> getItens() {
		List<Letra> listaItens = povoaItens();
		for (int i = 0; i < 4; i++) {
			listaItens.get(i).setSelecioada(true);
		}
		return listaItens;
	}
	
	public List<Letra> povoaItens() {
		List<Letra> itens = new ArrayList<Letra>();
		itens.add(new Letra("Nome", R.drawable.name));
		itens.add(new Letra("Objeto", R.drawable.objeto));
		itens.add(new Letra("Animal", R.drawable.animal));
		itens.add(new Letra("Fruta", R.drawable.fruta));
		itens.add(new Letra("Profissão", R.drawable.profissao));
		itens.add(new Letra("Carro", R.drawable.carro));
		itens.add(new Letra("País", R.drawable.pais));
		itens.add(new Letra("Cidade", R.drawable.cidade));
		itens.add(new Letra("Serie", R.drawable.serie));
		itens.add(new Letra("Novela", R.drawable.novela));
		itens.add(new Letra("Filme", R.drawable.filme));
		itens.add(new Letra("Ator", R.drawable.ator));
		itens.add(new Letra("Atriz", R.drawable.atriz));
		itens.add(new Letra("Cor", R.drawable.cor));
		itens.add(new Letra("Jogo", R.drawable.jogo));
		itens.add(new Letra("Time", R.drawable.time));
		itens.add(new Letra("Jogador", R.drawable.jogador));
		itens.add(new Letra("Cantor", R.drawable.cantor));
		itens.add(new Letra("Cantora", R.drawable.cantora));
	
		return itens;
	}
	
}
