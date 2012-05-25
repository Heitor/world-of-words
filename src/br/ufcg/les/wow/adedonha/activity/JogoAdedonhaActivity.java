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
	
	private TextView nomeJogadorTextView;
	private TextView contadorTextView;
	private TextView letraTextView;
	private ImageButton botaoSair;
	private ImageButton botaoVerificar;
	
	private List<Button> listaBotoesItens = new ArrayList<Button>();
	
	private Intent intentRespostas;
	private CountDownTimer contador; 
	
	private Jogador jogador;
	private ConfiguracaoParatida configuracao;
	private Long tempoRestante = 0L;
	private boolean marcouFim = false;
	
	private Dialog dialog;

	private EditText valorItem;

	private Button botaoCancelar;

	private Button botaoConfirmar;
	private boolean done = false;
	
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
					jogador.setTempo(tempoRestante);
				}
		     }

		     public void onFinish() {
		    	 if(Servidor.instance() != null) {
		    		 Servidor.instance().enviarJogador(jogador);
		    	 }
		    	 if(Cliente.instance() != null) {
		    		 Cliente.instance().enviarJogador(jogador);
		    	 }
		    	 if (!marcouFim) {
		    		 tempoRestante = 0L;
		    		 contadorTextView.setText("Fim de jogo!");
		    		 jogador.setTempo(tempoRestante);
		    		 mostraDialogSairJogo(msgFimJogo(), fimDeJogoListener(), null);
		    	 }
		     }
		  }.start();
	}

	private void carregaVariaveisDoJogo() {
		iniciaTextViewContador();
		atualizaNomeJogador();
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
				Log.d(TAG, "Trying to finish game: " + jogador);
				finalizaVariaveisJogo();
				
				if(Cliente.instance() != null) {
					Cliente.instance().encerrarPartida(jogador);
				} else {
					if(Servidor.instance() != null) {
						Servidor.instance().encerrarPartida(jogador);
					}
				}
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
		
		//configurarRespostas(this.jogador);
		
	}

	public void configurarRespostas(Jogador jogador) {
		marcouFim = true;
		contadorTextView.setText("Fim de jogo!");
		System.out.println("TEMPO NO FINALIZAR VARIAVEIS =" + tempoRestante);
		
		this.jogador.setTempo(tempoRestante);
		Log.d(TAG, "Jogador recebido: "+jogador);
		//if(jogador != null) {
		intentRespostas.putExtra(Jogador.JOGADOR+"2", jogador);
		//}
		intentRespostas.putExtra(ConfiguracaoParatida.CONFIGURACAO, this.configuracao);
		// Se o cliente for null, quer dizer que esse é o servidor
		// Deve haver uma solução melhor pra isso.
		/*if(Cliente.instance() != null) {
			Log.d(TAG, "Enviando mensagem teste de fim de jogo.");
			Cliente.instance().enviarJogador(this.jogador);
		} else {
			Servidor.instance().enviarJogador(this.jogador);
		}*/
		this.done = true;
		mostraDialogSairJogo(msgFimJogo(), fimDeJogoListener(), jogador);
	}
	
	private DialogInterface.OnClickListener fimDeJogoListener() {
		return new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
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
	
	private void mostraDialogSairJogo(String msg, DialogInterface.OnClickListener listener, Jogador jogador) {
		AlertDialog alerta = new AlertDialog.Builder(JogoAdedonhaActivity.this).create();
		alerta.setMessage(msg);
		alerta.setButton("Ok", listener);
		alerta.show();
	}
	
	private void carregaLetra() {
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

	private OnClickListener botaoSairListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent botaoSairIntent = new Intent(getApplicationContext(), AdedonhaActivity.class);
				
				finalizaVariaveisJogo();
				Servidor.instance().encerrarPartida(jogador);
				Servidor.instance().cancelar();
				
				startActivity(botaoSairIntent);
			}
		};
	}

	private void recuperaIntent() {
		Intent intent = getIntent();
		this.configuracao = (ConfiguracaoParatida) intent.getSerializableExtra(ConfiguracaoParatida.CONFIGURACAO);
		this.jogador = (Jogador) intent.getSerializableExtra(Jogador.JOGADOR);
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
	
	public boolean ready() {
		return this.done;
	}

	public boolean isMarcouFim() {
		return marcouFim;
	}

	public void setMarcouFim(boolean marcouFim) {
		this.marcouFim = marcouFim;
	}
}
