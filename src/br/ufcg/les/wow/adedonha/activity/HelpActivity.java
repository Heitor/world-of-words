package br.ufcg.les.wow.adedonha.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import br.ufcg.les.wow.R;

public class HelpActivity extends TabActivity {
	
	private TabHost tabHost;
	private TabHost.TabSpec spec;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		tabHost = getTabHost();
		
		loadTabs();
	}
	
	private void loadTabs() {
		aboutTheGame();
		aboutUs();
	}

	private void aboutTheGame() {
		intent = new Intent().setClass(this, AboutTheGameActivity.class);
		spec = tabHost.newTabSpec("about_the_game").setIndicator("Sobre o Jogo")
	              .setContent(intent);
		tabHost.addTab(spec);
		
	}

	private void aboutUs() {
		intent = new Intent().setClass(this, AboutUsActivity.class);
		spec = tabHost.newTabSpec("about_us").setIndicator("Sobre os desenvolvedores")
	              .setContent(intent);
		tabHost.addTab(spec);
		
	}

}
