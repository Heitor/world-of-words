package br.ufcg.les.wow.bluetooth.client;

import java.io.IOException;
import java.util.UUID;

import br.ufcg.les.wow.bluetooth.WoWHandle;
import br.ufcg.les.wow.bluetooth.server.ConnectedThread;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    public class ConnectClientThread extends Thread {
    	private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a69");
    	private static final String TAG = "[ConnectClientThread]";
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
		private WoWHandle handle;

        public ConnectClientThread(BluetoothDevice device, WoWHandle handle) {
            mmDevice = device;
            this.handle = handle;
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
                try {
                	Log.e(TAG, "Falhou enquanto conectava-se ao server socket...", e);
                    mmSocket.close();
                    Log.e(TAG, "Fechou o socket com sucesso...");
                } catch (IOException e2) {
                	Log.e(TAG, "Falhou enquanto fechava o server socket.", e2);
                }
                return;
            }

            // Start the connected thread
            connected(mmSocket, handle);
        }
        private void connected(BluetoothSocket socket, WoWHandle handle) {
        	ConnectedThread ct = new ConnectedThread(socket, handle);
        	ct.start();
        }

        public void cancel() {
            try {
            	Log.d(TAG, "Cancelando o socket...");
                mmSocket.close();
                Log.d(TAG, "Socket cancelado com sucesso.");
            } catch (IOException e) {
            	Log.e(TAG, "falhou enquanto cancelava o socket.", e);
            }
        }
    }