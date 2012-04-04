package br.ufcg.les.wow.anagrama.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.anagrama.model.Usuario;
import br.ufcg.les.wow.anagrama.persistence.dao.RankingDAO;
import br.ufcg.les.wow.anagrama.persistence.dao.UsuarioDAO;

public class RankingActivity extends Activity {
	
	UsuarioDAO usuariosDAO = new UsuarioDAO(this);
	private List<Usuario> listaUsuarios;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_ranking_anagrama);
		
		Intent usuarioIntent = getIntent();

        RankingDAO rankingDao = (RankingDAO) usuarioIntent.getSerializableExtra("rankingDao");
        
        //listaUsuarios = rankingDao.getRanking();
		
        carregarUsuarios();
        rankingDao.carregaRanking(listaUsuarios);
		carregaRanking();
		
		Button botaoVoltar = (Button) findViewById(R.id.voltarRanking);
		botaoVoltar.setOnClickListener(botaoVoltarListener());
		
		//carregarUsuarios();
	}
	
	private void carregarUsuarios() {
		try {
			usuariosDAO.open();
			listaUsuarios = usuariosDAO.listarObjetos();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		usuariosDAO.close();
	}

	private void carregaRanking() {
		int totalUsuariosNoRanking = listaUsuarios.size();
		List<TextView> textViewRankingList = new ArrayList<TextView>();
		
		TextView primeiroTextView = (TextView) findViewById(R.id.primeiro);
		textViewRankingList.add(primeiroTextView);
		
		TextView segundoTextView = (TextView) findViewById(R.id.segundo);
		textViewRankingList.add(segundoTextView);

		TextView terceiroTextView = (TextView) findViewById(R.id.terceiro);
		textViewRankingList.add(terceiroTextView);

		TextView quartoTextView = (TextView) findViewById(R.id.quarto);
		textViewRankingList.add(quartoTextView);

		TextView quintoTextView = (TextView) findViewById(R.id.quinto);
		textViewRankingList.add(quintoTextView);
		
		Iterator<TextView> itTextView = textViewRankingList.iterator();
		
		int count = 0;
		while(itTextView.hasNext() && count < totalUsuariosNoRanking) {
			itTextView.next().setText(count+1 + " - "
					+ listaUsuarios.get(count).toString());
			count++;
		}
	}

	private OnClickListener botaoVoltarListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				mudaContexto();
			}

			private void mudaContexto() {
				Intent okIntent = new Intent(RankingActivity.this,
						AnagramaHTActivity.class);
				startActivity(okIntent);
				finish();
			}
		};
	}

}
