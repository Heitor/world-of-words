package br.ufcg.les.wow.adedonha.activity;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.WorldofWordsActivity;
import br.ufcg.les.wow.adedonha.persistence.AdedonhaDAOImpl;
import br.ufcg.les.wow.adedonha.persistence.Palavra;
import br.ufcg.les.wow.util.GeradorStrings;

public class AdedonhaActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_adedonha);
		
		botaoJogarAction();
		botaoSairAction();
		
		AdedonhaDAOImpl adedonhaDao = new AdedonhaDAOImpl(getApplicationContext());
		try {
			adedonhaDao.open();
			if (adedonhaDao.listarObjetos().size() == 0) {
				List<Palavra> palavras = GeradorStrings.povoaBanco();
				adedonhaDao.inserirObjeto(palavras);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adedonhaDao.close();

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
