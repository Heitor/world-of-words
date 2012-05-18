package br.ufcg.les.wow.adedonha.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.model.ConfiguracaoParatida;
import br.ufcg.les.wow.adedonha.model.Jogador;

public class RespostaActivity extends Activity {
	
	private ConfiguracaoParatida configuracao;
	private Jogador jogador;
//	private int pontos;
//	private Long tempoRestante = 0L;
//	
//	private String letraJogo = "";
//	private HashMap<String, String> respostas;
	
	@SuppressWarnings({ "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_conferir_adedonha);
		
		Intent intent = getIntent();
		
		this.configuracao = (ConfiguracaoParatida) intent.getSerializableExtra(ConfiguracaoParatida.CONFIGURACAO);
		this.jogador = (Jogador) intent.getSerializableExtra(Jogador.JOGADOR);
		
		//setLetraJogo(intent.getStringExtra("letraJogo"));
		
		//respostas = (HashMap<String, String>) intent.getSerializableExtra("respostas");
		
		ScrollView layout = carregaRespostas();
		
		//System.out.println("Tamanho do mapa nas respostas = "  +respostas.size());
		
		//Jogador jogador = (Jogador) intent.getSerializableExtra("jogador");
		//tempoRestante = intent.getLongExtra("tempoJogo", 0L);
		
		//apresentaPalavras(respostas);
		
		setContentView(layout);
		apresentaDadosJogador();
		inicializaBotaoVerificado();
	}
	
	
	private ScrollView carregaRespostas() {
		
		ScrollView layout = (ScrollView) View.inflate(this,
				R.layout.page_conferir_adedonha, null);
		
		LinearLayout vTblRow = (LinearLayout)layout.
				findViewById(R.id.group_respostas_adedonha);
		
		List<String> itensList = new ArrayList<String>();
		//Set<String> itens = respostas.keySet();
		Set<String> itens = jogador.resultado().keySet();
		for (String iten : itens) {
			itensList.add(iten);
		}
		
		List<String> valoresList = new ArrayList<String>();
		Collection<String> valores = (Collection<String>) jogador.resultado().values();
		for (String valor : valores) {
			valoresList.add(valor);
		}

		for (int i = 0; i < jogador.resultado().size(); i++) {
			TextView resultadoTextView = new TextView(this);
			resultadoTextView.setText(itensList.get(i) + ": " + valoresList.get(i));
			resultadoTextView.setTextAppearance(getApplicationContext(), R.style.negrito);
			
			vTblRow.addView(resultadoTextView);
		}
		
		
		return layout;
	}
	
	

	private void inicializaBotaoVerificado() {
		ImageButton botaoVerificado = (ImageButton) findViewById(R.id.botao_verificado_adedonha);
		botaoVerificado.setBackgroundResource(R.drawable.seta_direita);
		botaoVerificado.setOnClickListener(botaoVerificadoListener());
		
	}

	private OnClickListener botaoVerificadoListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				//TODO PASSAR OS DADOS PARA O RANKING
//				Intent sairIntent = new Intent(RespostaActivity.this, AdedonhaActivity.class);
//				startActivity(sairIntent);
				finish();
				
			}
		};
	}

	private void apresentaDadosJogador() {
		//TODO Contabilizar o tempo
		//jogador.setPontuacao(pontos);
		//jogador.resultado()
		
		TextView nomeText = (TextView) findViewById(R.id.saida_jogador_adedonha);
		nomeText.setText("Jogador: " + this.jogador.nome());
		
		TextView tempoText = (TextView) findViewById(R.id.saida_tempo_adedonha);
		tempoText.setText("Tempo: " + this.jogador.tempo() + "s");
		
		TextView pontuacao = (TextView) findViewById(R.id.pontos_jogador_adedonha);
		pontuacao.setText("Pontuação: " + this.jogador.pontuacao());
		
		TextView letraText = (TextView) findViewById(R.id.saida_letra_adedonha);
		letraText.setText("Letra: " + this.configuracao.letraDaPartida());
		
	}

//	public int getPontos() {
//		return pontos;
//	}
//
//	public void setPontos(int pontos) {
//		this.pontos = pontos;
//	}

//	public String getLetraJogo() {
//		return letraJogo;
//	}
//
//	public void setLetraJogo(String letraJogo) {
//		this.letraJogo = letraJogo;
//	}

}
