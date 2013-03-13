package com.iut.ptut.view;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.iut.ptut.R;
import com.iut.ptut.model.Day;

public class TodayActivity extends Activity {
	
	Calendar cal;
	Date d;
	private Button h0800;
	private Button h0930;
	private Button h1105;
	private Button h1415;
	private Button h1540;
	private Button h1715;
	
	
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today);
		
		cal = Calendar.getInstance();
		d= new Date();
		d= cal.getTime();
		
		this.h0800 = (Button)findViewById(R.id.h0800);
		this.h0930 = (Button)findViewById(R.id.h0930);
		this.h1105 = (Button)findViewById(R.id.h1105);
		this.h1415 = (Button)findViewById(R.id.h1415);
		this.h1540 = (Button)findViewById(R.id.h1540);
		this.h1715 = (Button)findViewById(R.id.h1715);
		 
		
	} 
	protected void onStart(){
		 super.onStart();
		 getDay();
		 System.out.println("test");
	 }
	
	 
   protected void onRestart(){
  	 super.onRestart();
   }

   protected void onResume(){
  	 super.onResume();
   }

   protected void onPause(){
  	 super.onPause();
   }

   protected void onStop(){
  	 super.onStop();
   }

   protected void onDestroy(){
  	 super.onDestroy();
   }
	
	public TodayActivity getTodayActivity(){
		if (this == null){
			return (new TodayActivity());
		}
		
		return this;
	}
	
	public void getDay(){
		this.h0800.setText(" 08:00-09h30 - ACSI - 102 ");
		this.h0930.setText(" 09:30-11h05 - CN - 103 ");
		this.h1105.setText(" 11:05-12h30 - CLO - 105 ");
		this.h1415.setText(" 14:15-15h45 - CLO - 105 ");
		this.h1540.setText(" 15:45-17h15 - CLO - 105 ");
		this.h1715.setText(" 17:15-18h40 - CLO - 105 ");
	}
	
	
	/*
	//ancienne version
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
	}*/

}
