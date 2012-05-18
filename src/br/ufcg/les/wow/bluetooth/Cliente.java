package br.ufcg.les.wow.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.util.Log;
import br.ufcg.les.wow.adedonha.model.Jogador;
import br.ufcg.les.wow.bluetooth.activity.ConectandoCliente;

public class Cliente {
	private static final String TAG = "[Cliente]";
	private ThreadConectadaCliente threadConectadaCliente;
	private static Cliente thisInstance;
	
	private Cliente(ConectandoCliente conectandoClienteActivity,
			BluetoothDevice device, ManipuladorProtocolo handle) {
		this.threadConectadaCliente = new ThreadConectadaCliente(conectandoClienteActivity, device, handle);
	}
	
	public static synchronized Cliente newInstance(
			ConectandoCliente conectandoClienteActivity,
			BluetoothDevice device, ManipuladorProtocolo handle) {
		if(thisInstance == null) {
			thisInstance = new Cliente(conectandoClienteActivity, device, handle);	
		}
		return thisInstance;
	}
	
	public static synchronized Cliente instance() {
		return thisInstance;
	}
	
	public void conectar() {
		this.threadConectadaCliente.start();
		try {
			this.threadConectadaCliente.join();
			Log.d(TAG, "deu certo");
		} catch (InterruptedException e) {
			Log.e(TAG, "Falhou ao tentar fazer o join", e);
		}
	}
	
	public void enviarNome(String nome) {
		if(threadConectadaCliente == null || threadConectadaCliente.threadConectada() == null) {
			Log.e(TAG, "Nao conseguiu uma ThreadConectada.");
			return;
		}
		threadConectadaCliente.threadConectada().enviarNome(nome);
	}
	
	public void enviarJogador(Jogador jogador) {
		if(threadConectadaCliente == null || threadConectadaCliente.threadConectada() == null) {
			Log.e(TAG, "Nao conseguiu uma ThreadConectada.");
			return;
		}
		threadConectadaCliente.threadConectada().enviarJogador(jogador);
	}
}
