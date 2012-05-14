package br.ufcg.les.wow.bluetooth;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.ufcg.les.wow.adedonha.model.Jogo;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class Servidor extends Thread implements Serializable {
	private static final long serialVersionUID = 4880838429325379959L;
	private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a69");
	private final ManipuladorProtocolo handle = ManipuladorProtocolo.instance();
	private static final String WOW = "wow";
	private static final String TAG = "[Servidor]";
	private static Servidor servidor = null;
	private static BluetoothAdapter adaptadorBluetooth;

	private final BluetoothServerSocket servidorSocket;
	private boolean encerrar = false;

	private final List<ThreadConectada> threadsConectadas = new ArrayList<ThreadConectada>();

	private Servidor() {
		BluetoothServerSocket servidorSocketTemporario = null;
		try {
			servidorSocketTemporario = adaptadorBluetooth().listenUsingRfcommWithServiceRecord(WOW, MY_UUID);
		} catch (IOException e) {
			Log.e(TAG, "Falhou enquanto levantava o listener server.", e);
		}
		servidorSocket = servidorSocketTemporario;
	}

	public void run() {
		BluetoothSocket socketConectado = null;
		Log.d(TAG, "Aguardando conexao...");
		/* FIXME Esse while deve durar enquanto houver clientes para se conectar.
		 * Hoje ele aceita apenas uma conexao.
		 */
		while (socketConectado == null || this.encerrar) {
			try {
				socketConectado = servidorSocket.accept();
			} catch (IOException e) {
				Log.e(TAG, "Falhou enquanto aceitava uma conexao socket.", e);
			}
		}

		if (socketConectado != null) {
			Log.d(TAG, "Conexao estabelecida");
			encerrarServidor();
			connectar(socketConectado, this.handle);
		}

		if (this.encerrar) {
			Log.d(TAG, "Servidor encerrou sem aceitar uma conexao.");
		}
	}

	public void encerrar() {
		this.encerrar = true;
	}
	
	public void enviarConfiguracoesDaPartida(Jogo configuracoesDaPartida) {
		Log.d(TAG, "Enviando configuracoes da partida.");
		for(ThreadConectada threadConectada : this.threadsConectadas) {
			threadConectada.iniciarPartida(configuracoesDaPartida);
		}
	}
	
	public void encerrarPartida(Long tempoDaPartida) {
		Log.d(TAG, "Encerrando partida: " + this.threadsConectadas.size());
		for(ThreadConectada threadConectada : this.threadsConectadas) {
			threadConectada.encerrarPartida(tempoDaPartida);
		}
	}

	public List<ThreadConectada> threadsConectadas() {
		return this.threadsConectadas;
	}
	
	private void connectar(BluetoothSocket socket, ManipuladorProtocolo handle) {
		ThreadConectada threadsConectadas = new ThreadConectada(socket, handle);
		threadsConectadas.start();
		this.threadsConectadas.add(threadsConectadas);
	}

	private void encerrarServidor() {
		try {
			Log.d(TAG, "Encerrand o listener server socket...");
			servidorSocket.close();
			Log.d(TAG, "Server socket encerrado com sucesso.");
		} catch (IOException e) {
			Log.e(TAG, "Servidor falhou enquanto encerrava o listener server.", e);
		}
	}
	
	public static synchronized Servidor instance() {
        if (servidor == null) {
        	servidor = new Servidor();
        }
        return servidor;
	}
	
	public static synchronized Servidor newInstance() {
        	servidor = new Servidor();
        return servidor;
	}
	
	public static BluetoothAdapter adaptadorBluetooth() {
		if(adaptadorBluetooth == null) {
			adaptadorBluetooth = BluetoothAdapter.getDefaultAdapter();
			if(adaptadorBluetooth == null) {
				Log.e(TAG, "Dispositivo bluetooth nao encontrado.");
				//habilitaBluetooth();
			} else {
				Log.d(TAG, "Dispositivo bluetooth encontrado.");
			}
		}
		return adaptadorBluetooth;
	}
}