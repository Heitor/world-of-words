package br.ufcg.les.wow.adedonha.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	private Spinner spinnerTempo;
	
	private List<Letra> letrasDesejadas;
	private List<Letra> itensDesejados;
	private Long tempoDesejado = 0L;
	
	private static int totalChamadasTempo;
	private static final String GUEST = "Gue";
	private static final String VAZIO = "";
	protected static final String ESCOLHA = "Nível escolhido: ";
	protected static final String ESCOLHA_TEMPO = "Tempo escolhido: ";
	private static final String AVISO_ITEM = "Nenhum item foi selecionado!";
	
	private ArrayAdapter<Letra> letras;
	private ArrayAdapter<Letra> itens;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		itensDefault();
		setContentView(R.layout.sub_page_jogar_adedonha);
		totalChamadasTempo = 0;
		
		editText = (EditText) findViewById(R.id.edittext_adedonha);
		
		botaoOkAction();
		botaoLimparAction();
		botaoCancelarAction();
		spinnerAction();
		configurarLetraAction();
		configurarItemAction();
		listaLetrasDesejadasOnCreate();
		
	}
	
	private void configurarItemAction() {
		Button botaoConfigurarItem = (Button) findViewById(R.id.botao_itens_adedonha);
		botaoConfigurarItem.setOnClickListener(botaoConfigurarItensListener());
		
	}

	private OnClickListener botaoConfigurarItensListener() {
		return new OnClickListener() {

			public void onClick(View v) {

				Intent subMenuIntent = new Intent(SubMenuJogarAdedonhaActivity.this,
						ListaItemActivity.class);
				
				startActivity(subMenuIntent);
			}
		};
	}
	
	private void itensDefault() {
		itensDesejados = new ArrayList<Letra>();
		itensDesejados.add(new Letra("Nome"));
		itensDesejados.add(new Letra("Objeto"));
		itensDesejados.add(new Letra("Animal"));
		itensDesejados.add(new Letra("Fruta"));
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (ListaLetraActivity.adapter != null) {
			setLetras(ListaLetraActivity.adapter);
			listaLetrasDesejadasOnRestart();
		}
		
		if (ListaItemActivity.adapterItens != null) {
			itensDesejados = new ArrayList<Letra>();
			setItens(ListaItemActivity.adapterItens);
			listaItensDesejados();
		}
	}
	
	private void listaItensDesejados() {
		for (int i = 0; i < itens.getCount(); i++) {
			if (itens.getItem(i).isSelecionada()) {
				itensDesejados.add(itens.getItem(i));
			}
		}
		
	}

	private void listaLetrasDesejadasOnRestart() {
		letrasDesejadas = new ArrayList<Letra>();
		for (int i = 0; i < letras.getCount(); i++) {
			if (letras.getItem(i).isSelecionada())
				letrasDesejadas.add(letras.getItem(i));
		}
	}
	
		
	private void listaLetrasDesejadasOnCreate() {
		letrasDesejadas = new ArrayList<Letra>();
		if (letras == null) {
			povoaLetras();
		
		} else {
			for (int i = 0; i < letras.getCount(); i++) {
				if (letras.getItem(i).isSelecionada())
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
		carregaSpinnerTempo();
		
	}

	private void carregaSpinnerTempo() {
		spinnerTempo = (Spinner) findViewById(R.id.spinner_tempo_adedonha);
		ArrayAdapter<?> adapterNiveis = criaAdapterOpcoes(R.array.tempo);
		
		spinnerTempo.setAdapter(adapterNiveis);
		spinnerTempo.setOnItemSelectedListener(spinnerTempoListener());
		
	}

	private OnItemSelectedListener spinnerTempoListener() {
		return new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View arg1,
					int position, long id) {
				
				String tempoDesejado = configuraTempo(parent, position);
				
				if (totalChamadasTempo > 0) {
					Toast.makeText(parent.getContext(), ESCOLHA +
							tempoDesejado, Toast.LENGTH_SHORT).show();
				}
				
				totalChamadasTempo++;
			}

			private String configuraTempo(AdapterView<?> parent, int position) {
				String tempoDesejado = parent.getItemAtPosition(position).toString();
				if (tempoDesejado.equalsIgnoreCase("2 Minutos")) {
					setTempoDesejado(120000L);
				
				} else if (tempoDesejado.equalsIgnoreCase("3 Minutos")) {
					setTempoDesejado(180000L);
				
				} else if (tempoDesejado.equalsIgnoreCase("4 Minutos")){
					setTempoDesejado(240000L);
				
				} else if (tempoDesejado.equalsIgnoreCase("5 Minutos")) {
					setTempoDesejado(300000L);
				
				} else if (tempoDesejado.equalsIgnoreCase("8 Minutos")) {
					setTempoDesejado(480000L);
				
				} else if (tempoDesejado.equalsIgnoreCase("10 Minutos")) {
					setTempoDesejado(600000L);
				
				} else if (tempoDesejado.equalsIgnoreCase("15 Minutos")) {
					setTempoDesejado(900000L);
				
				} else {
					setTempoDesejado(1200000L);
				}
				
				return tempoDesejado;
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		
		};
	}


	private ArrayAdapter<?> criaAdapterOpcoes(int idArray) {
		ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,
				idArray, android.R.layout.simple_spinner_item);
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
				if (itensDesejados.size() > 0) {
					
					setJogador();
					Jogo jogo = new Jogo(nomeJogador, nivel, letrasDesejadas, itensDesejados);
					
					Intent subMenuIntent = new Intent(SubMenuJogarAdedonhaActivity.this,
							JogoAdedonhaActivity.class);
					
					subMenuIntent.putExtra("tempoDesejado", tempoDesejado);
					subMenuIntent.putExtra("jogo", jogo);
					startActivity(subMenuIntent);
					finish();
				
				} else {
					mostraDialogSairJogo(AVISO_ITEM, null);
				}
				
			}
		};
	}
	
	private void mostraDialogSairJogo(String msg,
			DialogInterface.OnClickListener listener) {
		AlertDialog alerta = new AlertDialog.
				Builder(SubMenuJogarAdedonhaActivity.this).create();
		alerta.setMessage(msg);
		alerta.setButton("Ok", listener);
		alerta.show();
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

	public void setLetras(ArrayAdapter<Letra> palavras) {
		this.letras = palavras;
	}

	public List<Letra> getLetrasDesejadas() {
		return letrasDesejadas;
	}

	public void setLetrasDesejadas(List<Letra> letrasDesejadas) {
		this.letrasDesejadas = letrasDesejadas;
	}

	public Long getTempoDesejado() {
		return tempoDesejado;
	}

	public void setTempoDesejado(Long tempoDesejado) {
		this.tempoDesejado = tempoDesejado;
	}

	public ArrayAdapter<Letra> getItens() {
		return itens;
	}

	public void setItens(ArrayAdapter<Letra> itens) {
		this.itens = itens;
	}

	public List<Letra> getItensDesejados() {
		return itensDesejados;
	}

	public void setItensDesejados(List<Letra> itensDesejados) {
		this.itensDesejados = itensDesejados;
	}

}
