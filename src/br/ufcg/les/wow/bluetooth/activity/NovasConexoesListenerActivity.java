package br.ufcg.les.wow.bluetooth.activity;

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
import br.ufcg.les.wow.adedonha.model.ConfiguracaoParatida;
import br.ufcg.les.wow.adedonha.model.Jogador;
import br.ufcg.les.wow.bluetooth.Servidor;

public class NovasConexoesListenerActivity extends Activity {
	private final static String TAG = "[NovasConexoesListenerActivity]";
	private Servidor servidor;
	
    private static final int HABILITA_BLUETOOTH = 2;
	
	private Jogador jogador;
	private ConfiguracaoParatida configuracao;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.conectando_server);
        
        Intent intent = getIntent();
        this.configuracao = (ConfiguracaoParatida) intent.getSerializableExtra(ConfiguracaoParatida.CONFIGURACAO);
        this.jogador = (Jogador) intent.getSerializableExtra(Jogador.JOGADOR);
        
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
				Log.d(TAG, "Iniciando jogo.");
				encerraListenerNovasConexoesServidor();
				Intent bluetoothStart = new Intent(NovasConexoesListenerActivity.this, PreJogoAdedonhaActivity.class);
				
				bluetoothStart.putExtra(ConfiguracaoParatida.CONFIGURACAO, configuracao);
				bluetoothStart.putExtra(Jogador.JOGADOR, jogador);
				startActivity(bluetoothStart);
				finish();
			}
		};
	}
	
	public void habilitaBluetooth() {
		if(!Servidor.adaptadorBluetooth().isEnabled()) {
			Log.d(TAG, "Habilitando bluetooth...");
			Intent habilitaBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			
			startActivityForResult(habilitaBtIntent, HABILITA_BLUETOOTH);
			Log.d(TAG, "Bluetooth habilitado com sucesso...");
		} else {
			Log.d(TAG, "Dispositivo jah habilitado");
		}
	}
	
	public boolean existeBluetooth() {
		return Servidor.adaptadorBluetooth() != null;
	}
	
	private void criaServidor() {
		this.servidor = Servidor.newInstance();
		this.servidor.start();
	}
	
	private void encerraListenerNovasConexoesServidor() {
		Log.d(TAG, "Encerrando listener de novas conexoes com o servidor...");
		this.servidor.encerrar();
		Log.d(TAG, "Listener encerrado com sucesso.");
		if(this.servidor.threadsConectadas().size() == 0) {
			Log.e(TAG, "Nenhum jogador se uniu a partida.");
			finish();
		}
		this.servidor.enviarConfiguracoesDaPartida(this.configuracao);
	}
}
