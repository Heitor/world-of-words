package br.ufcg.les.wow.bluetooth.activity;

import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.activity.SubMenuJogarAdedonhaActivity;
import br.ufcg.les.wow.bluetooth.ManipuladorProtocolo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ConfiguracoesDoJogoActivity extends Activity {
	private final static String TAG = "[ConfiguracoesDoJogoActivity]";
	private final ManipuladorProtocolo handle = ManipuladorProtocolo.instance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracoes_do_jogo);
		Log.d(TAG, "Inciando configuração de novo jogo..");
		
		botaoCriaServidorAction();
		botaoCriaClienteAction();
	}
	
	private void botaoCriaServidorAction() {
		Button botaoDicionario = (Button) findViewById(R.id.button_servidor);
		botaoDicionario.setOnClickListener(botaoCriaServidorListener());
	}
	
	private void botaoCriaClienteAction() {
		Button botaoDicionario = (Button) findViewById(R.id.button_cliente);
		botaoDicionario.setOnClickListener(botaoCriaClienteListener());
	}
	
	private OnClickListener botaoCriaServidorListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				Intent configuracoesDaPartidaIntent = new Intent(ConfiguracoesDoJogoActivity.this,
						SubMenuJogarAdedonhaActivity.class);
				configuracoesDaPartidaIntent.putExtra("protocolo", handle);
				startActivity(configuracoesDaPartidaIntent);
				finish();
			}
		};
	}
	
	private OnClickListener botaoCriaClienteListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent configuracoesDaPartidaIntent = new Intent(ConfiguracoesDoJogoActivity.this,
						ConfiguraClienteActivity.class);
				startActivity(configuracoesDaPartidaIntent);
				finish();
			}
		};
	}
}
