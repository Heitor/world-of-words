package br.ufcg.les.wow.bluetooth.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

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
                    buffer = new byte[1024];
                } catch (IOException e) {
                	Log.e(TAG, "Thread disconectada.", e);
                    break;
                }
            }
        }
        
        private byte[] serialize(Object obj) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				ObjectOutputStream os = new ObjectOutputStream(out);
				os.writeObject(obj);
			} catch (IOException e) {
				Log.e(TAG, "Falhou tentando serializar um objeto", e);
			}
            return out.toByteArray();
        }
        
        private byte[] cabecalhoOperacao(byte[] buffer) {
        	String operationInfo = Protocolo.CABECALHO_CONFIGURACOES_DA_PARTIDA+buffer.length;
    		return operationInfo.getBytes();
        }
        
        public void iniciarPartida(Serializable tempo) {
        		byte[] buffer = serialize(tempo);
        		byte[] bufferOperation = cabecalhoOperacao(buffer);
        		enviar(bufferOperation);
        		enviar(buffer);
        		enviar(bufferOperation);
        		enviar(buffer);
        }


        public void enviar(byte[] buffer) {
            try {
                streamDeSaida.write(buffer);
                this.protocolo.obtainMessage(Protocolo.ENVIAR_MENSAGEM, -1, -1, buffer).sendToTarget();
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