package br.ufcg.les.wow.adedonha.activity;

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

public class SubMenuJogarAdedonhaActivity extends Activity {
	
	private EditText editText;
	private String nomeJogador = "";
	private String nivel = "";
	private Spinner spinnerTema;
	
	private static int totalChamadas;
	private static final String GUEST = "Guest";
	private static final String VAZIO = "";
	protected static final String ESCOLHA = "Nível escolhido: ";
	
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

				Intent subMenuIntent = new Intent(SubMenuJogarAdedonhaActivity.this,
						JogoAdedonhaActivity.class);
				subMenuIntent.putExtra("nomeJogador", nomeJogador);
				subMenuIntent.putExtra("nivel", nivel);
				

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

}
