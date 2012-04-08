package br.ufcg.les.wow.adedonha.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.ufcg.les.wow.R;

public class SubMenuJogarAdedonhaActivity extends Activity {
	
	private String nomeJogador = "";
	private EditText editText;
	
	private static final String GUEST = "Guest";
	private static final String VAZIO = "";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_page_jogar_adedonha);
		
		editText = (EditText) findViewById(R.id.edittext_adedonha);

		botaoOkAction();
		botaoLimparAction();
		botaoCancelarAction();
	}
	
	private void botaoOkAction() {
		Button botaoOk = (Button) findViewById(R.id.confirmar_adedonha);
		//botaoOk.setOnClickListener(botaoConfirmarListener);
	}

//	private OnClickListener botaoConfirmarListener() {
//		return new OnClickListener() {
//
//			public void onClick(View v) {
//				setJogador();
//
//				Intent okIntent = new Intent(SubMenuJogarActivity.this,
//						JogoActivity.class);
//				okIntent.putExtra("nomeJogador", nomeJogador);
//
//				startActivity(okIntent);
//				finish();
//			}
//		};
//	}

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
				Intent cancelarIntent = new Intent(SubMenuJogarAdedonhaActivity.this,
						AdedonhaActivity.class);
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


}
