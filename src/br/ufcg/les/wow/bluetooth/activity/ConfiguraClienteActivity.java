package br.ufcg.les.wow.bluetooth.activity;

import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.activity.AdedonhaActivity;
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
	
	private EditText caixaDeTextNomeDoJogador;
	private String nomeJogador = VAZIO;
	
	private static final int REQUISICAO_CONEXAO_DISPOSITIVO = 1;
	
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
				Intent cancelarIntent = new Intent(ConfiguraClienteActivity.this,
						AdedonhaActivity.class);
				startActivity(cancelarIntent);
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
				Intent listarDispositivos = new Intent(ConfiguraClienteActivity.this, DeviceListActivity.class);
				listarDispositivos.putExtra("nomeJogador", nomeJogador);
	            startActivityForResult(listarDispositivos, REQUISICAO_CONEXAO_DISPOSITIVO);
	            finish();
			}
		};
	}
}
