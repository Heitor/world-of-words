package br.ufcg.les.wow.adedonha.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.WorldofWordsActivity;

public class AdedonhaActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_adedonha);
		
		botaoJogarAction();
		botaoSairAction();
	}

	private void botaoJogarAction() {
		Button botaoJogar = (Button) findViewById(R.id.jogar_adedonha);
		botaoJogar.setOnClickListener(listenerTrocarTela(SubMenuJogarAdedonhaActivity.class));
		
	}

	private void botaoSairAction() {
		Button botaoSair = (Button) findViewById(R.id.sair_adedonha);
		botaoSair.setOnClickListener(listenerTrocarTela(WorldofWordsActivity.class));

	}

	
	private OnClickListener listenerTrocarTela(final Class<?> telaDestino) {
		return new OnClickListener() {
			
			public void onClick(View v) {
				Intent outButton = new Intent(AdedonhaActivity.this,
						telaDestino);
				startActivity(outButton);
				finish();
			}
		};
	}

}
