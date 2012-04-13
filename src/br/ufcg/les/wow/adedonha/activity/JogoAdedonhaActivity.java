package br.ufcg.les.wow.adedonha.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.persistence.User;
import br.ufcg.les.wow.util.GeradorStrings;

public class JogoAdedonhaActivity extends Activity {

	private String nomeJogador = "";
	private String nivelJogo = "";

	private String letra = "";
	
	private Chronometer cronometro;
	private TextView nomeJogadorTextView;
	private TextView nivelTextView;
	private TextView letraTextView;
	private ImageButton botaoSair;
	private ImageButton botaoVerificar;
	
	private Intent intentRespostas;
	private EditText editTextNome;
	private EditText editTextObjeto;
	private EditText editTextAnimal;
	private EditText editTextFruta;
	private EditText editTextProfissao;
	private EditText editTextCarro;
	private EditText editTextPais;
	private EditText editTextSerie;
	
	private static final int TIPO_ADEDONHA = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_jogo_adedonha);
		
		recuperaIntent();
		carregaLetra();

		carregaVariaveisDoJogo();
		
		intentRespostas = new Intent(
				JogoAdedonhaActivity.this, RespostaActivity.class);

	}

	private void carregaVariaveisDoJogo() {
		iniciaCronometro();
		
		carregaVariaveisFacil();
		
		mapeiaNivel();

		atualizaNomeJogador();

		atualizaNivelJogada();

		atualizaLetraSelecionada();
		
		iniciaOpcaoNome();

		iniciaBotaoSair();
		
		iniciaBotaoVerificar();
	}

	private void carregaVariaveisFacil() {
		editTextNome = (EditText) findViewById(R.id.edit_text_nome);
		editTextObjeto = (EditText) findViewById(R.id.edit_text_objeto);
		editTextAnimal = (EditText) findViewById(R.id.edit_text_animal);
		editTextFruta = (EditText) findViewById(R.id.edit_text_fruta);

	}

	private void iniciaOpcaoNome() {
		//EditText opcaoNomeEditText = (EditText) findViewById(R.id.edit_text_nome);
		
	}
	
	private void mapeiaNivel() {
		if (nivelJogo.equalsIgnoreCase("Normal")) {
			carregaVariaveisNormal();
		
		} else if (nivelJogo.equalsIgnoreCase("Difícil")) {
			carregaVariaveisNormal();
			carregaVariaveisDificil();
		}

	}
	
	private void carregaVariaveisDificil() {
		variaveisPais();
		variaveisSerie();
		
	}

	private void variaveisSerie() {
		inicializaEditTextSerie();
		inicializaTextViewSerie();
		
	}

	private void inicializaTextViewSerie() {
		TextView textViewSerie = (TextView) findViewById(R.id.text_view_serie);
		textViewSerie.setVisibility(TextView.VISIBLE);
	}

	private void inicializaEditTextSerie() {
		editTextSerie = (EditText) findViewById(R.id.edit_text_serie);
		editTextSerie.setVisibility(EditText.VISIBLE);
		
	}

	private void variaveisPais() {
		inicializaEditTextPais();
		inicializaTextViewPais();
	}

	private void inicializaEditTextPais() {
		editTextPais = (EditText) findViewById(R.id.edit_text_pais);
		editTextPais.setVisibility(EditText.VISIBLE);
	}

	private void inicializaTextViewPais() {
		TextView textViewPais = (TextView) findViewById(R.id.text_view_pais);
		textViewPais.setVisibility(TextView.VISIBLE);
	}

	private void carregaVariaveisNormal() {
		variaveisProfissao();
		variaveisCarro();
	}

	private void variaveisCarro() {
		inicializaTextViewCarro();
		inicializaEditTextCarro();
		
	}

	private void inicializaEditTextCarro() {
		editTextCarro = (EditText) findViewById(R.id.edit_text_carro);
		editTextCarro.setVisibility(EditText.VISIBLE);
	}

	private void inicializaTextViewCarro() {
		TextView textViewCarro = (TextView) findViewById(R.id.text_view_carro);
		textViewCarro.setVisibility(TextView.VISIBLE);
	}

	private void variaveisProfissao() {
		inicializaTextViewProfissao();
		inicializaEditTextProfissao();
	}

	private void inicializaEditTextProfissao() {
		editTextProfissao = (EditText) findViewById(R.id.edit_text_profissao);
		editTextProfissao.setVisibility(EditText.VISIBLE);
	}

	private void inicializaTextViewProfissao() {
		TextView textViewProfissao = (TextView) findViewById(R.id.text_view_profissao);
		textViewProfissao.setVisibility(TextView.VISIBLE);
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
		//botaoVerificar.setOnClickListener(botaoSairListener());
	}

	private OnClickListener botaoVerificarListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				cronometro.stop();
				String tempo = (String) cronometro.getText();
				Long tempoLong = cronometro.getBase();
				
				ArrayList<String> respostas = new ArrayList<String>();
				
				respostas.add(editTextNome.getText().toString());
				respostas.add(editTextObjeto.getText().toString());
				respostas.add(editTextAnimal.getText().toString());
				respostas.add(editTextFruta.getText().toString());
				
				User jogador = new User(nomeJogador, 0, tempoLong, TIPO_ADEDONHA);
				
				intentRespostas.putExtra("jogador", jogador);
				intentRespostas.putExtra("tempoString", tempo);
				intentRespostas.putExtra("nivelJogo", nivelJogo);
				intentRespostas.putExtra("letraJogo", letra);
				
				if (nivelJogo.equalsIgnoreCase("Normal")) {
					respostas.add(editTextProfissao.getText().toString());
					respostas.add(editTextCarro.getText().toString());
				
				} else if (nivelJogo.equalsIgnoreCase("Difícil")) {
					respostas.add(editTextProfissao.getText().toString());
					respostas.add(editTextCarro.getText().toString());
					respostas.add(editTextPais.getText().toString());
					respostas.add(editTextSerie.getText().toString());
				}
				
				intentRespostas.putExtra("respostas", respostas);
				
				mostraDialogSairJogo(msgFimJogo(), listenerSair());
				
			}
		};
	}
	
	public String msgFimJogo() {
		return "FIM DE JOGO" + "\n\n Parabéns: "
				+ getNomeJogador() + "\n Tempo: "
				+ cronometro.getText();
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

	private void atualizaNivelJogada() {
		nivelTextView = (TextView) findViewById(R.id.text_view_nivel_adedonha);
		nivelTextView.setText("Nível do jogo: " + nivelJogo);
	}

	private void atualizaNomeJogador() {
		nomeJogadorTextView = (TextView) findViewById(R.id.text_view_jogador_adedonha);
		nomeJogadorTextView.setText("Boa sorte, " + nomeJogador);
		
		nomeJogadorTextView.setFocusableInTouchMode(true);
		nomeJogadorTextView.requestFocus();
	}

	private void iniciaCronometro() {
		cronometro = (Chronometer) findViewById(R.id.cronomentro_adedonha);
		cronometro.start();
	}

	private OnClickListener botaoSairListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent botaoSairIntent = new Intent(
						JogoAdedonhaActivity.this, AdedonhaActivity.class);
				// fimIntent.putExtra("usuario", usuario);
				startActivity(botaoSairIntent);
				finish();

			}
		};
	}

	private void carregaLetra() {
		List<String> letras = povoaLetras();
		letra = GeradorStrings.retornaLetra(letras);
	}

	private List<String> povoaLetras() {
		List<String> letras = new ArrayList<String>();

		for (int i = 65; i < 91; i++) {
			char a = (char) i;
			letras.add(String.valueOf(a));
		}
		return letras;
	}

	private void recuperaIntent() {
		Intent intent = getIntent();
		nomeJogador = intent.getStringExtra("nomeJogador");
		nivelJogo = intent.getStringExtra("nivel");
	}

	public String getNomeJogador() {
		return nomeJogador;
	}

	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}

	public String getNivelJogo() {
		return nivelJogo;
	}

	public void setNivelJogo(String nivelJogo) {
		this.nivelJogo = nivelJogo;
	}

}
