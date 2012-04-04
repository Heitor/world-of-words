package br.ufcg.les.wow.anagrama.activity;

import br.ufcg.les.wow.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AjudaActivity extends Activity {
	private final static String TEXTO_JOGAR = "Jogar: No menu jogar voc� iniciar� o " +
			"jogo ap�s inserir seu nome. Ser� apresentada uma palavra embaralhada e o " +
			"jogador ter� que entrar com os anagramas dessa palavra. Cada palavra encontrada " +
			"vale 50 pontos e ser� listada na tela. Ao tentar enviar uma palavra j� encontrada " +
			"40 pontos ser�o diminu�dos do total. Caso o jogador erre uma palavra, " +
			"ser� descontado 20 pontos do total.";
	
	private final static String TEXTO_RANKING = "Ranking: No menu ranking � poss�vel ver " +
			"a listagem dos 5 jogadores com melhor pontua��o.";
	
	private final static String TEXTO_OPCOES = "Op��es: No menu op��es � poss�vel escolher " +
			"o nn�vel dos anagramas, que pode ser f�cil, m�dio ou dif�cil.";
	
	
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
