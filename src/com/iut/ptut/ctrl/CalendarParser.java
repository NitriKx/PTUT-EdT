package com.iut.ptut.ctrl;

import java.io.FileInputStream;
import java.io.IOException;
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
import com.iut.ptut.model.Lesson;
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
		Calendar calendar = CalendarParser.convertirICSEnICal4J("S4_07.ics");
		System.out.println(CalendarParser.convertirCalendarEnTimeTable(calendar));
	}
	
	private CalendarParser() {
	}
	
	/**
	 * Convertit un Calendar de type "iCal4j" en TimeTable de notre application.
	 * @param cal Un calendar "iCal4j"
	 * @return Un TimeTable avec les données du calendar
	 */
	@SuppressWarnings("rawtypes")
	public static TimeTable convertirCalendarEnTimeTable(Calendar cal) {
		
		TimeTable tt = new TimeTable();
		// Une map associant le timestamp du début du jour à l'objet du jour correspondant
		HashMap<Long, Day> mapJours = new HashMap<Long, Day>();
		
		// On parcourt le ICS "lu"
		for (Iterator i = cal.getComponents().iterator(); i.hasNext();) {
		    Component component = (Component) i.next();
		    
		    // Si c'est un cours
		    if("VEVENT".equals(component.getName())) {
		    	
		    	try{ 
		    		Lesson cours = new Lesson(component);
		    		long debutJourCours = DateTools.calculeTimestampDebutJour(cours.getDateDebut());
		    		Day jour = mapJours.get(debutJourCours);
		    		
		    		// Si le jour n'a pas encore été crée, on l'ajoute à la map
		    		if(jour == null){
		    			jour = new Day();
		    			jour.setpDateDebut(new Date(debutJourCours));
		    			jour.setpDateFin(new Date(debutJourCours+1000*60*60*24));
		    			mapJours.put(debutJourCours, jour);
		    		}
		    		
		    		// On ajoute le cours au jour
		    		jour.addLesson(cours);
		    		
		    	} catch (Exception e) {
		    		_log.log(Level.SEVERE, "Impossible de convertir le VEVENT en Lesson.");
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
	public static Calendar convertirICSEnICal4J(String cheminFichierICS) throws IOException, ParserException {
		// On ouvre un flux de lecture sur le fichier : 
		FileInputStream fin = new FileInputStream(cheminFichierICS);
		// On le fait parser par iCal4J
		CalendarBuilder builder = new CalendarBuilder();
		Calendar result = builder.build(fin);
		// On ferme le flux
		fin.close();
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
				throw new ParsingProblemException("Le deuxième bloc du summary est malformé.");
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
	
	public static List<Lesson> filterCoursGroupeDonne(List<Lesson> lesson, String groupeTP) {
		
		return null;
	}
	
}
