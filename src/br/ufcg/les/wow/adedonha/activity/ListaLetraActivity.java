package br.ufcg.les.wow.adedonha.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import br.ufcg.les.wow.adedonha.model.Letra;
import br.ufcg.les.wow.util.InteractiveArrayAdapter;

public class ListaLetraActivity extends ListActivity {

	public static ArrayAdapter<Letra> adapter;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (adapter == null) {
			adapter = new InteractiveArrayAdapter(this,
				getLetra());
			
		}
		setListAdapter(adapter);
	}
	
	private List<Letra> povoaLetras() {
		List<Letra> letras = new ArrayList<Letra>();

		for (int i = 65; i < 91; i++) {
			char a = (char) i;
			letras.add(get(String.valueOf(a)));
		}
		return letras;
	}

	private List<Letra> getLetra() {
		List<Letra> listaLetras = povoaLetras();
		for (Letra letra : listaLetras) {
			letra.setSelecioada(true);
		}
		return listaLetras;
	}

	private Letra get(String s) {
		return new Letra(s);
	}

}