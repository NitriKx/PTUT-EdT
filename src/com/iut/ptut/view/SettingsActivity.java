package com.iut.ptut.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.iut.ptut.R;
import com.iut.ptut.model.ConfigManager;

public class SettingsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.layout_parametres);
		
		Spinner spinSemestre = (Spinner) this.findViewById(R.id.spinnerSemestre);
		Spinner spinGroupe = (Spinner) this.findViewById(R.id.spinnerGroupes);

		// On met les Spinner sur la valeur contenu dans les paramètres
		ArrayAdapter semestreAdapt = (ArrayAdapter) spinSemestre.getAdapter();
		spinSemestre.setSelection(semestreAdapt.getPosition(ConfigManager.getInstance().getProperty("user_semestre")));
		ArrayAdapter groupeAdapt = (ArrayAdapter) spinGroupe.getAdapter();
		spinGroupe.setSelection(groupeAdapt.getPosition(ConfigManager.getInstance().getProperty("user_group")));
		
		// On attache un listener qui est appelé lorsque la valeur change.
		// Dès que la valeur change on l'enregistre dans les properties.
		spinSemestre.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view, int pos, long i) {
				ConfigManager.getInstance().setProperty("user_semestre", parent.getItemAtPosition(pos).toString());
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinGroupe.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view, int pos, long i) {
				ConfigManager.getInstance().setProperty("user_group", parent.getItemAtPosition(pos).toString());
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
	}
}
