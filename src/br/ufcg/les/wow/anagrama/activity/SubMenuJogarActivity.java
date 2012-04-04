package br.ufcg.les.wow.anagrama.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.anagrama.enummeration.Nivel;

public class SubMenuJogarActivity extends Activity {
	
	private String nomeJogador = "";
	private EditText editText;
	
	private static final String GUEST = "Guest";
	private static final String VAZIO = "";
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.sub_page_jogar_anagrama);
	        
	        Intent nivelIntent = getIntent();
	        Nivel nivel = (Nivel) nivelIntent.getSerializableExtra("nivel");
	        
	        editText = (EditText) findViewById(R.id.edittext);
	        
	        botaoOkAction(nivel);
	        botaoLimparAction();
	        botaoCancelarAction();
	        
	    }

	private void botaoOkAction(Nivel nivel) {
		Button botaoOk = (Button) findViewById(R.id.confirmar);
		botaoOk.setOnClickListener(botaoOkListener(nivel));
	}

	private OnClickListener botaoOkListener(final Nivel nivel) {
		return new OnClickListener() {

			public void onClick(View v) {
				setJogador();
				
				Intent okIntent = new Intent(SubMenuJogarActivity.this,
						JogoActivity.class);
				okIntent.putExtra("nomeJogador", nomeJogador);
				okIntent.putExtra("nivel", nivel);
				
				startActivity(okIntent);
				finish();
			}
		};
	}
	

	private void botaoLimparAction() {
		Button botaoLimpar = (Button) findViewById(R.id.limpar);
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
		Button botaoCancelar = (Button) findViewById(R.id.cancelar);
		botaoCancelar.setOnClickListener(botaoCancelarListener());
		
	}

	private OnClickListener botaoCancelarListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent fimIntent = new Intent(SubMenuJogarActivity.this,
						AnagramaHTActivity.class);
				startActivity(fimIntent);
				finish();
			}
		};
	}
	
	private boolean nomeInvalido() {
		return editText.getText().toString().trim().equals(VAZIO);
	}
	
	private String getRandon() {
		int aleatorio = (int) (1+Math.random()*100); 
		return String.valueOf(aleatorio);
	}


	public void setNomeUsuario(String nomeUsuario) {
		this.nomeJogador = nomeUsuario;
	}

	public String getNomeUsuario() {
		return nomeJogador;
	}
	
}
