package br.ufcg.les.wow.adedonha.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.model.Letra;
import br.ufcg.les.wow.anagrama.model.Jogo;

public class SubMenuJogarAdedonhaActivity extends Activity {
	
	private EditText editText;
	private String nomeJogador = "";
	private String nivel = "";
	private Spinner spinnerTema;
	private List<Letra> letrasDesejadas;
	
	private static int totalChamadas;
	private static final String GUEST = "Gue";
	private static final String VAZIO = "";
	protected static final String ESCOLHA = "Nível escolhido: ";
	
	private ArrayAdapter<Letra> letras;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_page_jogar_adedonha);
		totalChamadas = 0;
		
		editText = (EditText) findViewById(R.id.edittext_adedonha);
		
		botaoOkAction();
		botaoLimparAction();
		botaoCancelarAction();
		spinnerAction();
		configurarLetraAction();
		listaLetrasDesejadasOnCreate();
		
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		setPalavras(ListaLetraActivity.adapter);
		listaLetrasDesejadasOnRestart();
	}
	
	private void listaLetrasDesejadasOnRestart() {
		for (int i = 0; i < letras.getCount(); i++) {
			if (letras.getItem(i).isSelecioada())
				letrasDesejadas.add(letras.getItem(i));
		}
	}
	
		
	private void listaLetrasDesejadasOnCreate() {
		letrasDesejadas = new ArrayList<Letra>();
		if (letras == null) {
			povoaLetras();
		
		} else {
			for (int i = 0; i < letras.getCount(); i++) {
				if (letras.getItem(i).isSelecioada())
					letrasDesejadas.add(letras.getItem(i));
			}
		}
	}
	
	private void povoaLetras() {
		letrasDesejadas = new ArrayList<Letra>();

		for (int i = 65; i < 91; i++) {
			char a = (char) i;
			letrasDesejadas.add(new Letra(String.valueOf(a)));
		}
	}

	private void configurarLetraAction() {
		Button botaoConfigurarLetra = (Button) findViewById(R.id.botao_configurar_adedonha);
		botaoConfigurarLetra.setOnClickListener(botaoConfigurarListener());
		
	}
	
	
	private OnClickListener botaoConfigurarListener() {
		return new OnClickListener() {

			public void onClick(View v) {

				Intent subMenuIntent = new Intent(SubMenuJogarAdedonhaActivity.this,
						ListaLetraActivity.class);
				
				startActivity(subMenuIntent);
			}
		};
	}

	private void spinnerAction() {
		spinnerTema = (Spinner) findViewById(R.id.spinner_nivel_adedonha);
		
		ArrayAdapter<?> adapterNiveis = criaAdapterOpcoes();
		spinnerTema.setAdapter(adapterNiveis);
		
		spinnerTema.setOnItemSelectedListener(spinnerTemaListener());
		
	}

	private OnItemSelectedListener spinnerTemaListener() {
			return new OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> parent, View arg1,
						int position, long id) {
					
					nivel = parent.getItemAtPosition(position).toString();
					
					if (totalChamadas > 0) {
						Toast.makeText(parent.getContext(), ESCOLHA +
								nivel, Toast.LENGTH_SHORT).show();
					}
					totalChamadas++;
				}

				public void onNothingSelected(AdapterView<?> arg0) {
				}
			
			};
	}

	private ArrayAdapter<?> criaAdapterOpcoes() {
		ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,
				R.array.nivel, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		return adapter;
	}

	private void botaoOkAction() {
		Button botaoOk = (Button) findViewById(R.id.botao_confirmar_adedonha);
		botaoOk.setOnClickListener(botaoConfirmarListener());
	}

	private OnClickListener botaoConfirmarListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				setJogador();
				Jogo jogo = new Jogo(nomeJogador, nivel, letrasDesejadas);

				Intent subMenuIntent = new Intent(SubMenuJogarAdedonhaActivity.this,
						JogoAdedonhaActivity.class);
				
				subMenuIntent.putExtra("jogo", jogo);
				startActivity(subMenuIntent);
				finish();
			}
		};
	}

	private void botaoLimparAction() {
		Button botaoLimpar = (Button) findViewById(R.id.limpar_adedonha);
		botaoLimpar.setOnClickListener(botaoLimparListener());

	}
	
	private void botaoCancelarAction() {
		Button botaoCancelar = (Button) findViewById(R.id.cancelar_adedonha);
		botaoCancelar.setOnClickListener(botaoCancelarListener());

	}

	private OnClickListener botaoCancelarListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent cancelarIntent = new Intent(SubMenuJogarAdedonhaActivity.this,
						AdedonhaActivity.class);
				startActivity(cancelarIntent);
				finish();
			}
		};
	}
	
	
	private void setJogador() {
		if (nomeInvalido()) {
			setNomeJogador(GUEST + getRandon());
		} else {
			setNomeJogador(editText.getText().toString());
		}
	}

	private OnClickListener botaoLimparListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				editText.setText(VAZIO);
				setNomeJogador(VAZIO);
			}
		};
	}
	
	private boolean nomeInvalido() {
		return editText.getText().toString().trim().equals(VAZIO);
	}
	
	private String getRandon() {
		int aleatorio = (int) (1 + Math.random() * 100);
		return String.valueOf(aleatorio);
	}


	public String getNomeJogador() {
		return nomeJogador;
	}


	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}
	
	public EditText getEditText() {
		return editText;
	}

	public void setEditText(EditText editText) {
		this.editText = editText;
	}

	public ArrayAdapter<Letra> getPalavras() {
		return letras;
	}

	public void setPalavras(ArrayAdapter<Letra> palavras) {
		this.letras = palavras;
	}

	public List<Letra> getLetrasDesejadas() {
		return letrasDesejadas;
	}

	public void setLetrasDesejadas(List<Letra> letrasDesejadas) {
		this.letrasDesejadas = letrasDesejadas;
	}

}
