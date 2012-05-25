package br.ufcg.les.wow.adedonha.activity;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.model.Jogador;
import br.ufcg.les.wow.persistence.dao.RankingDAO;
import br.ufcg.les.wow.persistence.dao.UsuarioDAO;

public class RankingActivity extends Activity {
	
	private UsuarioDAO usuariosDAO = new UsuarioDAO(this);
	private List<Jogador> playerList;
	
	private static final String SEPARATOR = " - ";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_ranking_anagrama);
		
		Intent usuarioIntent = getIntent();

        RankingDAO rankingDao = (RankingDAO) usuarioIntent.getSerializableExtra("rankingDao");
        loadPlayers();
        
        if (playerList.size() == 0) {
        	rankingDao.carregaRankingDefault();
        	playerList = rankingDao.getRanking();
        
        } else {
        	rankingDao.carregaRanking(playerList);
        }
        
        ScrollView layout = loadInformation();
		setContentView(layout);
		
	}
	
	private ScrollView loadInformation() {
		ScrollView layout = (ScrollView) View.inflate(this,
				R.layout.page_ranking_adedonha, null);
		
		LinearLayout vTblRow = (LinearLayout)layout.
				findViewById(R.id.group_ranking_adedonha);
		
		int count = 1;
		for (Jogador player : playerList) {
			TextView playerTextView = new TextView(this);
			playerTextView.setText(count + SEPARATOR + player.toString());
			playerTextView.setTextSize(20);
			playerTextView.setTextColor(Color.parseColor("#363636"));
			vTblRow.addView(playerTextView);
			count++;
		}
		
		return layout;
	}

	private void loadPlayers() {
		try {
			usuariosDAO.open();
			playerList = usuariosDAO.listarObjetos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		usuariosDAO.close();
	}

}
