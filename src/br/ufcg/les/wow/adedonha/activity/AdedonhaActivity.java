package br.ufcg.les.wow.adedonha.activity;

import java.sql.SQLException;

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
import br.ufcg.les.wow.adedonha.model.Jogador;
import br.ufcg.les.wow.bluetooth.Cliente;
import br.ufcg.les.wow.bluetooth.ManipuladorProtocolo;
import br.ufcg.les.wow.bluetooth.Servidor;
import br.ufcg.les.wow.bluetooth.activity.ConfiguracoesDoJogoActivity;
import br.ufcg.les.wow.persistence.dao.FactoryDao;
import br.ufcg.les.wow.persistence.dao.RankingDAO;
import br.ufcg.les.wow.persistence.dao.UsuarioDAO;

public class AdedonhaActivity extends Activity {
	
	private static final String TAG = "AdedonhaActivity";
	private static final boolean D = true;
	private static final int REQUEST_ENABLE_BT = 0;
	private RankingDAO rankingDAO = FactoryDao.getRankingDaoInstance();
	private UsuarioDAO usuarioDAO = new UsuarioDAO(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		setContentView(R.layout.main_adedonha);
		
		activateBluetooth();
		
		botaoJogarAction();
		botaoSairAction();
		loadRankingButton();
		loadHelpButton();
	}

	private void activateBluetooth() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Intent usuarioIntent = getIntent();
        Jogador usuario = (Jogador) usuarioIntent.getSerializableExtra("jogador");
        
        if (usuario != null) {
        	if(rankingDAO.addJogador(usuario)) {
        		try {
    				usuarioDAO.open();
    				usuarioDAO.inserirObjeto(usuario.getNome(), usuario.getPontuacao(),
    						0);
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			usuarioDAO.close();
        	}
        }
	}
	
	private void clearBluetooth() {
		ManipuladorProtocolo.newInstance();
		if(Servidor.instance() != null) {
			Log.d(TAG, "cancelando servidor...");
			Servidor.instance().cancelar();
		}
		if(Cliente.instance() != null) {
			Log.d(TAG, "cancelando cliente...");
			Cliente.instance().cancelar();
		}
	}
	
	 private void loadHelpButton() {
		 Button helpButton = (Button) findViewById(R.id.ajuda_adedonha);
		 helpButton.setOnClickListener(helpButtonListener());
		
	}

	private OnClickListener helpButtonListener() {
		return new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent rankingIntent = new Intent(AdedonhaActivity.this, HelpActivity.class);
				startActivity(rankingIntent);
			}
		};
	}

	private void loadRankingButton() {
		 Button rankingButton = (Button) findViewById(R.id.ranking_adedonha);
		 rankingButton.setOnClickListener(rankingButtonListener());
	}

	private OnClickListener rankingButtonListener() {
		return new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent rankingIntent = new Intent(AdedonhaActivity.this, RankingActivity.class);
				rankingIntent.putExtra("rankingDao", rankingDAO);
				startActivity(rankingIntent);
			}
		};
	}

	@Override
	    public void onStart() {
	        super.onStart();
	        if(D) Log.e(TAG, "++ ON START ++");
	        clearBluetooth();
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
				finish();
			}
		};
	}

}
