package br.ufcg.les.wow.adedonha.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.model.ConfiguracaoParatida;
import br.ufcg.les.wow.adedonha.model.Letra;
import br.ufcg.les.wow.adedonha.model.Jogador;
import br.ufcg.les.wow.bluetooth.Cliente;
import br.ufcg.les.wow.bluetooth.ManipuladorProtocolo;
import br.ufcg.les.wow.bluetooth.Servidor;

public class JogoAdedonhaActivity extends Activity {
	private static final String TAG = "[JogoAdedonhaActivity]";

	private String letra = "";
	
	//private Chronometer cronometro;
	private TextView nomeJogadorTextView;
	private TextView contadorTextView;
	private TextView letraTextView;
	private ImageButton botaoSair;
	private ImageButton botaoVerificar;
	
	//private HashMap<String, String> mapaResultados = new HashMap<String, String>();
	private List<Button> listaBotoesItens = new ArrayList<Button>();
	
	private Intent intentRespostas;
	private CountDownTimer contador; 
	
	//private Jogo jogo;
	private Jogador jogador;
	private ConfiguracaoParatida configuracao;
	private Long tempoRestante = 0L;
	private boolean marcouFim = false;
	
	//private static final int TIPO_ADEDONHA = 1;
	//private long tempoInicial = 12000;

	private Dialog dialog;

	private EditText valorItem;

	private Button botaoCancelar;

	private Button botaoConfirmar;
	
	private static final int LARGURA_CAIXINHA = 90;
	private static final int ALTURA_CAIXINHA = 50;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		recuperaIntent();
		
		ScrollView layout = carregaBotoesItens();
		setContentView(layout);

		carregaVariaveisDoJogo();
		
		setContador(inicializaContador());
		
