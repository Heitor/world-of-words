package br.ufcg.les.wow.adedonha.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class JogoAdedonhaActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String nomeJogador = intent.getStringExtra("nomeJogador");
		String nivel = intent.getStringExtra("nivel");
		
		System.out.println(nomeJogador);
		System.out.println(nivel);
	}

}
