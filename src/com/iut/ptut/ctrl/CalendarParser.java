package com.iut.ptut.ctrl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

public class CalendarParser {

	private Calendar calendar;
	
	private Logger _log = Logger.getLogger(this.getClass().getName());
	
	/**
	 * @param args
	 * @throws ParserException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ParserException {
		Calendar calendar = new CalendarParser().parseIntoICal4J("S4_07.ics");
		for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
		    Component component = (Component) i.next();
		    System.out.println("Component [" + component.getName() + "]");

		    for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
		        Property property = (Property) j.next();
		        System.out.println("\tProperty [" + property.getName() + ", " + property.getValue() + "]");
		    }
		}
	}
	
	public CalendarParser() {
		this.calendar = null;
	}
	
	public CalendarParser(String cheminFichierICS) {
		try {
			this.calendar = this.parseIntoICal4J(cheminFichierICS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parse un fichier ICS en un objet de type Calendar de le librairie iCal4J
	 * @param cheminFichierICS Le chemin vers le ficheir ICS
	 * @return Un objet de type net.fortuna.ical4j.model.Calendar
	 * @throws IOException
	 * @throws ParserException
	 */
	public Calendar parseIntoICal4J(String cheminFichierICS) throws IOException, ParserException {
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
	 * 	<li>type (TD ou TP)</li>
	 * 	<li>groupe (ex : 2 ou 2A)</li>
	 * 	<li>intervenant</li>
	 * </ul>
	 */
	public static HashMap<String, String> parseSummary(String summaryFromICS) throws ParsingProblemException {
		
		HashMap<String, String> resultat = new HashMap<String, String>();
		String[] split = summaryFromICS.split(" ");
		
		// Première vérification de format :
		if(split.length != 8) {
			throw new ParsingProblemException("Le summary ne contient pas le bon nombre d'espaces.");
		}
		
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
		
		// On vérifie qu'il n'y ai pas de valeurs vide
		for(String key : resultat.keySet()) {
			if(resultat.get(key).equals("")) {
				throw new ParsingProblemException("Une des valeurs est vide.");
			}
		}
		
		return resultat;
	}
	
}
