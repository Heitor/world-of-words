package br.ufcg.les.wow.bluetooth;

import java.io.Serializable;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

// TODO mudar esse nome.
public class Protocolo extends Handler implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3527073534967975662L;
	public static final int RECEBER_MENSAGEM = 1;
	public static final int ENVIAR_MENSAGEM = 2;
	private static final String TAG = "[Protocolo]";
	
	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		//msg.arg1
		case ENVIAR_MENSAGEM:
			byte[] writeBuf = (byte[]) msg.obj;
            // construct a string from the buffer
            String writeMessage = new String(writeBuf);
            Log.d(TAG, "Mensagem enviada: " + writeMessage);
			break;
		case RECEBER_MENSAGEM:
			byte[] readBuf = (byte[]) msg.obj;
            // construct a string from the valid bytes in the buffer
            String readMessage = new String(readBuf, 0, msg.arg1);
			Log.d(TAG, "Mensagem recebida: " + readMessage);
			break;
		}
	}
}
