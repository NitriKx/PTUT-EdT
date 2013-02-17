package com.iut.ptut.view;

import java.util.HashMap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.iut.ptut.R;
import com.iut.ptut.model.Day;

public class ToodayActivity extends Fragment {

	private TableLayout containerTable;
	private Day today;
	private int nbCreneaux = 7;
	public ToodayActivity() {
		// TODO Auto-generated constructor stub
		this.today = new Day();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.activity_tooday, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		containerTable = (TableLayout) getActivity().findViewById(
				R.id.containerTable);

		// Recuperation du table layout sur lequel nous allons agir
		String[] journee = getResources().getStringArray(R.array.day);

		// On va calculer la largeur des colonnes en fonction de la marge de 7
		// On affiche l'enreg dans une ligne
		TableRow tableRow = new TableRow(getActivity());
		containerTable.addView(tableRow, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		// bordure
		containerTable.setBackgroundColor(getResources()
				.getColor(R.color.black));

		// On crée une ligne de x "journee" colonnes
		tableRow.setLayoutParams(new LayoutParams(journee.length));

		// On va commencer par renseigner une ligne de titre par journe
		int i = 0;
		for (String item : journee) {
			TextView text = createTextView(false, i == journee.length - 1);
			text.setText(item);
			text.setGravity(Gravity.CENTER);
			text.setBackgroundColor(getResources().getColor(R.color.blue));
			tableRow.addView(text, i++);
		}

		for (int j = 0; j < this.nbCreneaux; j++) {
			tableRow = new TableRow(getActivity());
			containerTable.addView(tableRow, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
			TextView textHeure = createTextView(j == 6, i == journee.length - 1);
			TextView textMat = createTextView(j == 6, i == journee.length - 1);
			TextView textSalle = createTextView(j == 6, i == journee.length - 1);
			
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			
			map.put("heure", 0);
			map.put("matiere", 1);
			map.put("salle", 2);
			//this.today.getDateDebut().getHours() remplacer par l'outil de benoit
			String heure = new String();
			
			//heure =
			textHeure.setText("");
			textMat.setText(this.today.getListLesson().elementAt(j).getLibelle());
			textSalle.setText(this.today.getListLesson().elementAt(j).getEmplacement());
			
			textHeure.setGravity(Gravity.RIGHT);
			textMat.setGravity(Gravity.RIGHT);
			textSalle.setGravity(Gravity.RIGHT);
			
			tableRow.addView(textHeure, map.get("heure"));
			tableRow.addView(textMat,  map.get("matiere"));
			tableRow.addView(textSalle,  map.get("salle"));
		}

	}

	private TextView createTextView(boolean endline, boolean endcolumn) {
		TextView text = new TextView(getActivity(), null,
				R.style.frag3HeaderCol);
		int bottom = endline ? 1 : 0;
		int right = endcolumn ? 1 : 0;
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, 0.3f);
		params.setMargins(1, 1, right, bottom);
		text.setLayoutParams(params);
		text.setPadding(4, 4, 10, 4);
		text.setBackgroundColor(getResources().getColor(R.color.white));
		return text;
	}

}
