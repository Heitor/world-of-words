package br.ufcg.les.wow.bluetooth.activity;

import br.ufcg.les.wow.R;
import br.ufcg.les.wow.bluetooth.Cliente;
import br.ufcg.les.wow.bluetooth.Protocolo;
import br.ufcg.les.wow.bluetooth.ThreadConectada;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ConectandoCliente extends Activity  {
	private final static String TAG = "[ConectandoCliente]";
	private final Protocolo handle = new Protocolo();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.conectando_cliente);
        
        String enderecoServidor = recuperaIntent(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        Log.d(TAG, "RECUPEROU O INTENNNNNNNNNNNNNNT: " + enderecoServidor);
        
        Log.d(TAG, "Endereco escolhido: " + enderecoServidor);
        iniciaConexao(enderecoServidor);
        
        botaoCancelarAction();
	}
	
	private void botaoCancelarAction() {
		Button botaoCancelar = (Button) findViewById(R.id.botao_cancelar);
		botaoCancelar.setOnClickListener(botaoCancelarListener());

	}
	
	private OnClickListener botaoCancelarListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent cancelarIntent = new Intent(ConectandoCliente.this,
						ConfiguraClienteActivity.class);
				startActivity(cancelarIntent);
				finish();
			}
		};
	}
	
	private String recuperaIntent(String chave) {
		Intent intent = getIntent();
		return (String)intent.getSerializableExtra(chave);
	}
	
	private void iniciaConexao(String endereco) {
		NovasConexoesListenerActivity teste = new NovasConexoesListenerActivity();
		BluetoothAdapter adaptadorBluetooth = teste.adaptadorBluetooth();
		BluetoothDevice device = adaptadorBluetooth.getRemoteDevice(endereco);
		Log.d(TAG, "BluetoothDevice: Address -> " + device.getAddress() + " Name -> " + device.getName());
		connecte(device);
	}
	
	private void connecte(BluetoothDevice device) {
		Cliente cct = new Cliente(this, device, this.handle);
		cct.start();
		try {
			cct.join();
			Log.d(TAG, "deu certo");
		} catch (InterruptedException e) {
			Log.e(TAG, "Falhou ao tentar fazer o join", e);
		}
		
		ThreadConectada f = cct.threadConectada();
		String teste = "my_name";
		if(f != null) {
			f.enviar(teste.getBytes());
			Log.d(TAG, "CERINNNN = " + teste);
		} else {
			Log.e(TAG, "Nao conseguiu pegar uma thread conectada.");
		}
		
	}
	
	/*
	 * FIXME Esse metodo deve ser chamado em caso de erro ao tentar conectar.
	 * Ele devia mostrar uma telinha dizendo que deu merda.
	 */
	public void cancela() {
		Log.e(TAG, "Deu erro, e cancela foi chamado com sucesso.");
		Intent cancelarIntent = new Intent(ConectandoCliente.this,
				ConfiguraClienteActivity.class);
		startActivity(cancelarIntent);
		Log.e(TAG, "Deu erro, e cancela foi chamado com sucesso. lol");
		finish();
	}
	
}
