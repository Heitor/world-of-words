package br.ufcg.les.wow.bluetooth.server;

import java.io.IOException;
import java.util.UUID;

import br.ufcg.les.wow.bluetooth.WoWHandle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    public class ConnectServerThread extends Thread {
    	private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a69");
    	private static final String APP_NAME = "wow";
    	private static final String TAG = "[ConnectServerThread]";
    	
    	private final BluetoothServerSocket mmServerSocket;
        private BluetoothAdapter bluetoothAdapter = null;
        private WoWHandle handle;

        public ConnectServerThread(BluetoothAdapter bluetoothAdapter, WoWHandle handle) {
            BluetoothServerSocket tmp = null;
            this.bluetoothAdapter = bluetoothAdapter;
            this.handle = handle; 
		 try {
	            tmp = this.bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID);
	        } catch (IOException e) { }
	        mmServerSocket = tmp;
        }

        public void run() {
        	BluetoothSocket socket = null;
        	Log.d(TAG, "Aguardando conexao...");
        	// TODO Esse while deve durar enquanto houver clientes para se conectar.
	        while (true) {
	            try {
	                socket = mmServerSocket.accept();
	            } catch (IOException e) {
	                break;
	            }

	            if (socket != null) {
	            	Log.d(TAG, "Conexao estabelecida");
	                try {
	                	Log.d(TAG, "Fechando o server socket...");
	                	mmServerSocket.close();
	                	Log.d(TAG, "Server socket fechado com sucesso.");
					} catch (IOException e) {
						Log.e(TAG, "Falhou enquanto fechava o server socket.", e);
					}
	                break;
	            }
	        }
	        
	        connect(socket, this.handle);
        }
        
        private void connect(BluetoothSocket socket, WoWHandle handle) {
        	ConnectedThread ct = new ConnectedThread(socket, handle);
        	ct.start();
        	String teste = "fudencio";
        	ct.write(teste.getBytes());
        }

        public void cancel() {
            try {
            	Log.d(TAG, "Cancelando o server socket...");
            	mmServerSocket.close();
            	Log.d(TAG, "Server socket cancelado com sucesso.");
            } catch (IOException e) {
                Log.e(TAG, "Servidor falhou enquanto cancelava.", e);
            }
        }
    }