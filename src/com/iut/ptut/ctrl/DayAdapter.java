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
 * @author Hugz2 & NitriKx
 *
 */
public class DayAdapter {
	
	
	public static List<Lesson> getLesson() throws DatabaseManipulationException, MalformedURLException, IOException, ParserException {
		
		// On récupère l'instance unique de la base de données 
		DatabaseManager bdd = DatabaseManager.getInstance();
		
		// On ouvre la base
		bdd.open();
		
		// On récupère l'instance de Calendar (au jour courant par défaut)
		Calendar cal = Calendar.getInstance();
		
		// cal.getTime() -> Récupère la date courrante
		
		// On met le calendrier au tout début du jour (1 javier 2013 00:00.000)
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		// On sauvegarde le timestamp du début du jour
		Date debut = cal.getTime();
		
		// On fait pareil pour la fin du jour
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		
		Date fin = cal.getTime();
		List<Lesson> coursJour;
		/*code de test, A SUPPRIMER APRES 
		Lesson less = new Lesson(1, "CN", "JMB", "101", new Date(), new Date(), 1, new Group());
		List<Lesson> coursJour = new ArrayList<Lesson>();
		coursJour.add(less);
		coursJour.add(less);
		coursJour.add(less);
		coursJour.add(less);
		coursJour.add(less);
		coursJour.add(less);
		/*FIN de code de test.*/
		
		
		// On récupère dans la base le lessons pour le jour
		coursJour = bdd.getListeLessonPourPeriode(debut, fin); 
		bdd.close();
			
		return coursJour;
	}

}
