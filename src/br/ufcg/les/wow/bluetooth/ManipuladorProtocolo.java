package br.ufcg.les.wow.bluetooth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Map;

import br.ufcg.les.wow.adedonha.activity.JogoAdedonhaActivity;
import br.ufcg.les.wow.adedonha.model.ConfiguracaoParatida;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

// TODO mudar esse nome.
public class ManipuladorProtocolo extends Handler implements Serializable {
	private static final long serialVersionUID = 3527073534967975662L;
	public static final int RECEBER_MENSAGEM = 1;
	public static final int ENVIAR_MENSAGEM = 2;
	
	private static final String TAG = "[Protocolo]";
	
	/* informacoes da operacao atual */
	private static final String STATUS = "status";
	private static final String SIZE = "size";
	private static final String TIPO_OPERACAO_ATUAL = "operacao";
	private static final String CURSOR = "cursor";
	
	/* status da operacao corrente */
	private static final String STATUS_EXECUTANDO = "executando";
	private static final String STATUS_ESPERANDO = "esperando";
	
	/* protocolo para as mensagens */
	public static final String CABECALHO_TAMANHO = "size=";
	public static final String CABECALHO_OPERACAO = "operacao=";
	public static final int OPERACAO_INVALIDA = 0;
	public static final int OPERACAO_CONFIGURACOES_DA_PARTIDA = 3;
	public static final String CABECALHO_CONFIGURACOES_DA_PARTIDA = CABECALHO_OPERACAO + OPERACAO_CONFIGURACOES_DA_PARTIDA + "," + CABECALHO_TAMANHO;
	public static final int OPERACAO_NOME_JOGADOR = 4;
	public static final String CABECALHO_NOME_JOGADOR = CABECALHO_OPERACAO + OPERACAO_NOME_JOGADOR + "," + CABECALHO_TAMANHO;
	public static final int OPERACAO_ENCERRAR_PARTIDA = 5;
	public static final String CABECALHO_ENCERRAR_PARTIDA = CABECALHO_OPERACAO + OPERACAO_ENCERRAR_PARTIDA + "," + CABECALHO_TAMANHO;
	
	byte[] bufferOperacao = null;
	private static ManipuladorProtocolo thisInstance = null;
	// Usados para iniciar a tela de contagem regressiva.
	private Intent iniciarPartidaIntent;
	private Context iniciarPartidaContext;
	
	// Usado para bloquear a tela quando uma requisicao de encerrar partida vem do servidor.
	private Intent encerrarPartidaIntent;
	private Context encerrarPartidaContext;
	private JogoAdedonhaActivity jogoAdedonhaActivity;
	
