package com.iut.ptut.ctrl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;

import com.iut.ptut.model.Day;
import com.iut.ptut.model.Group;
import com.iut.ptut.model.Lesson;
import com.iut.ptut.model.ParsingProblemException;
import com.iut.ptut.model.TimeTable;

/**
 * Cette classe regroupe les méthodes permettant de parser les fichiers ICS de l'IUT.
 * @author Benoît Sauvère
 */
public class CalendarParser {

	
	private static Logger _log = Logger.getLogger(CalendarParser.class.getName());
	
	
	
	/**
	 * @param args
	 * @throws ParserException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ParserException {
		System.out.println(CalendarParser.getTimeTableDepuisFichierICS("S4_08.ics", new Group("3B", 4, 2012) ));
	}
	
	private CalendarParser() {
	}
	
	/**
	 * Créer un objet TimeTable pour le groupe donné avec les informations contenues dans le fichier ICS.
	 * @param cheminFichierICS Le chemin vers le fichier ICS.
	 * @param groupe Un objet Group
	 * @return Une objet TimeTable remplis
	 * @throws ParserException 
	 * @throws IOException 
	 */
	public static TimeTable getTimeTableDepuisFichierICS(String cheminFichierICS, Group groupe) throws IOException, ParserException {
		FileInputStream fis = new FileInputStream(new File(cheminFichierICS));
		TimeTable r = CalendarParser.convertirCalendarEnTimeTable(CalendarParser.convertirICSEnICal4J(fis), groupe);
		fis.close();
		return r;
	}
	
	/**
	 * Créer un objet TimeTable pour le groupe donné avec les informations contenues dans le fichier ICS.
	 * @param cheminFichierICS Le chemin vers le fichier ICS.
	 * @param groupe Un objet Group
	 * @return Une objet TimeTable remplis
	 * @throws ParserException 
	 * @throws IOException 
	 */
	public static TimeTable getTimeTableDepuisICS(InputStream is, Group groupe) throws IOException, ParserException {
		BufferedInputStream bis = new BufferedInputStream(is);
		TimeTable r = CalendarParser.convertirCalendarEnTimeTable(CalendarParser.convertirICSEnICal4J(bis), groupe);
		bis.close();
		return r;
	}
	
	/**
	 * Convertit un Calendar de type "iCal4j" en TimeTable de notre application.
	 * Ce TimeTable contientra tout les cours pour tout les groupes.
	 * @param cal Un calendar "iCal4j"
	 * @return Un TimeTable avec toute les données du calendar, pour tout les groupes.
	 */
	@SuppressWarnings("unused")
	private static TimeTable convertirCalendarEnTimeTable(Calendar cal) {
		return CalendarParser.convertirCalendarEnTimeTable(cal, new Group());
	}
	
	/**
	 * Convertit un Calendar de type "iCal4j" en TimeTable de notre application.
	 * Ce TimeTable contientra tout les cours pour tout les groupes.
	 * @param cal Un calendar "iCal4j"
	 * @param groupe Le groupe pour lequel on veut l'emploi du temps. Si la chaine est vide, on prendre tout les cours de tout les groupes.
	 * Il peut être de la forme : "2", "2B", "A" (cours communs). 
	 * @return Un TimeTable avec les données du calendar
	 */
	@SuppressWarnings("rawtypes")
	private static TimeTable convertirCalendarEnTimeTable(Calendar cal, Group groupe) {
		
		TimeTable tt = new TimeTable();
		tt.setGroupe(groupe);
		// Une map associant le timestamp du début du jour à l'objet du jour correspondant
		HashMap<Long, Day> mapJours = new HashMap<Long, Day>();
		
		// On parcourt le ICS "lu"
		for (Iterator i = cal.getComponents().iterator(); i.hasNext();) {
		    Component component = (Component) i.next();
		    
		    // Si c'est un cours
		    if("VEVENT".equals(component.getName())) {
		    	
		    	try{ 
		    		Lesson cour = new Lesson(component);

		    		// Si le cours appartient au groupe choisis
		    		if(cour.estDansLeGroupe(groupe)) {
			    		long debutJourCours = DateTools.calculeTimestampDebutJour(cour.getDateDebut());
			    		Day jour = mapJours.get(debutJourCours);
			    		
			    		// Si le jour n'a pas encore été crée, on l'ajoute à la map
			    		if(jour == null){
			    			jour = new Day();
			    			mapJours.put(debutJourCours, jour);
			    		}
			    		// On ajoute le cours au jour
			    		jour.addLesson(cour);
		    		}
		    		
		    	} catch (Exception e) {
		    		_log.log(Level.SEVERE, "Impossible de convertir le VEVENT en Lesson.");
		    		e.printStackTrace();
		    	}
		    }
		}
		
		// Une fois tout les cours lu, on ajoute les jours à l'emploi du temps
		for(long key : mapJours.keySet()) {
			tt.addDay(mapJours.get(key));
		}
		
		return tt;
	}
	
	
	
