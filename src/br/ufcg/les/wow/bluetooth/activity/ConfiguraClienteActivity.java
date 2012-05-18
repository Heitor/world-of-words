package br.ufcg.les.wow.bluetooth.activity;

import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.model.Jogador;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConfiguraClienteActivity extends Activity {
	private final static String TAG = "[ConfiguraClienteActivity]";
	private static final String VAZIO = "";
	private static final String GUEST = "Gue";
	
	private EditText caixaDeTextNomeDoJogador;
	private String nomeJogador = VAZIO;
	
	private static final int REQUISICAO_CONEXAO_DISPOSITIVO = 1;
	protected static final int REQUEST_ENABLE_BT = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracoes_jogo_cliente);
		Log.d(TAG, "Inciando configuração de novo jogo..");
		
		this.caixaDeTextNomeDoJogador = (EditText) findViewById(R.id.caixadetexto_opcoes_cliente);
		
		botaoLimparAction();
		botaoCancelarAction();
		botaoOkAction();
	}
	
	private void botaoLimparAction() {
		Button botaoLimpar = (Button) findViewById(R.id.botao_limpar_opcoes_cliente);
		botaoLimpar.setOnClickListener(botaoLimparListener());
	}
	
	private OnClickListener botaoLimparListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				caixaDeTextNomeDoJogador.setText(VAZIO);
				nomeJogador = VAZIO;
			}
		};
	}
	
	private void botaoCancelarAction() {
		Button botaoCancelar = (Button) findViewById(R.id.botao_cancelar_opcoes_cliente);
		botaoCancelar.setOnClickListener(botaoCancelarListener());

	}
	
	private OnClickListener botaoCancelarListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		};
	}
	
	private void botaoOkAction() {
		Button botaoOk = (Button) findViewById(R.id.botao_confirmar_opcoes_cliente);
		botaoOk.setOnClickListener(botaoOkListener());
	}
	
	private OnClickListener botaoOkListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				setJogador();
				Jogador jogador = new Jogador(nomeJogador);
				Intent listarDispositivos = new Intent(ConfiguraClienteActivity.this, DeviceListActivity.class);
				//listarDispositivos.putExtra("nomeJogador", nomeJogador);
				listarDispositivos.putExtra(Jogador.JOGADOR, jogador);
				startActivityForResult(listarDispositivos, REQUISICAO_CONEXAO_DISPOSITIVO);
				finish();
			}
		};
	}
	
	private void setJogador() {
		if (nomeInvalido()) {
			setNomeJogador(GUEST + getRandon());
		} else {
			setNomeJogador(this.caixaDeTextNomeDoJogador.getText().toString());
		}
	}
	
	private boolean nomeInvalido() {
		return this.caixaDeTextNomeDoJogador.getText().toString().trim().equals(VAZIO);
	}
	
	private String getRandon() {
		int aleatorio = (int) (1 + Math.random() * 100);
		return String.valueOf(aleatorio);
	}
	
	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}
}
