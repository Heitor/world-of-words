package br.ufcg.les.wow.dicionario.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.WorldofWordsActivity;

public class DicionarioActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_dicionario);

		botaoJogarAction();
		botaoSairAction();
	}

	private void botaoJogarAction() {
		Button botaoJogar = (Button) findViewById(R.id.jogar_dicionario);
		botaoJogar.setOnClickListener(listenerTrocarTela(SubMenuJogarDicionarioActivity.class));
	}

	private void botaoSairAction() {
		Button botaoSair = (Button) findViewById(R.id.sair_dicionario);
		botaoSair.setOnClickListener(listenerTrocarTela(WorldofWordsActivity.class));

	}

	
	private OnClickListener listenerTrocarTela(final Class<?> telaDestino) {
		return new OnClickListener() {
			
			public void onClick(View v) {
				Intent outButton = new Intent(DicionarioActivity.this,
						telaDestino);
				startActivity(outButton);
				finish();
			}
		};
	}

}
