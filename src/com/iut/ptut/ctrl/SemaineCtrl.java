package com.iut.ptut.ctrl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.iut.ptut.model.ConfigManager;
import com.iut.ptut.model.Day;
import com.iut.ptut.model.Group;
import com.iut.ptut.model.TimeTable;
import com.iut.ptut.model.database.DatabaseManager;

public class SemaineCtrl {
	
	/**
	 * Récupère la liste des jours disponibles dans la semaine courrante et celle qui la suit
	 * @return La liste des jours
	 * @throws ParseException 
	 */
	public static List<Date> getListeJourDisponible() throws ParseException {
		
		List<Date> res = new ArrayList<Date>();
		
		DatabaseManager bdd = DatabaseManager.getInstance();
		bdd.open();
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		int semaine = cal.get(Calendar.WEEK_OF_YEAR);
		int semestre = Integer.parseInt(ConfigManager.getInstance().getProperty("user_semestre"));
		int anneeScolaire = CalendarParser.getAnneeScolairePourDate(cal.getTime());
		Group groupe = new Group(
				ConfigManager.getInstance().getProperty("user_group"),
				semestre,anneeScolaire
				);
		
		// On récupère le timetable courrant
		TimeTable semaineCourante = bdd.getTimeTable(groupe, semaine, cal.get(Calendar.YEAR));
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		// Puis celui de la semaine prochaine
		TimeTable semaineSuivante = bdd.getTimeTable(groupe, cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR));
		
		// Si l'on a la semaine courrante, on ajoute les jour dans la liste
		if(semaineCourante != null) {
			for(Day d : semaineCourante.getListJour()) {
				res.add(d.getDateDebut());
			}
		}
		
		// Si on a la semaine suivante, on ajoute les jour dans la liste
		if(semaineSuivante != null) {
			for(Day d : semaineSuivante.getListJour()) {
				res.add(d.getDateDebut());
			}
		}
		
		return res;
	}
	
	
	
}
