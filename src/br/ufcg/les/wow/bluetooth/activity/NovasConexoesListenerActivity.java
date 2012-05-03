package br.ufcg.les.wow.bluetooth.activity;

import java.util.List;

import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.activity.JogoAdedonhaActivity;
import br.ufcg.les.wow.bluetooth.Protocolo;
import br.ufcg.les.wow.bluetooth.server.Servidor;
import br.ufcg.les.wow.bluetooth.server.ServidorThreadConectada;
import br.ufcg.les.wow.exceptions.DispositivoNaoEncontradoException;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NovasConexoesListenerActivity extends Activity {
	private final static String TAG = "[NovasConexoesListenerActivity]";
	private static BluetoothAdapter adaptadorBluetooth = adaptadorBluetooth();
	private Servidor sst;
	private static final int CONECTAR_DISPOSITIVO = 1;
    private static final int HABILITA_BLUETOOTH = 2;
	
	private Protocolo handle;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.conectando_server);
        
        Intent intent = getIntent();
        handle =  (Protocolo)intent.getSerializableExtra("protocolo");
        
        
        adaptadorBluetooth();
        criaServidor();
        botaoCriaServidorAction();
	}
	
	private void botaoCriaServidorAction() {
		Button botaoDicionario = (Button) findViewById(R.id.button_encerrar);
		botaoDicionario.setOnClickListener(botaoEncerrarListener());
	}
	
	private OnClickListener botaoEncerrarListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Log.d(TAG, "Clicked on encerrar.");
				encerraServidor();
				
				// TODO 
				Intent bluetoothStart = new Intent(NovasConexoesListenerActivity.this,
						JogoAdedonhaActivity.class);
				startActivity(bluetoothStart);
				finish();
			}
		};
	}
	
	public static BluetoothAdapter adaptadorBluetooth() {
		if(adaptadorBluetooth == null) {
			adaptadorBluetooth = BluetoothAdapter.getDefaultAdapter();
			if(adaptadorBluetooth == null) {
				Log.e(TAG, "Dispositivo bluetooth nao encontrado.");
				throw new DispositivoNaoEncontradoException("O dispositivo de Bluetooth nao foi encontrado");
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
	
	/*private void connect(BluetoothDevice dispositivo) {
		ConnectClientThread cct = new ConnectClientThread(dispositivo, this.handle);
		cct.start();
	}*/
	
	/*public void onActivityResult(int codigoDeRequisicao, int resultado, Intent data) {
        Log.d(TAG, "onActivityResult: " + resultado);
        switch (codigoDeRequisicao) {
        case CONECTAR_DISPOSITIVO:
            if (resultado == Activity.RESULT_OK) {
                String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                BluetoothDevice device = adaptadorBluetooth().getRemoteDevice(address);
                Log.d(TAG, "BluetoothDevice: Address -> " + device.getAddress() + " Name -> " + device.getName());
                connect(device);
            }
            break;
        }
        finish();
    }*/
	
	private void criaServidor() {
		// FIXME adaptadorBluetooth() eh singleton agora, eu posso acessar isso
		// de dentro da thread.
		this.sst = new Servidor(adaptadorBluetooth(), this.handle);
		this.sst.start();
	}
	
	private void encerraServidor() {
		Log.d(TAG, "Encerrando servidor...");
		this.sst.encerrar();
		Log.d(TAG, "Servidor encerrado com sucesso.");
		
		if(this.sst.threadsConectadas().size() == 0) {
			
		}
		
		// Teste
		List<ServidorThreadConectada> t = this.sst.threadsConectadas();
		Log.d(TAG, "Enviando Hello.");
		for(ServidorThreadConectada c : t) {
			String teste = "Hello =)";
			c.write(teste.getBytes());
		}
		
	}
	
	public boolean existeBluetooth() {
		return adaptadorBluetooth() != null;
	}
}
