package br.ufcg.les.wow.bluetooth.activity;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.activity.PreJogoAdedonhaActivity;
import br.ufcg.les.wow.bluetooth.Protocolo;
import br.ufcg.les.wow.bluetooth.Servidor;
import br.ufcg.les.wow.bluetooth.ThreadConectada;

public class NovasConexoesListenerActivity extends Activity {
	private final static String TAG = "[NovasConexoesListenerActivity]";
	private BluetoothAdapter adaptadorBluetooth = adaptadorBluetooth();
	private Servidor servidor;
	
    private static final int HABILITA_BLUETOOTH = 2;
	
	private Protocolo handle;
	private Serializable jogo;
	private long tempoDesejado;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.conectando_server);
        
        Intent intent = getIntent();
        handle =  (Protocolo)intent.getSerializableExtra("protocolo");
        jogo = intent.getSerializableExtra("jogo");
        tempoDesejado = intent.getLongExtra("tempoDesejado", 120000L);
        
        
        adaptadorBluetooth();
        criaServidor();
        botaoIniciarAction();
	}

	private void botaoIniciarAction() {
		Button botaoDicionario = (Button) findViewById(R.id.button_encerrar);
		botaoDicionario.setOnClickListener(botaoIniciarListener());
	}
	
	private OnClickListener botaoIniciarListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Log.d(TAG, "Clicked on encerrar.");
				encerraListenerNovasConexoesServidor();
				Intent bluetoothStart = new Intent(NovasConexoesListenerActivity.this, PreJogoAdedonhaActivity.class);
				
				bluetoothStart.putExtra("jogo", jogo);
				bluetoothStart.putExtra("tempoDesejado", tempoDesejado);
				startActivity(bluetoothStart);
				finish();
			}
		};
	}
	
	public BluetoothAdapter adaptadorBluetooth() {
		if(adaptadorBluetooth == null) {
			adaptadorBluetooth = BluetoothAdapter.getDefaultAdapter();
			if(adaptadorBluetooth == null) {
				Log.e(TAG, "Dispositivo bluetooth nao encontrado.");
				habilitaBluetooth();
			} else {
				Log.d(TAG, "Dispositivo bluetooth encontrado.");
			}
		}
		return adaptadorBluetooth;
	}
	
	public void habilitaBluetooth() {
		if(!adaptadorBluetooth().isEnabled()) {
			Log.d(TAG, "Habilitando bluetooth...");
			Intent habilitaBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			
			startActivityForResult(habilitaBtIntent, HABILITA_BLUETOOTH);
			Log.d(TAG, "Bluetooth habilitado com sucesso...");
		} else {
			Log.d(TAG, "Dispositivo jah habilitado");
		}
	}
	
	public boolean existeBluetooth() {
		return adaptadorBluetooth() != null;
	}
	
	private void criaServidor() {
		this.servidor = new Servidor(adaptadorBluetooth(), this.handle);
		this.servidor.start();
	}
	
	private void encerraListenerNovasConexoesServidor() {
		Log.d(TAG, "Encerrando listener de novas conexoes com o servidor...");
		this.servidor.encerrar();
		Log.d(TAG, "Listener encerrado com sucesso.");
		if(this.servidor.threadsConectadas().size() == 0) {
			Log.e(TAG, "Nenhum jogador se uniu a partida.");
		}
		enviarConfiguracoesDaPartida(this.servidor.threadsConectadas());
	}
	
	private void enviarConfiguracoesDaPartida(List<ThreadConectada> listenerClientThreads) {
		Log.d(TAG, "Enviando configuracoes da patida.");
		for(ThreadConectada threadConectada : listenerClientThreads) {
			threadConectada.iniciarPartida(this.jogo);
		}
	}
}