	private Map<String, String> dadosDaOperacao = new HashMap<String, String>();
	
	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case ENVIAR_MENSAGEM:
			Log.d(TAG, "ENVIAR_MENSAGEM");
			Log.d(TAG, "Enviando Mensagem: Tamanho="+msg.arg1);
			break;
		case RECEBER_MENSAGEM:
			Log.d(TAG, "RECEBER_MENSAGEM");
			byte[] buffer = (byte[]) msg.obj;
			recebeMensagem(buffer, msg.arg1);
			break;
		default:
			Log.d(TAG, "OPERACAO NAO IMPLEMENTADA");
			break;
		}
	}

	private void recebeMensagem(byte[] buffer, int tamanho) {
		Log.d(TAG, "isCabecalho="+isCabecalho(buffer, tamanho)+",isExecutando="+isExecutando());
		if(isCabecalho(buffer, tamanho) && !isExecutando()) {
			Log.d(TAG, "RecebendoCabecalho: " + new String(buffer, 0, tamanho));
			configuraOperacao(buffer, tamanho);
		} else {
			Log.d(TAG, "RecebendoConteudo: TamanhoBuffer="+tamanhoBuffer()+", Cursor="+cursor()+", TamanhoRecebido="+tamanho);
			if(adicioneConteudo(buffer, tamanho)) {
				execute(this.bufferOperacao, operacao());
				incializaDados();
			}
		}
	}
	
	private void incializaDados() {
		this.dadosDaOperacao.clear();
		this.dadosDaOperacao.put(TIPO_OPERACAO_ATUAL, String.valueOf(OPERACAO_INVALIDA));
		this.dadosDaOperacao.put(STATUS, STATUS_ESPERANDO);
		this.dadosDaOperacao.put(SIZE, "0");
		this.dadosDaOperacao.put(CURSOR, "0");
		this.bufferOperacao = null;
	}

	private void configuraOperacao(byte[] buffer, int tamanho) {
		this.dadosDaOperacao = extraiDadoOperacao(buffer, tamanho);
		this.bufferOperacao = new byte[tamanhoBuffer()];
	}
	
	private synchronized void execute(byte[] buffer, int operacao) {
		Object obj = deserialize(buffer);
		switch (operacao) {
		case OPERACAO_CONFIGURACOES_DA_PARTIDA:
			Log.d(TAG, "OPERACAO_CONFIGURACOES_DA_PARTIDA");
			ConfiguracaoParatida configuracaoesDaPartida = (ConfiguracaoParatida) obj;
			iniciarPartida(configuracaoesDaPartida);
			break;
		case OPERACAO_NOME_JOGADOR:
			Log.d(TAG, "OPERACAO_NOME_JOGADOR");
			String nome = (String) obj;
			Log.d(TAG, "Jogador se conectou com nome: " + nome);
			break;
		case OPERACAO_ENCERRAR_PARTIDA:
			Log.d(TAG, "OPERACAO_ENCERRAR_PARTIDA");
			Long tempoDapartida = (Long) obj;
			Log.d(TAG, "Encerrando com tempo: " + tempoDapartida);
			encerrarPartida();
			break;
		default:
			Log.e(TAG, "OPERACAO NAO SUPORTADA");
			break;
		}
	}
	
	private void encerrarPartida() {
		if(this.encerrarPartidaIntent != null && this.encerrarPartidaContext != null) {
			this.jogoAdedonhaActivity.configurarRespostas();
		} else {
			if(this.encerrarPartidaIntent == null) {
				Log.e(TAG, "encerrarPartidaIntent is null" );
			}
			if(this.encerrarPartidaContext == null) {
				Log.e(TAG, "encerrarPartidaContext is null" );
			}
		}
	}

	private void iniciarPartida(ConfiguracaoParatida configuracaoesDaPartida) {
		if(this.iniciarPartidaIntent != null && this.iniciarPartidaContext != null && configuracaoesDaPartida != null) {
			//iniciarPartidaIntent.putExtra("jogo", configuracaoesDaPartida);
			//iniciarPartidaIntent.putExtra("tempoDesejado", configuracaoesDaPartida);
			iniciarPartidaIntent.putExtra(ConfiguracaoParatida.CONFIGURACAO, configuracaoesDaPartida);
			iniciarPartidaIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			iniciarPartidaContext.startActivity(iniciarPartidaIntent);
		} else {
			if(this.iniciarPartidaIntent == null) {
				Log.e(TAG, "startGameIntent is null" );
			}
			if(this.iniciarPartidaContext == null) {
				Log.e(TAG, "startGameContext is null" );
			}
			if(configuracaoesDaPartida == null) {
				Log.e(TAG, "configuracaoesDaPartida is null" );
			}
		}
	}
	
	private int operacao() {
		if(this.dadosDaOperacao.containsKey(TIPO_OPERACAO_ATUAL)) {
			return Integer.parseInt(this.dadosDaOperacao.get(TIPO_OPERACAO_ATUAL));
		}
		return OPERACAO_INVALIDA;
	}
	
	private boolean isExecutando() {
		if(this.dadosDaOperacao.containsKey(STATUS)) {
			return this.dadosDaOperacao.get(STATUS).equalsIgnoreCase(STATUS_EXECUTANDO);
		}
		return false;
	}
	
	private synchronized boolean adicioneConteudo(byte[] buffer, int length) {
		if(this.bufferOperacao == null) {
			return false;
		}
		int cursorAtual = cursor();
		for (int i = 0; i < length; i++) {
			this.bufferOperacao[cursorAtual + i] = buffer[i];
		}
		atualizaCursor(length);
		return cursor() == tamanhoBuffer();
	}
	
	private synchronized void atualizaCursor(int bufferLength) {
		Integer cursorAtual = cursor();
		cursorAtual += bufferLength;
		this.dadosDaOperacao.put(CURSOR, cursorAtual.toString());
	}
	
	private int tamanhoBuffer() {
		if(this.dadosDaOperacao.containsKey(SIZE)) {
			return Integer.parseInt(this.dadosDaOperacao.get(SIZE));
		}
		return 0;
	}
	
	private int cursor() {
		if(this.dadosDaOperacao.containsKey(CURSOR)) {
			return Integer.parseInt(this.dadosDaOperacao.get(CURSOR));
		}
		return 0;
	}
	
	private HashMap<String, String> extraiDadoOperacao(byte[] buffer, int tamanho) {
		HashMap<String, String> dadosDaOperacao = new HashMap<String, String>();
		String cabecalho = new String(buffer, 0, tamanho);
		String[] dados = cabecalho.split(",");
		for(String dado : dados) {
			String[] chaveValor = dado.split("=");
			dadosDaOperacao.put(chaveValor[0], chaveValor[1]);
		}
		dadosDaOperacao.put(CURSOR, "0");
		dadosDaOperacao.put(STATUS, STATUS_EXECUTANDO);
		return dadosDaOperacao;
	}
	
	private boolean isCabecalho(byte[] buffer, int length) {
		try {
			String cabecalho = new String(buffer, 0, length);
			return cabecalho.startsWith(CABECALHO_OPERACAO) && cabecalho.indexOf(CABECALHO_TAMANHO) >= 0;
		} catch(Exception e) {
			return false;
		}
	}
	
	public static byte[] serialize(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(obj);
		} catch (IOException e) {
			Log.e(TAG, "Falhou tentando serializar um objeto", e);
		}
        return out.toByteArray();
    }
	
	public static Object deserialize(byte[] data) {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
		try {
			ObjectInputStream is = new ObjectInputStream(in);
			return is.readObject();
		} catch (StreamCorruptedException e) {
			Log.e(TAG, "Falhou tentando deserializar um objeto", e);
		} catch (IOException e) {
			Log.e(TAG, "Falhou tentando deserializar um objeto", e);
		} catch (ClassNotFoundException e) {
			Log.e(TAG, "Falhou tentando deserializar um objeto.", e);
		}
		return null;
	}
	
	public synchronized void setIniciarPartidaActivity(Intent startGameIntent, Context startGameContext) {
		this.iniciarPartidaIntent = startGameIntent;
		this.iniciarPartidaContext = startGameContext;
	}
	
	public synchronized void setEncerrarPartidaActivity(Intent encerrarPartidaIntent, Context encerrarPartidaContext, JogoAdedonhaActivity jogoAdedonhaActivity) {
		this.encerrarPartidaIntent = encerrarPartidaIntent;
		this.encerrarPartidaContext = encerrarPartidaContext;
		this.jogoAdedonhaActivity = jogoAdedonhaActivity;
	}
	
	public static synchronized ManipuladorProtocolo instance() {
		if(thisInstance == null) {
			thisInstance = new ManipuladorProtocolo();
		}
		return thisInstance;
	}
}