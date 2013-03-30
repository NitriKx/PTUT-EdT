package com.iut.ptut.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.iut.ptut.R;
import com.iut.ptut.ctrl.DayAdapter;
import com.iut.ptut.model.Lesson;

/**
 * 
 * @author Hugo Roman, Benoît Sauvère
 *
 */
public class TodayFragment extends Fragment {
	
	private List<Lesson> MyListLesson;
	private Logger _log = Logger.getLogger(this.getClass().getName());
	private View vue;
	private Date date;
	private boolean retourListeJours = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		vue = inflater.inflate(R.layout.activity_today, container, false);
		// On initialise la date avec la date actuelle
		this.date = new Date();
		// On vérifie si l'utilisateur a passé la date à afficher
		if(this.getArguments() != null) {
			if(this.getArguments().getLong("date") != 0L) {
				this.date = new Date(this.getArguments().getLong("date"));
			}
			
			// Si on veut revenir sur la page "semaine" en appuyant sur Back
			if(this.getArguments().getBoolean("needBack")) {
				this.retourListeJours = true;
			}
		} 
		
		this.remplirVuePourDate(this.date);
		return vue;
	}

	/**
	 * Cette méthode vide et remplis le fragment avec les données pour la date désirée.
	 * @param dateDansJour La date dans le jour dont on veut les cours
	 */
	@SuppressLint("DefaultLocale")
	private void remplirVuePourDate(Date dateDansJour) {
		
		// Format de la date au dessus du tableau
		SimpleDateFormat formatDateTableau = new SimpleDateFormat("EEEE dd/MM/yyyy", Locale.getDefault());
		// Format des heures pour les cours
		SimpleDateFormat formatHeureLesson = new SimpleDateFormat("HH:mm", Locale.getDefault());
		
		try {
			// On récupère la liste des lessons
			MyListLesson = DayAdapter.getLessonPourDay(dateDansJour);
			
			// Si il n'y a pas de Lesson
			if(MyListLesson.size() <= 0) {
				_log.log(Level.WARNING, "Pas de Lesson trouvé pendant le chargement du jour actuel !");
			}
			
			// On récupère le TableLayout du jour actuel
			TableLayout tbl = (TableLayout) vue.findViewById(R.id.tableau_today);
			// On le vide
			tbl.removeAllViews();
			// On lui (re)met le fond
			tbl.setBackgroundResource(R.drawable.bordure_tableau);
			
			// On créer une ligne contenant la date 
			// On est obligé de manipuler la première lettre pour la mettre en majuscule
			String formattedDate = formatDateTableau.format(dateDansJour);
			String texteDate = ("" + formattedDate.charAt(0)).toUpperCase(Locale.getDefault()) 
					+ formattedDate.substring(1, formattedDate.length());
			
			TableRow rowTitreTableau = new TableRow(vue.getContext());
			tbl.addView(rowTitreTableau);
			TextView texteDateTableau = new TextView(vue.getContext());
			texteDateTableau.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
			texteDateTableau.setText(texteDate);
			TableRow.LayoutParams p = (TableRow.LayoutParams) texteDateTableau.getLayoutParams();
			if(p == null) {
				p = new TableRow.LayoutParams();
			}
				
			p.span = 3;
			texteDateTableau.setLayoutParams(p);
			rowTitreTableau.addView(texteDateTableau);
			
			// ====== LIGNE DES TITRES ======
			
			// Le paramètre pour espacer les colonnes
			int espaceCol = 5;
			p = new android.widget.TableRow.LayoutParams();
			p.rightMargin = DisplayHelper.dpToPixel(espaceCol, MainActivity.context); 
			p.leftMargin = DisplayHelper.dpToPixel(espaceCol, MainActivity.context); 
			
			TableRow rowTitre = new TableRow(vue.getContext());
			String[] titres = new String[] {"Heures", "Matière", "Salle"};
			// On construit la ligne de titre
			for(String t : titres) {
				TextView tv = new TextView(vue.getContext());
				
				tv.setLayoutParams(p);
				tv.setText(t);
				// En GRAAAS
				tv.setTypeface(null, Typeface.BOLD);
				tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
				rowTitre.addView(tv); 
			}
			tbl.addView(rowTitre);
			
			// Pour chaque Lesson on créer une ligne
			for(Lesson l : MyListLesson) {
				TableRow lessonRow = new TableRow(vue.getContext());
				// On créer la case avec les heures
				TextView viewHoraire = new TextView(vue.getContext());
				viewHoraire.setLayoutParams(p);
				viewHoraire.setText(formatHeureLesson.format(l.getDateDebut()) + " - " + formatHeureLesson.format(l.getDateFin()));
				viewHoraire.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
				lessonRow.addView(viewHoraire); 
				
				// On créer la case avec le libelle de la matière
				TextView viewMatiere = new TextView(vue.getContext());
				viewMatiere.setLayoutParams(p);
				viewMatiere.setText(l.getLibelle());
				viewMatiere.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
				lessonRow.addView(viewMatiere); 
				
				// On créer la case avec l'emplacement du cour
				TextView viewSalle = new TextView(vue.getContext());
				viewSalle.setLayoutParams(p);
				viewSalle.setText(l.getEmplacement());
				viewSalle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
				lessonRow.addView(viewSalle); 
				
				// On ajoute la ligne au tableau
				tbl.addView(lessonRow);
			}
			
		} catch (Exception e) {
			_log.log(Level.SEVERE, "Impossible de charger la liste des cours pour le jour actuel. " +
					"Nom = [" + e.getClass() + "] Cause = [" + e.getCause() + "] Message = [" + e.getMessage() + "]");
			
			Toast.makeText(MainActivity.context, "Impossible de charger la liste des cours :/", Toast.LENGTH_SHORT).show();
		}
	}

	public boolean isRetourListeJours() {
		return retourListeJours;
	}

}
