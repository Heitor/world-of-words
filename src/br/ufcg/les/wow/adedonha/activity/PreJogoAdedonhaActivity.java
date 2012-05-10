package br.ufcg.les.wow.adedonha.activity;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.model.Jogo;

public class PreJogoAdedonhaActivity extends Activity {

	private CountDownTimer contador;
	private TextView contadorTextView;
	private Jogo jogo;
	private long tempoDesejado;
	private Intent preJogoIntent;
	// FIXME concertar servidos
	//private Serializable servidor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pre_jogo_page);

		contadorTextView = (TextView) findViewById(R.id.pre_jogo_contador);

		setContador(inicializaContador());

		Intent intent = getIntent();

		jogo = (Jogo) intent.getSerializableExtra("jogo");
		//servidor = intent.getSerializableExtra("servidor");
		tempoDesejado = intent.getLongExtra("tempoDesejado", 180000);

		preJogoIntent = new Intent(PreJogoAdedonhaActivity.this, JogoAdedonhaActivity.class);

		preJogoIntent.putExtra("jogo", jogo);
		preJogoIntent.putExtra("tempoDesejado", tempoDesejado);
		//preJogoIntent.putExtra("servidor", servidor);

	}

	private CountDownTimer inicializaContador() {
		return new CountDownTimer(6000, 1000) {

			public void onTick(long tempoMiliseconds) {
				Long tempoReal = (tempoMiliseconds / 1000);
				contadorTextView.setText(String.valueOf(tempoReal));
			}

			public void onFinish() {
				contadorTextView.setTextSize(130);
				contadorTextView.setText("Start");
				startActivity(preJogoIntent);
				finish();
			}
		}.start();
	}

	public CountDownTimer getContador() {
		return contador;
	}

	public void setContador(CountDownTimer contador) {
		this.contador = contador;
	}

}