	/**
	 * Parse un fichier ICS en un objet de type Calendar de le librairie iCal4J
	 * @param cheminFichierICS Le chemin vers le ficheir ICS
	 * @return Un objet de type net.fortuna.ical4j.model.Calendar
	 * @throws IOException
	 * @throws ParserException
	 */
	private static Calendar convertirICSEnICal4J(InputStream is) throws IOException, ParserException {
		// On le fait parser par iCal4J
		CalendarBuilder builder = new CalendarBuilder();
		Calendar result = builder.build(is);
		return result;
	}
	
	

	/**
	 * Lit le contenu du summary pour en recupérer les informations.
	 * @param summaryFromICS le contenu d'une ligne de fichier ICS<br/> Format : "formation specialite/semestre matiere type groupe -- intervenant"
	 * @return HashMap contenant les entrées :<br/>
	 * <ul>
	 * 	<li>formation</li>
	 * 	<li>specialite</li>
	 * 	<li>semestre</li>
	 * 	<li>matiere</li>
	 * 	<li>intervenant</li>
	 * 	<li>type (TD ou TP) (sauf cours amphi)</li>
	 * 	<li>groupe (ex : 2 ou 2A) (sauf cours amphi)</li>
	 * </ul>
	 */
	public static HashMap<String, String> parseSummary(String summaryFromICS) throws ParsingProblemException {
		
		HashMap<String, String> resultat = new HashMap<String, String>();
		String[] split = summaryFromICS.split(" ");
		
		// Première vérification de format :
		if(split.length != 8 && split.length != 6) {
			throw new ParsingProblemException("Le summary ne contient pas le bon nombre d'espaces.");
		}
		
		// Si le cours est un amphi
		if(split.length == 6) {
			// On lit les différentes informations
			resultat.put("formation", split[0]);
			resultat.put("matiere", split[2]);
			resultat.put("intervenant", split[5]);
			
			// SPECIAL - On sépare la spécialité du semestre (un /)
			String[] splitPartie2 = split[1].split("/");
			if(splitPartie2.length != 2) {
				throw new ParsingProblemException("Le deuxième bloc du summary est malformé.");
			}
			resultat.put("specialite", splitPartie2[0]);
			resultat.put("semestre", splitPartie2[1]);
		
		// Si c'est un cours normal
		} else {
		
			// On lit les différentes informations
			resultat.put("formation", split[0]);
			resultat.put("matiere", split[2]);
			resultat.put("type", split[3]);
			resultat.put("groupe", split[5]);
			resultat.put("intervenant", split[7]);
	
			
			// SPECIAL - On sépare la spécialité du semestre (un /)
			String[] splitPartie2 = split[1].split("/");
			if(splitPartie2.length != 2) {
				throw new ParsingProblemException("Le deuxième bloc du summary est malformé. Summary=[" + summaryFromICS+"]");
			}
			resultat.put("specialite", splitPartie2[0]);
			resultat.put("semestre", splitPartie2[1]);
		}
		
		// On vérifie qu'il n'y ai pas de valeurs vide
		for(String key : resultat.keySet()) {
			if(resultat.get(key).equals("")) {
				throw new ParsingProblemException("Une des valeurs est vide.");
			}
		}
		return resultat;
	}
	
	public static int getAnneeScolairePourDate(Date date) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Calendar calJuillet = java.util.Calendar.getInstance();

		cal.setTime(date);
		
		// On créer un calendrier au 1er juillet
		calJuillet.setTime(date);
		calJuillet.set(java.util.Calendar.MONTH, java.util.Calendar.JULY);
		calJuillet.set(java.util.Calendar.DAY_OF_MONTH, 1);
		calJuillet.set(java.util.Calendar.HOUR, 0);
		calJuillet.set(java.util.Calendar.MINUTE, 0);
		calJuillet.set(java.util.Calendar.SECOND, 0);
		calJuillet.set(java.util.Calendar.MILLISECOND, 0);

		// Si la date est avant Juillet on considère que c'est l'année scolaire suivante
		if(cal.before(calJuillet)) {
			return cal.get(java.util.Calendar.YEAR)-1;
		} 
		
		// Sinon on est à la bonne année
		return cal.get(java.util.Calendar.YEAR);
	}
	
	/**
	 * Filtre une liste de Lesson pour en extraire les cours d'un groupe de TP
	 * @param lessons La liste de lessons à filtrer
	 * @param groupeTP Le groupe de TP. Le numéro de sous groupe peut-être omis. Le groupe "A" désigne seulement les cours communs à tous.
	 * @return La liste des cours filtrés.
	 */
	public static List<Lesson> filterCoursGroupeDonne(List<Lesson> lessons, Group groupe) {
		
		if(groupe.getGroupe().length() > 2) {
			throw new InvalidParameterException("Le paramètre groupe TP n'est pas correctement renseigné. Il doit ressembler à par exemple à 3 ou 3B.");
		}
		
		List<Lesson> resultat = new ArrayList<Lesson>();
		
		for(Lesson lesson : lessons) {
			// Si le cours est pour le groupe de TD/TP ou pour tout le monde (Amphi...)
			if(lesson.estDansLeGroupe(groupe))
				resultat.add(new Lesson(lesson));
		}
		
		return resultat;
	}
	
}
