package br.ufcg.les.wow.bluetooth.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.ufcg.les.wow.bluetooth.Protocolo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class Servidor extends Thread {
	private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a69");
	private static final String WOW = "wow";
	private static final String TAG = "[Servidor]";

	private final BluetoothServerSocket servidorSocket;
	private BluetoothAdapter adaptadorBluetooth = null;
	private Protocolo protocolo;
	private boolean encerrar = false;

	private final List<ServidorThreadConectada> threadsConectadas = new ArrayList<ServidorThreadConectada>();

	public Servidor(BluetoothAdapter adaptadorBluetooth,
			Protocolo handle) {
		BluetoothServerSocket servidorSocketTemporario = null;
		this.adaptadorBluetooth = adaptadorBluetooth;
		this.protocolo = handle;
		try {
			servidorSocketTemporario = this.adaptadorBluetooth.listenUsingRfcommWithServiceRecord(
					WOW, MY_UUID);
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
				Log.e(TAG, "Falhou enquanto aceitava uma conexão socket.", e);
				//break;
			}
		}

		if (socketConectado != null) {
			Log.d(TAG, "Conexao estabelecida");
			encerrarServidor();
			connectar(socketConectado, this.protocolo);
		}

		if (this.encerrar) {
			Log.d(TAG, "Servidor encerrou sem aceitar uma conexao.");
		}
	}

	public void encerrar() {
		this.encerrar = true;
	}

	public List<ServidorThreadConectada> threadsConectadas() {
		return this.threadsConectadas;
	}
	
	private void connectar(BluetoothSocket socket, Protocolo handle) {
		ServidorThreadConectada threadsConectadas = new ServidorThreadConectada(socket, handle);
		threadsConectadas.start();
		this.threadsConectadas.add(threadsConectadas);
	}

	private void encerrarServidor() {
		try {
			Log.d(TAG, "Cancelando o server socket...");
			servidorSocket.close();
			Log.d(TAG, "Server socket cancelado com sucesso.");
		} catch (IOException e) {
			Log.e(TAG, "Servidor falhou enquanto cancelava.", e);
		}
	}
}