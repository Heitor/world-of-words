package br.ufcg.les.wow;

import br.ufcg.les.wow.adedonha.activity.AdedonhaActivity;
import br.ufcg.les.wow.anagrama.activity.AnagramaHTActivity;
import br.ufcg.les.wow.client.TesteHttp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WorldofWordsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button botaoAnagrama = (Button) findViewById(R.id.button4);
        botaoAnagrama.setOnClickListener(botaoAnagramaListener());
        
        
        // Heitor: My Tests
        TesteHttp teste = new TesteHttp();
        teste.testeHttpConnection();
        botaoAdedonhaAction();
        botaoAnagramaAction();
    }

	private void botaoAdedonhaAction() {
		Button botaoAdedonha = (Button) findViewById(R.id.button1);
        botaoAdedonha.setOnClickListener(botaoAdedonhaListener());
		
	}

	private OnClickListener botaoAdedonhaListener() {
		return new OnClickListener() {

			public void onClick(View v) {
				Intent settingsButton = new Intent(WorldofWordsActivity.this,
						AdedonhaActivity.class);
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