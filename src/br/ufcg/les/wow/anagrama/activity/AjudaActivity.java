package br.ufcg.les.wow.anagrama.activity;

import br.ufcg.les.wow.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AjudaActivity extends Activity {
	private final static String TEXTO_JOGAR = "Jogar: No menu jogar você iniciará o " +
			"jogo após inserir seu nome. Será apresentada uma palavra embaralhada e o " +
			"jogador terá que entrar com os anagramas dessa palavra. Cada palavra encontrada " +
			"vale 50 pontos e será listada na tela. Ao tentar enviar uma palavra já encontrada " +
			"40 pontos serão diminuídos do total. Caso o jogador erre uma palavra, " +
			"será descontado 20 pontos do total.";
	
	private final static String TEXTO_RANKING = "Ranking: No menu ranking é possível ver " +
			"a listagem dos 5 jogadores com melhor pontuação.";
	
	private final static String TEXTO_OPCOES = "Opções: No menu opções é possível escolher " +
			"o nnível dos anagramas, que pode ser fácil, médio ou difícil.";
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.page_ajuda_anagrama);
	        
	        TextView textViewJogar = (TextView) findViewById(R.id.jogar);
	        textViewJogar.setText(TEXTO_JOGAR);
			
	        TextView textViewRanking = (TextView) findViewById(R.id.rankingText);
	        textViewRanking.setText(TEXTO_RANKING);
			
			TextView ajudaTextView = (TextView) findViewById(R.id.ajuda);
			ajudaTextView.setText(TEXTO_OPCOES);
			
			Button botaoVoltarAjuda = (Button) findViewById(R.id.voltarAjuda);
			botaoVoltarAjuda.setOnClickListener(botaoVoltarAjudaListener());
	    }


	private OnClickListener botaoVoltarAjudaListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		};
	}

}
