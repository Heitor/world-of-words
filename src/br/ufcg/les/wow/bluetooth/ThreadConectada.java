package br.ufcg.les.wow.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import br.ufcg.les.wow.adedonha.model.Jogador;


import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ThreadConectada extends Thread {
	private static final String TAG = "[ThreadConectada]";
	private final BluetoothSocket socketConectado;
	private final InputStream streamDeEntrada;
	private final OutputStream streamDeSaida;
	private final ManipuladorProtocolo handle;

	public ThreadConectada(BluetoothSocket socketConectado, ManipuladorProtocolo protocolo) {
		Log.d(TAG, "Thread criada.");
		this.socketConectado = socketConectado;
		this.handle = protocolo;
		InputStream streamDeEntrada = null;
		OutputStream streamDeSaida = null;

		if (socketConectado == null) {
			Log.e(TAG, "Socket = NULL");
		}
		try {
			streamDeEntrada = socketConectado.getInputStream();
			streamDeSaida = socketConectado.getOutputStream();
		} catch (IOException e) {
			Log.e(TAG, "Um erro ocorreu enquanto configurava os sockets", e);
		}

		this.streamDeEntrada = streamDeEntrada;
		this.streamDeSaida = streamDeSaida;
	}

	public BluetoothSocket socketConectado() {
		return this.socketConectado;
	}

	public void run() {
		Log.d(TAG, "Iniciando listener...");
		startListener();
	}
	
	private synchronized void startListener() {
		byte[] buffer = new byte[1024];
		int tamanhoBuffer;

		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(streamDeEntrada);
				tamanhoBuffer = ois.read(buffer);
				//tamanhoBuffer = streamDeEntrada.read(buffer);
				this.handle.obtainMessage(ManipuladorProtocolo.RECEBER_MENSAGEM, tamanhoBuffer, -1, buffer).sendToTarget();
				buffer = new byte[1024];
			} catch (IOException e) {
				Log.e(TAG, "Um erro ocorreu enquanto recebia dados.", e);
				break;
			}
		}
	}

	private byte[] cabecalhoConfigurarPartida(byte[] buffer) {
		String operationInfo = ManipuladorProtocolo.CABECALHO_CONFIGURACOES_DA_PARTIDA + buffer.length;
		return operationInfo.getBytes();
	}
	
	private byte[] cabecalhoNome(byte[] buffer) {
		String operationInfo = ManipuladorProtocolo.CABECALHO_NOME_JOGADOR + buffer.length;
		return operationInfo.getBytes();
	}
	
	private byte[] cabecalhoEncerrarPartida(byte[] buffer) {
		String operationInfo = ManipuladorProtocolo.CABECALHO_ENCERRAR_PARTIDA + buffer.length;
		return operationInfo.getBytes();
	}
	
	private byte[] cabecalhoEnviarJogador(byte[] buffer) {
		String operationInfo = ManipuladorProtocolo.CABECALHO_JOGADOR + buffer.length;
		return operationInfo.getBytes();
	}
	
	public void enviarJogador(Jogador jogador) {
		if(jogador == null) {
			Log.e(TAG, "Falhou tentando enviar um jogador nulo.");
			return;
		}
		byte[] buffer = ManipuladorProtocolo.serialize(jogador);
		byte[] bufferCabecalho = cabecalhoEnviarJogador(buffer);
		enviar(bufferCabecalho);
		enviar(buffer);
	}
	
	public void enviarNome(Serializable nome) {
		if(nome == null) {
			Log.e(TAG, "Falhou tentando enviar um nome nulo.a");
			return;
		}
		byte[] buffer = ManipuladorProtocolo.serialize(nome);
		byte[] bufferCabecalho = cabecalhoNome(buffer);
		enviar(bufferCabecalho);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		enviar(buffer);
	}

	public void iniciarPartida(Serializable configuracoesDaPartida) {
		if(configuracoesDaPartida == null) {
			Log.e(TAG, "Falhou tentando enviar configuracoes nulas da partida..");
			return;
		}
		byte[] buffer = ManipuladorProtocolo.serialize(configuracoesDaPartida);
		byte[] bufferCabecalho = cabecalhoConfigurarPartida(buffer);
		enviar(bufferCabecalho);
		enviar(buffer);
	}
	
	public void encerrarPartida(Serializable jogador) {
		byte[] buffer = ManipuladorProtocolo.serialize(jogador);
		byte[] bufferCabecalho = cabecalhoEncerrarPartida(buffer);
		enviar(bufferCabecalho);
		enviar(buffer);
	}

	public void enviar(byte[] buffer) {
		try {
			//streamDeSaida.write(buffer);
			ObjectOutputStream oos = new ObjectOutputStream(streamDeSaida);
			oos.write(buffer);
			oos.flush();
			this.handle.obtainMessage(ManipuladorProtocolo.ENVIAR_MENSAGEM, buffer.length, -1, buffer).sendToTarget();
		} catch (IOException e) {
			this.handle.obtainMessage(ManipuladorProtocolo.RECEBER_MENSAGEM, buffer.length, -1, buffer).sendToTarget();
			Log.e(TAG, "Exception during write", e);
		}
	}

	public void cancelar() {
		try {
			socketConectado.close();
		} catch (IOException e) {
			Log.e(TAG, "close() of connect socket failed", e);
		}
	}
}