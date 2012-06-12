package br.ufcg.les.wow.adedonha.activity;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
		
		Intent usuarioIntent = getIntent();

        RankingDAO rankingDao = (RankingDAO) usuarioIntent.getSerializableExtra("rankingDao");
        loadPlayers();
        
        if (playerList.size() == 0) {
        	rankingDao.carregaRankingDefault();
        	playerList = rankingDao.getRanking();
        	try {
				inserirJogadores();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        
        } else {
        	rankingDao.carregaRanking(playerList);
        }
        
        ScrollView layout = loadInformation2();
		setContentView(layout);
		
	}
	
	private void inserirJogadores() throws SQLException {
		usuariosDAO.open();
		for (Jogador jogador : playerList) {
			usuariosDAO.inserirObjeto(jogador.getNome(), jogador.getPontuacao(), jogador.tempo());
		}
		usuariosDAO.close();
		
	}

	private View createLine() {
		View line = new View(this);
		line.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
		line.setBackgroundColor(Color.rgb(51, 51, 51));
		
		return line;
	}
	
	private ScrollView loadInformation2() {
		ScrollView layout = (ScrollView) View.inflate(this,
				R.layout.teste_table, null);
		
		TableLayout table = (TableLayout)layout.
				findViewById(R.id.teste);
		
		table.setStretchAllColumns(true);  
	    table.setShrinkAllColumns(true); 
	    table.setBaselineAligned(true);
		
		int count = 1;
		for (Jogador player : playerList) {
			TableRow rowTitle = new TableRow(this);
			rowTitle.setGravity(Gravity.CENTER_HORIZONTAL); 
			
			TextView playerTextView = createTextView(count + SEPARATOR + player.getNome());
			rowTitle.addView(playerTextView);
			
			TextView pontuation = createTextView(player.getPontuacao() + " Pontos");
			pontuation.setGravity(Gravity.RIGHT);
			
			rowTitle.addView(pontuation);
			
			View v = createLine();
			table.addView(rowTitle);
			table.addView(v);
			count++;
			if (count == 11) {
				break;
			}
		}
		
		return layout;
		
		
	}

	private TextView createTextView(String text) {
		TextView textView = new TextView(this);
		textView.setText(text);
		textView.setTextSize(20);
		textView.setTextColor(Color.parseColor("#363636"));
		return textView;
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
