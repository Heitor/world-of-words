package br.ufcg.les.wow.adedonha.activity;

import br.ufcg.les.wow.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutUsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_about_us);
		loadLink();
		
	}

	private void loadLink() {
		TextView sourceText = (TextView) findViewById(R.id.link_site);
		sourceText.setText("https://sites.google.com/site/lesadosufcg/");
		sourceText.setAutoLinkMask(0);
	}


}
