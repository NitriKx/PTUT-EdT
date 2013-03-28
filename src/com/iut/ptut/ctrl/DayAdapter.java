package com.iut.ptut.ctrl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.fortuna.ical4j.data.ParserException;
import com.iut.ptut.model.Lesson;
import com.iut.ptut.model.database.DatabaseManager;
import com.iut.ptut.model.database.DatabaseManipulationException;
/**
 * Cette classe permet de retourner une liste de lesson, en fonction du jour courant.
 * @author Hugz2 & Ben
 *
 */
public class DayAdapter {
	
	public static List<Lesson> getLessonPourDay(Date dateDansJour) throws DatabaseManipulationException {
		
		// On r�cup�re l'instance unique de la base de donn�es 
		DatabaseManager bdd = DatabaseManager.getInstance();
		
		// On ouvre la base
		bdd.open();

		// On r�cup�re l'instance de Calendar (au jour courant par d�faut)
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateDansJour);
		
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
		
		List<Lesson> coursJour;
		// On r�cup�re dans la base le lessons pour le jour
		coursJour = bdd.getListeLessonPourPeriode(debut, fin); 
		bdd.close();
			
		return coursJour;
	}
	
	public static List<Lesson> getLessonJourCourant() throws DatabaseManipulationException, MalformedURLException, IOException, ParserException {
		return DayAdapter.getLessonPourDay(new Date());
	}

}
