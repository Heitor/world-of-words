package br.ufcg.les.wow.dicionario.activity;

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

public class SubMenuJogarDicionarioActivity extends Activity {
	
	private String nomeJogador = "";
	private String tema = "";
	
	private List<String> temas = new ArrayList<String>();
	
	private Spinner spinnerTema;
	private EditText editText;
	
	private static int totalChamadas;
	private static final String GUEST = "Guest";
	private static final String VAZIO = "";
	private static final String ESCOLHA = "Você escolheu o tema: ";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_page_jogar_dicionario);
		
		editText = (EditText) findViewById(R.id.edittext_adedonha);
		
		totalChamadas = 0;

		botaoOkAction();
		botaoLimparAction();
		botaoCancelarAction();
		spinnerAction();
	}
	
	private void spinnerAction() {
		spinnerTema = (Spinner) findViewById(R.id.spinner_tema_adedonha);
		
		//TODO CARREGAR OS TEMAS AQUI
		temas.add("TESTE - 1");
		temas.add("TESTE - 2");
		
		ArrayAdapter<String> adapterOpcoes = criaAdapterOpcoes(temas);
		spinnerTema.setAdapter(adapterOpcoes);
		
		spinnerTema.setOnItemSelectedListener(spinnerTemaListener());
		
	}

	private OnItemSelectedListener spinnerTemaListener() {
			return new OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> parent, View arg1,
						int position, long id) {
					tema = parent.getItemAtPosition(position).toString();
					
					if (totalChamadas > 0) {
						Toast.makeText(parent.getContext(), ESCOLHA +
							tema, Toast.LENGTH_SHORT).show();
					}
					totalChamadas++;
				}

				public void onNothingSelected(AdapterView<?> arg0) {
				}
			
			};
	}

	private ArrayAdapter<String> criaAdapterOpcoes(List<String> teste) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, teste);
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

				Intent subMenuIntent = new Intent(SubMenuJogarDicionarioActivity.this,
						JogoDicionarioActivity.class);
				subMenuIntent.putExtra("nomeJogador", nomeJogador);
				subMenuIntent.putExtra("temaJogo", tema);
				

				startActivity(subMenuIntent);
				finish();
			}
		};
	}

	private void botaoLimparAction() {
		Button botaoLimpar = (Button) findViewById(R.id.limpar_adedonha);
		botaoLimpar.setOnClickListener(botaoLimparListener());

	}

	private void setJogador() {
		if (nomeInvalido()) {
			setNomeUsuario(GUEST + getRandon());
		} else {
			setNomeUsuario(editText.getText().toString());
		}
	}

	private OnClickListener botaoLimparListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				editText.setText(VAZIO);
				setNomeUsuario(VAZIO);
			}
		};
	}

	private void botaoCancelarAction() {
		Button botaoCancelar = (Button) findViewById(R.id.cancelar_adedonha);
		botaoCancelar.setOnClickListener(botaoCancelarListener());

	}

	private OnClickListener botaoCancelarListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent cancelarIntent = new Intent(SubMenuJogarDicionarioActivity.this,
						DicionarioActivity.class);
				startActivity(cancelarIntent);
				finish();
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

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeJogador = nomeUsuario;
	}

	public String getNomeUsuario() {
		return nomeJogador;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}


}
