package br.ufcg.les.wow.adedonha.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import br.ufcg.les.wow.adedonha.model.Letra;
import br.ufcg.les.wow.anagrama.model.Jogo;
import br.ufcg.les.wow.persistence.User;
import br.ufcg.les.wow.util.GeradorStrings;

public class JogoAdedonhaActivity extends Activity {


	private String letra = "";
	
	//private Chronometer cronometro;
	private TextView nomeJogadorTextView;
	private TextView contadorTextView;
	private TextView nivelTextView;
	private TextView letraTextView;
	private ImageButton botaoSair;
	private ImageButton botaoVerificar;
	
	private HashMap<String, String> mapaResultados = new HashMap<String, String>();
	private List<Button> listaBotoesItens = new ArrayList<Button>();
	
	private Intent intentRespostas;
	private CountDownTimer contador; 
	
	private Jogo jogo;
	private Long tempoRestante = 0L;
	private boolean marcouFim = false;
	
	private static final int TIPO_ADEDONHA = 1;
	private long tempoInicial = 12000;

	private Dialog dialog;

	private EditText valorItem;

	private Button botaoCancelar;

	private Button botaoConfirmar;
	
	private static final int LARGURA_CAIXINHA = 90;
	private static final int ALTURA_CAIXINHA = 50;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.page_jogo_adedonha);
		
		recuperaIntent();
		
		ScrollView layout = carregaBotoesItens();
		setContentView(layout);
		
		carregaLetra();

		carregaVariaveisDoJogo();
		
		setContador(inicializaContador());
		
		intentRespostas = new Intent(
				JogoAdedonhaActivity.this, RespostaActivity.class);

	}
	
	private ScrollView carregaBotoesItens() {
		
		List<Letra> itensDesejados = jogo.getItensDesejados();
		
		ScrollView layout = (ScrollView) View.inflate(this,
				R.layout.page_jogo_adedonha, null);
		
		LinearLayout vTblRow = (LinearLayout)layout.
				findViewById(R.id.group_itens_adedonha);

		for (int i = 0; i < itensDesejados.size(); i++) {
			Button botaoItem = new Button(this);
			botaoItem.setVisibility(Button.VISIBLE);
			botaoItem.setText(itensDesejados.get(i).getDescricao());
			botaoItem.setTextSize(24);
			botaoItem.setLayoutParams(new TableRow.
					LayoutParams(LARGURA_CAIXINHA, ALTURA_CAIXINHA));
			
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
					mapaResultados.put(botaoItem.getText().toString(), valorInserido);
					botaoItem.getBackground().setColorFilter(
							new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
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
		return new CountDownTimer(tempoInicial, 1000) {

			public void onTick(long tempoMiliseconds) {
				if (!marcouFim) {
					Long tempoReal = (tempoMiliseconds / 1000);
					contadorTextView.setText("Tempo: " +tempoReal + "s");
					tempoRestante = tempoReal;
				}
		     }

		     public void onFinish() {
		    	 if (!marcouFim) {
		    		 tempoRestante = 0L;
		    		 contadorTextView.setText("Fim de jogo!");
		    		 jogo.setTempo(tempoRestante);
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
				mostraDialogSairJogo(msgFimJogo(), listenerSair());
			}
		};
	}
	
	private void finalizaVariaveisJogo() {
		marcouFim = true;
		contadorTextView.setText("Fim de jogo!");
		System.out.println("TEMPO NO FINALIZAR VARIAVEIS =" + tempoRestante);
		
		String nivelJogo = jogo.getNivelString();
		
		User jogador = new User(jogo.getNomeJogador(), 0, tempoRestante, TIPO_ADEDONHA);
		
		intentRespostas.putExtra("jogador", jogador);
		intentRespostas.putExtra("tempoJogo", tempoRestante);
		intentRespostas.putExtra("nivelJogo", nivelJogo);
		intentRespostas.putExtra("letraJogo", letra);
		intentRespostas.putExtra("respostas", mapaResultados);
		
	}
	
	private DialogInterface.OnClickListener fimDeJogoListener() {
		return new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				finalizaVariaveisJogo();
				startActivity(intentRespostas);
				finish();
			}
		};
	}
	
	public String msgFimJogo() {
		return "FIM DE JOGO" + "\n\n Parabéns: "
				+ jogo.getNomeJogador() + "\n Tempo restante: "
				+ tempoRestante + "s";
	}
	
	private DialogInterface.OnClickListener listenerSair() {
		return new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				startActivity(intentRespostas);
				finish();
			}
		};
	}
	
	private void mostraDialogSairJogo(String msg,
			DialogInterface.OnClickListener listener) {
		AlertDialog alerta = new AlertDialog.
				Builder(JogoAdedonhaActivity.this).create();
		alerta.setMessage(msg);
		alerta.setButton("Ok", listener);
		alerta.show();
	}

	private void atualizaLetraSelecionada() {
		letraTextView = (TextView) findViewById(R.id.text_view_letra_adedonha);
		letraTextView.setText("Letra selecionada: " + letra.toUpperCase());
	}

//	private void atualizaNivelJogada() {
//		nivelTextView = (TextView) findViewById(R.id.text_view_nivel_adedonha);
//		nivelTextView.setText("Nível do jogo: " + jogo.getNivelString());
//	}

	private void atualizaNomeJogador() {
		nomeJogadorTextView = (TextView) findViewById(R.id.text_view_jogador_adedonha);
		nomeJogadorTextView.setText("Boa sorte, " + jogo.getNomeJogador());
		
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
				Intent botaoSairIntent = new Intent(
						JogoAdedonhaActivity.this, AdedonhaActivity.class);
				// fimIntent.putExtra("usuario", usuario);
				finalizaVariaveisJogo();
				startActivity(botaoSairIntent);
				finish();
				
			}
		};
	}

	private void carregaLetra() {
		List<Letra> letras = jogo.getLetrasDesejadas();
		letra = GeradorStrings.retornaLetra(letras).getDescricao();
	}
	

	private void recuperaIntent() {
		Intent intent = getIntent();
		jogo = (Jogo) intent.getSerializableExtra("jogo");
		setTempoInicial(intent.getLongExtra("tempoDesejado", 120000L));
		System.out.println(jogo.getLetrasDesejadas().size());
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
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

	public long getTempoInicial() {
		return tempoInicial;
	}

	public void setTempoInicial(long tempoInicial) {
		this.tempoInicial = tempoInicial;
	}

	public HashMap<String, String> getMapaResultados() {
		return mapaResultados;
	}

	public void setMapaResultados(HashMap<String, String> mapaResultados) {
		this.mapaResultados = mapaResultados;
	}

}
