package br.ufcg.les.wow.adedonha.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.util.GeradorStrings;

public class JogoAdedonhaActivity extends Activity {
	
	private String tema;
	private String nomeJogador;
	
	private TextView temaTextView;
	private TextView letraTextView;
	private TextView nomeJogadorTextView;
	private Chronometer cronometro;
	private EditText resposta;
	private ImageButton botaoSair;
	
	private static final String TEMA_PARAM = "temaJogo";
	private static final String NOME_JOGADOR_PARAM = "nomeJogador";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_jogo_adedonha);
		
		carregaIntent();
		//XXX GAMBIARRA PARA O FUNCIONAMENTO DO JOGO---------------------------------
		
		/*
		 * Tema temp = carro
		 * fusion - gol - polo - kadete - vectra - ka - fusca - ferrari
		 */
		
		//escolhe a letra f
		List<String> palavras = new ArrayList<String>();
		palavras.add("fusion");
		palavras.add("fusca");palavras.add("ferrari");
		
		//TODO Fazer a busca das palavras com o tema no BD.
		
		//TODO Pegar todas as primeiras letras das palavras do tema passado.
		String letra = GeradorStrings.retornaLetra(palavras);
		
		//XXX GAMBIARRA PARA O FUNCIONAMENTO DO JOGO---------------------------------
		cronometro = (Chronometer) findViewById(R.id.cronomentro_adedonha);
		cronometro.start();
		
		nomeJogadorTextView = (TextView) findViewById(R.id.text_view_jogador);
		nomeJogadorTextView.setText("Boa sorte, " + nomeJogador);
		
		temaTextView = (TextView) findViewById(R.id.text_view_tema);
		temaTextView.setText("Tema escolhido: Carro");
		
		letraTextView = (TextView) findViewById(R.id.text_view_letra);
		letraTextView.setText("Letra selecionada: " + letra.toUpperCase());
		
		botaoSair = (ImageButton) findViewById(R.id.imageBotaoSair);
		botaoSair.setBackgroundResource(R.drawable.close);
		botaoSair.setOnClickListener(botaoSairListener());
		
		
	}

	private OnClickListener botaoSairListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				Intent botaoSairIntent = new Intent(JogoAdedonhaActivity.this, 
						AdedonhaActivity.class);
				//fimIntent.putExtra("usuario", usuario);
				startActivity(botaoSairIntent);
				finish();
				
			}
		};
	}

	private void carregaIntent() {
		Intent jogoAdedonhaIntent = getIntent();
		
		tema = jogoAdedonhaIntent.getStringExtra(TEMA_PARAM);
		nomeJogador = jogoAdedonhaIntent.getStringExtra(NOME_JOGADOR_PARAM);
	}

}
