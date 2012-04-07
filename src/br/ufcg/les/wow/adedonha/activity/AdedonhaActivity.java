package br.ufcg.les.wow.adedonha.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.WorldofWordsActivity;
import br.ufcg.les.wow.adedonha.activity.util.ActionUtil;

public class AdedonhaActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_adedonha);

		botaoSairAction();
	}

	private void botaoSairAction() {
		Button botaoSair = (Button) findViewById(R.id.sair);
		botaoSair.setOnClickListener(botaoSairListener());

	}

	private OnClickListener botaoSairListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				ActionUtil util = new ActionUtil();
				util.sairDoJogo(AdedonhaActivity.this, WorldofWordsActivity.class);
//				Intent outButton = new Intent(AdedonhaActivity.this,
//						WorldofWordsActivity.class);
//				startActivity(outButton);
//				finish();
			}
		};
	}

}
