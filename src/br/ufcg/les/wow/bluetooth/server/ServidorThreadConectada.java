package br.ufcg.les.wow.bluetooth.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.ufcg.les.wow.bluetooth.Protocolo;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

/**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    public class ServidorThreadConectada extends Thread {
    	private static final String TAG = "[ThreadConectada]";
        private final BluetoothSocket socketConectado;
        private final InputStream streamDeEntrada;
        private final OutputStream streamDeSaida;
        private final Protocolo protocolo;

        public ServidorThreadConectada(BluetoothSocket socketConectado, Protocolo protocolo) {
            Log.d(TAG, "Thread conectada criada.");
            this.socketConectado = socketConectado;
            this.protocolo = protocolo;
            InputStream streamDeEntrada = null;
            OutputStream streamDeSaida = null;

            // Get the BluetoothSocket input and output streams
            if(socketConectado == null) {
            	Log.e(TAG, "Socket is null");
            }
            try {
                streamDeEntrada = socketConectado.getInputStream();
                streamDeSaida = socketConectado.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            this.streamDeEntrada = streamDeEntrada;
            this.streamDeSaida = streamDeSaida;
        }
        
        public BluetoothSocket socketConectado() {
        	return this.socketConectado;
        }

        public void run() {
            Log.i(TAG, "Iniciando listener...");
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = streamDeEntrada.read(buffer);

                    this.protocolo.obtainMessage(Protocolo.RECEBER_MENSAGEM, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                	Log.e(TAG, "Thread disconectada.", e);
                    break;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                streamDeSaida.write(buffer);

                this.protocolo.obtainMessage(Protocolo.ENVIAR_MENSAGEM, -1, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                socketConectado.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }