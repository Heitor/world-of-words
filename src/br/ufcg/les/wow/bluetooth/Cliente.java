package br.ufcg.les.wow.bluetooth;

import java.io.IOException;
import java.util.UUID;

import br.ufcg.les.wow.bluetooth.activity.ConectandoCliente;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class Cliente extends Thread {
	private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a69");
	private static final String TAG = "[Cliente]";
	
	private final BluetoothSocket clienteSocket;
	private final BluetoothDevice mmDevice;
	private final ConectandoCliente conectandoClienteActivity;
	private Protocolo handle;
	private ThreadConectada threadConectada;

	public Cliente(ConectandoCliente conectandoClienteActivity, BluetoothDevice device, Protocolo handle) {
		mmDevice = device;
		this.handle = handle;
		this.conectandoClienteActivity = conectandoClienteActivity;
		BluetoothSocket tmp = null;

		try {
			tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
		} catch (IOException e) {
			Log.e(TAG, "Falhou ao tentar se conectar com o servidor bluetooth", e);
		}
		clienteSocket = tmp;
	}

	public void run() {
		Log.i(TAG, "BEGIN mConnectThread");
		setName("ConnectThread");

		try {
			Log.d(TAG, "Conectando-se ao server socket...");
			clienteSocket.connect();
			Log.d(TAG, "Conectado ao server socket com sucesso...");
		} catch (IOException e) {
			Log.e(TAG, "Falhou enquanto conectava-se ao server socket...", e);
			cancelarConexao();
		}

		// Start the connected thread
		conecte(clienteSocket, handle);
		return;
	}

	private void conecte(BluetoothSocket socket, Protocolo handle) {
		threadConectada = new ThreadConectada(socket, handle);
		threadConectada.start();
	}
	
	public ThreadConectada threadConectada() {
		return threadConectada;
	}

	public void cancelarConexao() {
		try {
			Log.d(TAG, "Cancelando o socket...");
			clienteSocket.close();
			Log.d(TAG, "Socket cancelado com sucesso.");
		} catch (IOException e) {
			Log.e(TAG, "falhou enquanto cancelava o socket.", e);
		} finally {
			this.conectandoClienteActivity.cancela();
		}
	}
}