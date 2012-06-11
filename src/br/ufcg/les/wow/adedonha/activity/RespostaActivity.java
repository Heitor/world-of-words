package br.ufcg.les.wow.adedonha.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.model.ConfiguracaoParatida;
import br.ufcg.les.wow.adedonha.model.Jogador;

public class RespostaActivity extends Activity {

	private ConfiguracaoParatida configuracao;
	private Jogador jogador;
	private List<ToggleButton> evaluations = new ArrayList<ToggleButton>();

	private int pontuation = 0;
	private TextView pontuationTextView;
	private static final int VALID_ANSWER = 20;
	private static final int INVALID_ANSWER = 20;
	private static final String PONTUATION_TEXT = "Pontuação: ";

	private static final String VALID = "Válida";
	private static final String INVALID = "Inválida";
	private static final String EVALUATION = "Avaliação";
	private static final int TEXT_SIZE_TOGGLE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_conferir_adedonha);

		Intent intent = getIntent();

		this.configuracao = (ConfiguracaoParatida) intent
				.getSerializableExtra(ConfiguracaoParatida.CONFIGURACAO);
		this.jogador = (Jogador) intent.getSerializableExtra(Jogador.JOGADOR
				+ "2");

		// setLetraJogo(intent.getStringExtra("letraJogo"));

		// respostas = (HashMap<String, String>)
		// intent.getSerializableExtra("respostas");

		ScrollView layout = carregaRespostas();

		// System.out.println("Tamanho do mapa nas respostas = "
		// +respostas.size());

		// Jogador jogador = (Jogador) intent.getSerializableExtra("jogador");
		// tempoRestante = intent.getLongExtra("tempoJogo", 0L);

		// apresentaPalavras(respostas);

		setContentView(layout);
		apresentaDadosJogador();
		inicializaBotaoVerificado();
	}

	private ScrollView carregaRespostas() {

		ScrollView layout = (ScrollView) View.inflate(this,
				R.layout.page_conferir_adedonha, null);

		LinearLayout vTblRow = (LinearLayout) layout
				.findViewById(R.id.group_respostas_adedonha);

		List<String> itensList = new ArrayList<String>();
		// Set<String> itens = respostas.keySet();
		Set<String> itens = jogador.resultado().keySet();
		for (String iten : itens) {
			itensList.add(iten);
		}

		List<String> valoresList = new ArrayList<String>();
		Collection<String> valores = (Collection<String>) jogador.resultado()
				.values();
		for (String valor : valores) {
			valoresList.add(valor);
		}

		for (int i = 0; i < jogador.resultado().size(); i++) {
			TextView resultadoTextView = insertAnswerTextView(itensList,
					valoresList, i);

			vTblRow.addView(resultadoTextView);

			ToggleButton evaluationToggle = insertToggleButton();
			vTblRow.addView(evaluationToggle);
		}

		return layout;
	}

	private TextView insertAnswerTextView(List<String> itensList,
			List<String> valoresList, int i) {
		TextView resultadoTextView = new TextView(this);
		resultadoTextView.setText(itensList.get(i) + ": " + valoresList.get(i));
		resultadoTextView.setTextAppearance(getApplicationContext(),
				R.style.negrito);
		return resultadoTextView;
	}

	private ToggleButton insertToggleButton() {
		ToggleButton evaluationToggle = new ToggleButton(this);
		evaluationToggle.setText(EVALUATION);
		evaluationToggle.setTextOff(INVALID);
		evaluationToggle.setTextOn(VALID);
		evaluationToggle.setTextSize(TEXT_SIZE_TOGGLE);

		evaluationToggle.setOnCheckedChangeListener(evaluationToggleListener());

		this.evaluations.add(evaluationToggle);

		return evaluationToggle;
	}

	private OnCheckedChangeListener evaluationToggleListener() {
		return new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					pontuation += VALID_ANSWER;

				} else {
					if (pontuation >= VALID_ANSWER) {
						pontuation -= INVALID_ANSWER;
					}
				}
				pontuationTextView.setText(PONTUATION_TEXT + pontuation);
			}
		};
	}

	private void inicializaBotaoVerificado() {
		ImageButton botaoVerificado = (ImageButton) findViewById(R.id.botao_verificado_adedonha);
		botaoVerificado.setBackgroundResource(R.drawable.seta_direita);
		botaoVerificado.setOnClickListener(botaoVerificadoListener(pontuation));

	}

	private OnClickListener botaoVerificadoListener(final int pont) {
		return new OnClickListener() {

			public void onClick(View v) {
//				// TODO PASSAR OS DADOS PARA O RANKING
//				Intent sairIntent = new Intent(RespostaActivity.this,
//				 AdedonhaActivity.class);
//				sairIntent.putExtra("jogador", jogador);
//				sendBroadcast(sairIntent);
//				// startActivity(sairIntent);
//				finish();
				mostraDialogSairJogo("Parabéns " + jogador.getNome() + "\n" +
						"Você fez " + pontuation + " Pontos", alertaFimListener() , listenerShare());

			}
		};
	}
	
	private DialogInterface.OnClickListener listenerShare() {
		return new DialogInterface.OnClickListener() {

			private void shareIt() {
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "Fiz " + pontuation
						+ " pontos no Adedonha !"
						+ "\nTente você também!!";
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent, "Share via"));
			}

			public void onClick(DialogInterface dialog, int which) {
				shareIt();
			}
		};
	}
	
	private DialogInterface.OnClickListener alertaFimListener() {
		return new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				finish();
			}
		};
	}
	
	private void sairDoJogo() {
//		Intent fimIntent = new Intent(RespostaActivity.this,
//				AdedonhaActivity.class);
//		fimIntent.putExtra("usuario", usuario);
//		startActivity(fimIntent);
		finish();
	}

	@Override
	protected void onDestroy() {
//		jogador.setPontuacao(pontuation);
//		System.out
//				.println("CHAMOU AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
//		if (FactoryDao.getRankingDaoInstance().addJogador(jogador)) {
//			try {
//				FactoryDao.getUsuarioDaoInstance().open();
//				FactoryDao.getUsuarioDaoInstance().inserirObjeto(jogador);
//				usuarioDAO.open();
//				usuarioDAO.inserirObjeto(usuario.getNome(),
//						usuario.getPontucao(), usuario.getTempo());
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			usuarioDAO.close();
//		}
//		;
		super.onDestroy();

	}
	
	private void mostraDialogSairJogo(String msg,
			DialogInterface.OnClickListener listener,
			DialogInterface.OnClickListener listenerButton2) {
		AlertDialog alerta = new AlertDialog.Builder(RespostaActivity.this)
				.create();
		alerta.setMessage(msg);
		alerta.setButton2("Share", listenerButton2);
		alerta.setButton("Ok", listener);
		alerta.show();
	}
	
	private void apresentaDadosJogador() {
		// TODO Contabilizar o tempo
		// jogador.setPontuacao(pontos);
		// jogador.resultado()

		TextView nomeText = (TextView) findViewById(R.id.saida_jogador_adedonha);
		nomeText.setText("Jogador: " + this.jogador.nome());

		TextView tempoText = (TextView) findViewById(R.id.saida_tempo_adedonha);
		tempoText.setText("Tempo: " + this.jogador.tempo() + "s");

		pontuationTextView = (TextView) findViewById(R.id.pontos_jogador_adedonha);
		pontuationTextView.setText(PONTUATION_TEXT
				+ this.jogador.getPontuacao());

		TextView letraText = (TextView) findViewById(R.id.saida_letra_adedonha);
		letraText.setText("Letra: " + this.configuracao.letraDaPartida());

	}

}
