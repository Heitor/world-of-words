package br.ufcg.les.wow.bluetooth.activity;

import br.ufcg.les.wow.R;
import br.ufcg.les.wow.adedonha.activity.PreJogoAdedonhaActivity;
import br.ufcg.les.wow.adedonha.model.ConfiguracaoParatida;
import br.ufcg.les.wow.adedonha.model.Jogador;
import br.ufcg.les.wow.bluetooth.Cliente;
import br.ufcg.les.wow.bluetooth.ThreadConectadaCliente;
import br.ufcg.les.wow.bluetooth.ManipuladorProtocolo;
import br.ufcg.les.wow.bluetooth.Servidor;
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
	private final ManipuladorProtocolo handle = ManipuladorProtocolo.instance();
	private Cliente cliente;
	private Jogador jogador;
	private Intent startGameIntent;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        this.jogador = (Jogador) intent.getSerializableExtra(Jogador.JOGADOR);
        
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.conectando_cliente);
        
        String enderecoServidor = recuperaIntent(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        Log.d(TAG, "RECUPEROU O INTENNNNNNNNNNNNNNT: " + enderecoServidor);
        
        Log.d(TAG, "Endereco escolhido: " + enderecoServidor);
        iniciaConexao(enderecoServidor);
        
        this.startGameIntent = new Intent(getApplicationContext(), PreJogoAdedonhaActivity.class);
        this.startGameIntent.putExtra(Jogador.JOGADOR, this.jogador);
        ManipuladorProtocolo.instance().setIniciarPartidaActivity(this);
        
        botaoCancelarAction();
	}
	
	private void botaoCancelarAction() {
		Button botaoCancelar = (Button) findViewById(R.id.botao_cancelar);
		botaoCancelar.setOnClickListener(botaoCancelarListener());

	}
	
	public void iniciarPartida(ConfiguracaoParatida configuracao) {
		this.startGameIntent.putExtra(ConfiguracaoParatida.CONFIGURACAO, configuracao);
		startActivity(this.startGameIntent);
		finish();
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
		BluetoothAdapter adaptadorBluetooth = Servidor.adaptadorBluetooth();
		BluetoothDevice device = adaptadorBluetooth.getRemoteDevice(endereco);
		Log.d(TAG, "BluetoothDevice: Address -> " + device.getAddress() + " Name -> " + device.getName());
		connecte(device);
	}
	
	private void connecte(BluetoothDevice device) {
		this.cliente = Cliente.newInstance(this, device, this.handle);// new ThreadConectadaCliente(this, device, this.handle);
		this.cliente.conectar();
		this.cliente.enviarNome(this.jogador.nome());
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
