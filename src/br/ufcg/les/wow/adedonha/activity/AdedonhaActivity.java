package br.ufcg.les.wow.adedonha.activity;

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
import br.ufcg.les.wow.bluetooth.activity.ConfiguracoesDoJogoActivity;

public class AdedonhaActivity extends Activity {
	
	private BluetoothAdapter mBluetoothAdapter = null;
	private static final String TAG = "AdedonhaActivity";
	private static final boolean D = true;

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;
	//private BluetoothChatService mChatService = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		setContentView(R.layout.main_adedonha);
		
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.bluetooth);
		
		 // Get local Bluetooth adapter
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//        // If the adapter is null, then Bluetooth is not supported
//        if (mBluetoothAdapter == null) {
//            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
//            finish();
//            return;
//        }
		
		
		botaoJogarAction();
		botaoSairAction();
		
//		AdedonhaDAOImpl adedonhaDao = new AdedonhaDAOImpl(getApplicationContext());
//		try {
//			adedonhaDao.open();
//			if (adedonhaDao.listarObjetos().size() == 0) {
//				List<Palavra> palavras = GeradorStrings.povoaBanco();
//				adedonhaDao.inserirObjeto(palavras);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		adedonhaDao.close();

	}
	
	 @Override
	    public void onStart() {
	        super.onStart();
	        if(D) Log.e(TAG, "++ ON START ++");

	        // If BT is not on, request that it be enabled.
	        // setupChat() will then be called during onActivityResult
//	        if (!mBluetoothAdapter.isEnabled()) {
//	            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//	            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//	        }
	        
	 }
	        // Otherwise, setup the chat session
//	        } else {
//	            if (mChatService == null) setupChat();
//	        }
	   

	private void botaoJogarAction() {
		Button botaoJogar = (Button) findViewById(R.id.jogar_adedonha);
		//botaoJogar.setOnClickListener(listenerTrocarTela(SubMenuJogarAdedonhaActivity.class));
		botaoJogar.setOnClickListener(listenerTrocarTela(ConfiguracoesDoJogoActivity.class));
		
	}

	private void botaoSairAction() {
		Button botaoSair = (Button) findViewById(R.id.sair_adedonha);
		botaoSair.setOnClickListener(listenerSairJogo());

	}
	
	private OnClickListener listenerSairJogo() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
		};
	}

	
	private OnClickListener listenerTrocarTela(final Class<?> telaDestino) {
		return new OnClickListener() {
			
			public void onClick(View v) {
				Intent outButton = new Intent(AdedonhaActivity.this,
						telaDestino);
				startActivity(outButton);
				//finish();
			}
		};
	}

}