		intentRespostas = new Intent(getApplicationContext(), RespostaActivity.class);
		ManipuladorProtocolo.instance().setEncerrarPartidaActivity(this);

	}
	
	private ScrollView carregaBotoesItens() {
		
		List<Letra> itensDesejados = this.configuracao.itensDesejados();
		
		ScrollView layout = (ScrollView) View.inflate(this,
				R.layout.page_jogo_adedonha, null);
		
		LinearLayout vTblRow = (LinearLayout)layout.
				findViewById(R.id.group_itens_adedonha);

		for (int i = 0; i < itensDesejados.size(); i++) {
			Button botaoItem = new Button(this);
			botaoItem.setVisibility(Button.VISIBLE);
			botaoItem.setText(itensDesejados.get(i).getDescricao());
			botaoItem.setTextSize(24);
			botaoItem.setLayoutParams(new TableRow.LayoutParams(LARGURA_CAIXINHA, ALTURA_CAIXINHA));
			
			vTblRow.addView(botaoItem);
			listaBotoesItens.add(botaoItem);
		}
		
		addOnClickListenerBotoes();
		
		return layout;
	}
	
	private void addOnClickListenerBotoes() {
		for (Button botaoItem : listaBotoesItens) {
			botaoItem.setOnClickListener(clickListenerItem(botaoItem));
		}
		
	}
	
	private OnClickListener clickListenerItem(final Button botaoItem) {
		return new OnClickListener() {
			
			public void onClick(View v) {
				mostraDialog(botaoItem);
			}
		};
	}
	
	private void mostraDialog(Button botaoItem) {
		dialog = new Dialog(this);

		dialog.setContentView(R.layout.custom_dialog_adedonha);
		dialog.setTitle("Valor para o item: " + botaoItem.getText());
		
		valorItem = (EditText) dialog.findViewById(R.id.edittext_dialog_adedonha);
		
		botaoCancelar = (Button) dialog.findViewById(R.id.cancelar_dialog_adedonha);
		botaoCancelar.setOnClickListener(cancelarListener());
		
		botaoConfirmar = (Button) dialog.findViewById(R.id.confirmar_dialog_adedonha);
		botaoConfirmar.setOnClickListener(confirmarDialogListener(botaoItem));
		
		dialog.show();

	}

	private OnClickListener confirmarDialogListener(final Button botaoItem) {
		return new OnClickListener() {
			
			public void onClick(View v) {
				String valorInserido = valorItem.getText().toString();
				if (entradaValida(valorInserido)) {
					jogador.putResultado(botaoItem.getText().toString(), valorInserido);
					//mapaResultados.put(botaoItem.getText().toString(), valorInserido);
					botaoItem.getBackground().setColorFilter(
							new LightingColorFilter(0xFFFFFFFF, 0xff0000ff));
					dialog.dismiss();
				}
			}

			private boolean entradaValida(String valorInserido) {
				return valorInserido != null && !valorInserido.trim().equals("");
			}
		};
	}

	private OnClickListener cancelarListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				dialog.dismiss();
			}
		};
	}

	private CountDownTimer inicializaContador() {
		return new CountDownTimer(configuracao.tempo(), 1000) {

			public void onTick(long tempoMiliseconds) {
				if (!marcouFim) {
					Long tempoReal = (tempoMiliseconds / 1000);
					contadorTextView.setText("Tempo: " +tempoReal + "s");
					tempoRestante = tempoReal;
				}
		     }

		     public void onFinish() {
		    	 Servidor.instance().encerrarPartida(jogador);
		    	 if (!marcouFim) {
		    		 tempoRestante = 0L;
		    		 contadorTextView.setText("Fim de jogo!");
		    		 //jogo.setTempo(tempoRestante);
		    		 jogador.setTempo(tempoRestante);
		    		 mostraDialogSairJogo(msgFimJogo(), fimDeJogoListener());
		    	 }
		     }
		  }.start();
	}

	private void carregaVariaveisDoJogo() {
		iniciaCronometro();
		
		iniciaTextViewContador();
		
		atualizaNomeJogador();

		//atualizaNivelJogada();

		atualizaLetraSelecionada();
		
		iniciaBotaoSair();
		
		iniciaBotaoVerificar();
	}

	private void iniciaTextViewContador() {
		contadorTextView = (TextView) findViewById(R.id.contador_adedonha);
	}

	private void iniciaBotaoSair() {
		botaoSair = (ImageButton) findViewById(R.id.imageBotaoSair_adedonha);
		botaoSair.setBackgroundResource(R.drawable.close);
		botaoSair.setOnClickListener(botaoSairListener());
	}
	
	private void iniciaBotaoVerificar() {
		botaoVerificar = (ImageButton) findViewById(R.id.imageBotaoVerificar_adedonha);
		botaoVerificar.setBackgroundResource(R.drawable.seta_direita);
		botaoVerificar.setOnClickListener(botaoVerificarListener());
	}

	private OnClickListener botaoVerificarListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				finalizaVariaveisJogo();
				Servidor.instance().encerrarPartida(jogador);
				//mostraDialogSairJogo(msgFimJogo(), listenerSair());
			}
		};
	}
	
	public void setJogadorOnRespostasIntent(Jogador jogador) {
		intentRespostas.putExtra(Jogador.JOGADOR, jogador);
	}
	
	private void finalizaVariaveisJogo() {
		marcouFim = true;
		contadorTextView.setText("Fim de jogo!");
		System.out.println("TEMPO NO FINALIZAR VARIAVEIS =" + tempoRestante);
		
		configurarRespostas(this.jogador);
		
	}

	public void configurarRespostas(Jogador jogador) {
		marcouFim = true;
		contadorTextView.setText("Fim de jogo!");
		System.out.println("TEMPO NO FINALIZAR VARIAVEIS =" + tempoRestante);
		
		//Jogador jogador = new Jogador(jogo.getNomeJogador(), 0, tempoRestante, TIPO_ADEDONHA);
		
		this.jogador.setTempo(tempoRestante);
		intentRespostas.putExtra(Jogador.JOGADOR, jogador);
		//intentRespostas.putExtra("tempoJogo", tempoRestante);
		intentRespostas.putExtra(ConfiguracaoParatida.CONFIGURACAO, this.configuracao);
		//intentRespostas.putExtra("respostas", mapaResultados);
		Cliente cliente = Cliente.instance();
		// Se o cliente for null, quer dizer que esse é o servidor
		// Deve haver uma solução melhor pra isso.
		if(cliente != null) {
			Log.d(TAG, "Enviando mensagem teste de fim de jogo.");
			cliente.enviarJogador(this.jogador);
		}
		mostraDialogSairJogo(msgFimJogo(), fimDeJogoListener());
		//startActivity(intentRespostas);
	}
	
	private DialogInterface.OnClickListener fimDeJogoListener() {
		return new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				//finalizaVariaveisJogo();
				startActivity(intentRespostas);
				finish();
			}
		};
	}
	
	public String msgFimJogo() {
		return "FIM DE JOGO" + "\n\n Parabéns: "
				+ this.jogador.nome() + "\n Tempo restante: "
				+ tempoRestante + "s";
	}
	
	private DialogInterface.OnClickListener listenerSair() {
		return new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				startActivity(intentRespostas);
				//finish();
			}
		};
	}
	
	private void mostraDialogSairJogo(String msg, DialogInterface.OnClickListener listener) {
		AlertDialog alerta = new AlertDialog.Builder(JogoAdedonhaActivity.this).create();
		alerta.setMessage(msg);
		alerta.setButton("Ok", listener);
		alerta.show();
	}
	
	private void carregaLetra() {
		//List<Letra> letras = ;
		letra = this.configuracao.letraDaPartida().getDescricao();
	}

	private void atualizaLetraSelecionada() {
		letraTextView = (TextView) findViewById(R.id.text_view_letra_adedonha);
		carregaLetra();
		letraTextView.setText("Letra selecionada: " + letra.toUpperCase());
	}

	private void atualizaNomeJogador() {
		nomeJogadorTextView = (TextView) findViewById(R.id.text_view_jogador_adedonha);
		nomeJogadorTextView.setText("Boa sorte, " + this.jogador.nome());
		
		nomeJogadorTextView.setFocusableInTouchMode(true);
		nomeJogadorTextView.requestFocus();
	}

	private void iniciaCronometro() {
//		cronometro = (Chronometer) findViewById(R.id.cronomentro_adedonha);
//		cronometro.start();
	}

	private OnClickListener botaoSairListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent botaoSairIntent = new Intent(getApplicationContext(), AdedonhaActivity.class);
				
				finalizaVariaveisJogo();
				Servidor.instance().encerrarPartida(jogador);
				startActivity(botaoSairIntent);
				//finish();
			}
		};
	}

	private void recuperaIntent() {
		Intent intent = getIntent();
		this.configuracao = (ConfiguracaoParatida) intent.getSerializableExtra(ConfiguracaoParatida.CONFIGURACAO);
		this.jogador = (Jogador) intent.getSerializableExtra(Jogador.JOGADOR);
		//jogo = (Jogo) intent.getSerializableExtra("jogo");
		//setTempoInicial(this.configuracao.tempo());
	}

	public Jogador jogador() {
		return this.jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public TextView getContadorTextView() {
		return contadorTextView;
	}

	public void setContadorTextView(TextView contadorTextView) {
		this.contadorTextView = contadorTextView;
	}

	public CountDownTimer getContador() {
		return contador;
	}

	public void setContador(CountDownTimer contador) {
		this.contador = contador;
	}

	public boolean isMarcouFim() {
		return marcouFim;
	}

	public void setMarcouFim(boolean marcouFim) {
		this.marcouFim = marcouFim;
	}

//	public HashMap<String, String> getMapaResultados() {
//		return mapaResultados;
//	}
//
//	public void setMapaResultados(HashMap<String, String> mapaResultados) {
//		this.mapaResultados = mapaResultados;
//	}

}
