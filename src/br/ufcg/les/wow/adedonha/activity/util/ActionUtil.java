package br.ufcg.les.wow.adedonha.activity.util;

import android.app.Activity;
import android.content.Intent;

public class ActionUtil extends Activity {
	
	public void sairDoJogo(Activity origem, Class<?> destino) {
		Intent outButton = new Intent(origem,
				destino);
		startActivity(outButton);
		finish();
	}

}
