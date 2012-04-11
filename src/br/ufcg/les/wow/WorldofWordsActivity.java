package br.ufcg.les.wow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.ufcg.les.wow.anagrama.activity.AnagramaHTActivity;
import br.ufcg.les.wow.client.WebServiceConnector;
import br.ufcg.les.wow.dicionario.activity.DicionarioActivity;

public class WorldofWordsActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button botaoAnagrama = (Button) findViewById(R.id.button4);
        botaoAnagrama.setOnClickListener(botaoAnagramaListener());
        
        
        // Heitor: My Tests
//        WebServiceConnector teste = new WebServiceConnector(this);
//        teste.updateDataBase();
        //User newUser = new User("nomeUser", 20, 40L, 1); 
        //teste.sendUser(newUser);
        // Heitor: End of My Tests
        
        botaoDicionarioAction();
        botaoAnagramaAction();
    }

	private void botaoDicionarioAction() {
		Button botaoDicionario = (Button) findViewById(R.id.button2);
		botaoDicionario.setOnClickListener(botaoDicionarioListener());
		
	}

	private OnClickListener botaoDicionarioListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent settingsButton = new Intent(WorldofWordsActivity.this,
						DicionarioActivity.class);
				startActivity(settingsButton);
				finish();
			}
		};
	}

	private void botaoAnagramaAction() {
		Button botaoAnagrama = (Button) findViewById(R.id.button4);
        botaoAnagrama.setOnClickListener(botaoAnagramaListener());
	}

	private OnClickListener botaoAnagramaListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent settingsButton = new Intent(WorldofWordsActivity.this,
						AnagramaHTActivity.class);
				startActivity(settingsButton);
				finish();
			}
		};
	}
}