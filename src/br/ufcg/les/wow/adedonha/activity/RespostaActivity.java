package br.ufcg.les.wow.adedonha.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.persistence.User;

public class RespostaActivity extends Activity {
	
	private int nivel;
	private int pontos;
	private String tempoString = "";
	
	private static final int ACERTO = 20;
	private static final int ERRO = 5; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_conferir_adedonha);
		
		Intent intent = getIntent();
		
		@SuppressWarnings("unchecked")
		ArrayList<String> respostas = (ArrayList<String>) intent.
				getSerializableExtra("respostas");
		
		User jogador = (User) intent.getSerializableExtra("jogador");
		tempoString = intent.getStringExtra("tempoString");
		
		apresentaPalavras(respostas);
		apresentaDadosJogador(jogador);
		
		
		
	}

	private void apresentaDadosJogador(User jogador) {
		//TODO Contabilizar o tempo
		jogador.setPointing(pontos);
		
		TextView nomeText = (TextView) findViewById(R.id.row9_jogador_adedonha);
		nomeText.setText("Jogador: " + jogador.getUserName());
		
		TextView tempoText = (TextView) findViewById(R.id.row9_tempo_adedonha);
		tempoText.setText("Tempo: " + tempoString);
		
		TextView pontuacao = (TextView) findViewById(R.id.row9_pontuacao_adedonha);
		pontuacao.setText("Pontuação: " + jogador.getPointing());
		
	}

	private void apresentaPalavras(ArrayList<String> respostas) {
		nivel = respostas.size();
		
		apresentaPalavraNome(respostas.get(0));
		apresentaPalavraObjeto(respostas.get(1));
		apresentaPalavraAnimal(respostas.get(2));
		apresentaPalavraFruta(respostas.get(3));
		
		if (nivel > 4) {
			apresentaPalavraProfissao(respostas.get(4));
			apresentaPalavraCarro(respostas.get(5));
			
			if (nivel > 6) {
				apresentaPalavraCidade(respostas.get(6));
				apresentaPalavraSerie(respostas.get(7));
			}
			
		}
	}

	private void apresentaPalavraSerie(String palavra) {
		verificaPalavra(palavra, R.id.row8_imagem_adedonha,
				R.id.row8_palavra1_col2_adedonha, R.id.row8_imagem2_adedonha);
		
	}

	private void apresentaPalavraCidade(String palavra) {
		verificaPalavra(palavra, R.id.row7_imagem_adedonha,
				R.id.row7_palavra1_col2_adedonha, R.id.row7_imagem2_adedonha);
		
	}

	private void apresentaPalavraCarro(String palavra) {
		verificaPalavra(palavra, R.id.row6_imagem_adedonha,
				R.id.row6_palavra1_col2_adedonha, R.id.row6_imagem2_adedonha);
		
	}

	private void apresentaPalavraProfissao(String palavra) {
		verificaPalavra(palavra, R.id.row5_imagem_adedonha,
				R.id.row5_palavra1_col2_adedonha, R.id.row5_imagem2_adedonha);
		
	}

	private void apresentaPalavraFruta(String palavra) {
		verificaPalavra(palavra, R.id.row4_imagem_adedonha,
				R.id.row4_palavra1_col2_adedonha, R.id.row4_imagem2_adedonha);
		
	}

	private void apresentaPalavraAnimal(String palavra) {
		verificaPalavra(palavra, R.id.row3_imagem_adedonha,
				R.id.row3_palavra1_col2_adedonha, R.id.row3_imagem2_adedonha);
		
	}

	private void apresentaPalavraObjeto(String palavra) {
		verificaPalavra(palavra, R.id.row2_imagem_adedonha,
				R.id.row2_palavra1_col2_adedonha, R.id.row2_imagem2_adedonha);
	}

	private void apresentaPalavraNome(String palavra) {
		verificaPalavra(palavra, R.id.row_imagem1_adedonha,
				R.id.row_palavra1_col2_adedonha, R.id.row_imagem2_adedonha);
	}

	private void verificaPalavra(String palavra, int rowImagem1Adedonha,
			int rowPalavra1Col2Adedonha, int rowImageButton) {
		TextView text = (TextView) findViewById(rowPalavra1Col2Adedonha);
		ImageView imagem = (ImageView) findViewById(rowImagem1Adedonha);
		ImageButton sugestao = (ImageButton) findViewById(rowImageButton);
		
		if (verificaPalavra(palavra)) {
			pontos += ACERTO;
			imagem.setImageResource(R.drawable.icone_ok_v2);
		
		} else {
			pontos -= ERRO;
			imagem.setImageResource(R.drawable.icone_not_ok);
			sugestao.setVisibility(ImageButton.VISIBLE);
			sugestao.setBackgroundResource(R.drawable.sugest);
			//sugestao.setOnClickListener(listener);
		}
		
		text.setText(palavra);
		
	}

	private boolean verificaPalavra(String palavra) {
		// TODO buscar no bd
		return true;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

}
