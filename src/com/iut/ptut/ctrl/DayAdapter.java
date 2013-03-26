package com.iut.ptut.ctrl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.iut.ptut.model.Day;
import com.iut.ptut.model.Group;
import com.iut.ptut.model.Lesson;
import com.iut.ptut.model.database.DatabaseManager;
import com.iut.ptut.model.database.DatabaseManipulationException;

/**
 * Cette classe permet de retourner une liste de lesson, en fonction du jour courant.
 * @author Hugz2 & NitriKx
 *
 */
public class DayAdapter {
	
	public static List<Lesson> getLesson() throws DatabaseManipulationException {
		
		// On r�cup�re l'instance unique de la base de donn�es 
		DatabaseManager bdd = DatabaseManager.getInstance();
		
		// On ouvre la base
		bdd.open();
		
		// On r�cup�re l'instance de Calendar (au jour courant par d�faut)
		Calendar cal = Calendar.getInstance();
		
		// cal.getTime() -> R�cup�e la date courrante
		
		// On met le calendrier au tout d�but du jour (1 javier 2013 00:00.000)
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		// On sauvegarde le timestamp du d�but du jour
		Date debut = cal.getTime();
		
		// On fait pareil pour la fin du jour
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		
		Date fin = cal.getTime();
		//List<Lesson> coursJour;
		
		/*code de test, A SUPPRIMER APRES //A DECOMMENTER QUAND LA BDD SERA OK!*/
		Lesson less = new Lesson(1, "CN", "JMB", "101", new Date(), new Date(), 1, new Group());
		List<Lesson> coursJour = new ArrayList<Lesson>();
		coursJour.add(less);
		coursJour.add(less);
		coursJour.add(less);
		coursJour.add(less);
		coursJour.add(less);
		coursJour.add(less);
		/*FIN de code de test.*/
		
		
		// On r�cup�re dans la base le lessons pour le jour
		//A DECOMMENTER QUAND LA BDD SERA OK!
		//coursJour = bdd.getListeLessonPourPeriode(debut, fin); 
		bdd.close();
			
		return coursJour;
	}

}
