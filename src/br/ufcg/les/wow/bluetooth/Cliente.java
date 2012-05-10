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
	
	private final BluetoothSocket mmSocket;
	private final BluetoothDevice mmDevice;
	private final ConectandoCliente conecandoCliente;
	private Protocolo handle;
	private ThreadConectada ct;

	public Cliente(ConectandoCliente conecandoCliente, BluetoothDevice device, Protocolo handle) {
		mmDevice = device;
		this.handle = handle;
		this.conecandoCliente = conecandoCliente;
		BluetoothSocket tmp = null;

		try {
			tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
		} catch (IOException e) {
			Log.e(TAG, "Falhou ao tentar se conectar com o servidor bluetooth", e);
		}
		mmSocket = tmp;
	}

	public void run() {
		Log.i(TAG, "BEGIN mConnectThread");
		setName("ConnectThread");

		try {
			Log.d(TAG, "Conectando-se ao server socket...");
			mmSocket.connect();
			Log.d(TAG, "Conectado ao server socket com sucesso...");
		} catch (IOException e) {
			Log.e(TAG, "Falhou enquanto conectava-se ao server socket...", e);
			cancel();
		}

		// Start the connected thread
		conecte(mmSocket, handle);
		return;
	}

	private void conecte(BluetoothSocket socket, Protocolo handle) {
		ct = new ThreadConectada(socket, handle);
		ct.start();
	}
	
	public ThreadConectada threadConectada() {
		return ct;
	}

	public void cancel() {
		try {
			Log.d(TAG, "Cancelando o socket...");
			mmSocket.close();
			Log.d(TAG, "Socket cancelado com sucesso.");
		} catch (IOException e) {
			Log.e(TAG, "falhou enquanto cancelava o socket.", e);
		} finally {
			this.conecandoCliente.cancela();
		}
	}
}