package br.ufcg.les.wow.bluetooth.activity;

import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.activity.AdedonhaActivity;
import br.ufcg.les.wow.bluetooth.WoWHandle;
import br.ufcg.les.wow.bluetooth.client.ConnectClientThread;
import br.ufcg.les.wow.bluetooth.server.ConnectServerThread;
import br.ufcg.les.wow.exceptions.DispositivoNaoEncontradoException;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/*
 * android:theme="@android:style/Theme.Dialog"
 * android:configChanges="orientation|keyboardHidden">
 */
public class BluetoothConfiguration extends Activity {
	private final static String TAG = "[BluetoothConfiguration]";
	private static final int CONECTAR_DISPOSITIVO = 1;
    private static final int HABILITA_BLUETOOTH = 2;
	
	private static BluetoothAdapter bluetoothAdapter = null;
	
	private final WoWHandle handle = new WoWHandle();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_bluetooth);
		Log.d(TAG, "Inciando Configurações de bluetooth..");
		configBluetooth();
		
		botaoCriaServidorAction();
		botaoCriaClienteAction();
	}
	
	private void botaoCriaServidorAction() {
		Button botaoDicionario = (Button) findViewById(R.id.button_servidor);
		botaoDicionario.setOnClickListener(botaoCriaServidorListener());
	}
	
	private void botaoCriaClienteAction() {
		Button botaoDicionario = (Button) findViewById(R.id.button_cliente);
		botaoDicionario.setOnClickListener(botaoCriaClienteListener());
	}
	
	private OnClickListener botaoCriaServidorListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				criaServidor();
				Intent bluetoothStart = new Intent(BluetoothConfiguration.this,
						AdedonhaActivity.class);
				startActivity(bluetoothStart);
				finish();
			}
		};
	}
	
	private OnClickListener botaoCriaClienteListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				criaCliente();
				/*Intent bluetoothStart = new Intent(BluetoothConfiguration.this,
						AdedonhaActivity.class);
				startActivity(bluetoothStart);*/
				finish();
			}
		};
	}
	
	private void criaServidor() {
		ConnectServerThread sst = new ConnectServerThread(this.bluetoothAdapter, this.handle);
		sst.start();
	}
	
	private void criaCliente() {
		Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, CONECTAR_DISPOSITIVO);
	}
	
	private void connect(BluetoothDevice device) {
		ConnectClientThread cct = new ConnectClientThread(device, this.handle);
		cct.start();
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: " + resultCode);
        switch (requestCode) {
        case CONECTAR_DISPOSITIVO:
            if (resultCode == Activity.RESULT_OK) {
                String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                BluetoothDevice device = this.bluetoothAdapter.getRemoteDevice(address);
                Log.d(TAG, "BluetoothDevice: Address -> " + device.getAddress() + " Name -> " + device.getName());
                connect(device);
            }
            break;
        }
        finish();
    }
	
	public boolean existeBluetooth() {
		return this.bluetoothAdapter != null;
	}
	
	// **** METODOS PRIVADOS ****
	
	public static BluetoothAdapter configBluetooth() {
		if(bluetoothAdapter == null) {
			bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if(bluetoothAdapter == null) {
				Log.e(TAG, "Dispositivo bluetooth nao encontrado.");
				throw new DispositivoNaoEncontradoException("O dispositivo de Bluetooth nao foi encontrado");
			} else {
				Log.d(TAG, "Dispositivo bluetooth encontrado.");
			}
		}
		//habilitaBluetooth();
		return bluetoothAdapter;
	}
	
	private void habilitaBluetooth() {
		if(!this.bluetoothAdapter.isEnabled()) {
			Log.d(TAG, "Habilitando bluetooth...");
			Intent habilitaBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			
			startActivityForResult(habilitaBtIntent, HABILITA_BLUETOOTH);
			Log.d(TAG, "Bluetooth habilitado com sucesso...");
		} else {
			Log.d(TAG, "Dispositivo jah habilitado");
		}
	}
}
