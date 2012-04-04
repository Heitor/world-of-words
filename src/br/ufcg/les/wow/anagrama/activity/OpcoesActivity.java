package br.ufcg.les.wow.anagrama.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import br.ufcg.les.wow.R;
import br.ufcg.les.wow.anagrama.enummeration.Nivel;

public class OpcoesActivity extends Activity {
	
	private Nivel nivel;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_opcoes_anagrama);
        
        Button botaoOkOpcoes = (Button) findViewById(R.id.nivelok);
        botaoOkOpcoes.setOnClickListener(botaoOkOpcoesListener());
        
    }

	private OnClickListener botaoOkOpcoesListener() {
		return new OnClickListener() {
			
			public void onClick(View v) {
				getOpcaoNivelJogador();
		        mudaContexto();
			}
			
		};
	}
	
	private void mudaContexto() {
		Intent okIntent = new Intent(OpcoesActivity.this,
				AnagramaHTActivity.class);
		okIntent.putExtra("nivel", nivel);
		startActivity(okIntent);
		finish();
	}
	
	private void getOpcaoNivelJogador() {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        int opt = radioGroup.getCheckedRadioButtonId();
        
        if (opt == R.id.radio0) {
        	setNivel(Nivel.FACIL);
        } else if (opt == R.id.radio1) {
        	setNivel(Nivel.NORMAL);
        } else {
        	setNivel(Nivel.DIFICIL);
        }
	}


	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

}
