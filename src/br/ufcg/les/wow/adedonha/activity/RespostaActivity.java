package br.ufcg.les.wow.adedonha.activity;

import java.sql.SQLException;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.persistence.AdedonhaDAOImpl;
import br.ufcg.les.wow.adedonha.persistence.Palavra;
import br.ufcg.les.wow.persistence.User;

public class RespostaActivity extends Activity {
	
	private int nivel;
	private int pontos;
	private Long tempoRestante = 0L;
	private AdedonhaDAOImpl adedonhaDao;
	
	private String letraJogo = "";
	private HashMap<String, String> respostas;
	
	private static final int ACERTO = 20;
	private static final int ERRO = 5; 
	
	
	@SuppressWarnings({ "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_conferir_adedonha);
		
		adedonhaDao = new AdedonhaDAOImpl(getApplicationContext());
		
		Intent intent = getIntent();
		
		setLetraJogo(intent.getStringExtra("letraJogo"));
		
		respostas = (HashMap<String, String>) intent.
				getSerializableExtra("respostas");
		
		ScrollView layout = carregaRespostas();
		
		System.out.println("Tamanho do mapa nas respostas = "  +respostas.size());
		
		User jogador = (User) intent.getSerializableExtra("jogador");
		tempoRestante = intent.getLongExtra("tempoJogo", 0L);
		
		//apresentaPalavras(respostas);
		
		setContentView(layout);
		apresentaDadosJogador(jogador);
		inicializaBotaoVerificado();
	}
	
	
	private ScrollView carregaRespostas() {
		
		ScrollView layout = (ScrollView) View.inflate(this,
				R.layout.page_conferir_adedonha, null);
		
		LinearLayout vTblRow = (LinearLayout)layout.
				findViewById(R.id.group_respostas_adedonha);
		
		List<String> itensList = new ArrayList<String>();
		Set<String> itens = respostas.keySet();
		for (String iten : itens) {
			itensList.add(iten);
		}
		
		List<String> valoresList = new ArrayList<String>();
		Collection<String> valores = (Collection<String>) respostas.values();
		for (String valor : valores) {
			valoresList.add(valor);
		}

		for (int i = 0; i < respostas.size(); i++) {
			TextView resultadoTextView = new TextView(this);
			resultadoTextView.setText(itensList.get(i) + ": " + valoresList.get(i));
			resultadoTextView.setTextSize(24);
			resultadoTextView.setTextColor(0xFFFFFFFF);
			
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

	private void apresentaDadosJogador(User jogador) {
		//TODO Contabilizar o tempo
		jogador.setPointing(pontos);
		
		TextView nomeText = (TextView) findViewById(R.id.saida_jogador_adedonha);
		nomeText.setText("Jogador: " + jogador.getUserName());
		
		TextView tempoText = (TextView) findViewById(R.id.saida_tempo_adedonha);
		tempoText.setText("Tempo: " + tempoRestante + "s");
		
		TextView pontuacao = (TextView) findViewById(R.id.pontos_jogador_adedonha);
		pontuacao.setText("Pontuação: " + jogador.getPointing());
		
	}



	private void verificaPalavra(String palavra, int rowImagem1Adedonha,
			int rowPalavra1Col2Adedonha, int rowImageButton) {
		
		TextView text = (TextView) findViewById(rowPalavra1Col2Adedonha);
		//ImageView imagem = (ImageView) findViewById(rowImagem1Adedonha);
		//ImageButton sugestao = (ImageButton) findViewById(rowImageButton);
		//imagem.setImageResource(R.drawable.icone_ok_v2);
		
//		if (verificaPalavra(palavra)) {
//			pontos += ACERTO;
//			imagem.setImageResource(R.drawable.icone_ok_v2);
//		
//		} else {
//			
//			if (pontos > 10) {
//				pontos -= ERRO;
//			}
//			
//			if (!palavra.equals("")) {
//				imagem.setImageResource(R.drawable.icone_not_ok);
//				sugestao.setVisibility(ImageButton.VISIBLE);
//				sugestao.setBackgroundResource(R.drawable.atencao);
//			}
//			
//			//sugestao.setOnClickListener(listener);
//		}
		
		text.setText(palavra);
		
	}

	private boolean verificaPalavra(String palavra) {
		try {
			adedonhaDao.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Palavra> listarObjetos = adedonhaDao.listarObjetos();
		adedonhaDao.close();
		
		for (Palavra pal : listarObjetos) {
			if (pal.getWord().equalsIgnoreCase(palavra) && 
					pal.getWord().substring(0, 1).equalsIgnoreCase(letraJogo)) {
				return true;
			}
		}
		return false;
		
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public String getLetraJogo() {
		return letraJogo;
	}

	public void setLetraJogo(String letraJogo) {
		this.letraJogo = letraJogo;
	}

}
