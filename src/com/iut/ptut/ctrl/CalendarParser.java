package com.iut.ptut.ctrl;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

public class CalendarParser {

	/**
	 * @param args
	 * @throws ParserException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ParserException {
		FileInputStream fin = new FileInputStream("S4_07.ics");
		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(fin);
		
		for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
		    Component component = (Component) i.next();
		    System.out.println("Component [" + component.getName() + "]");

		    for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
		        Property property = (Property) j.next();
		        System.out.println("\tProperty [" + property.getName() + ", " + property.getValue() + "]");
		    }
		}
	}
	
	/**
	 * Lit le contenu du summary pour en recup�rer les informations.
	 * @param summaryFromICS le contenu d'une ligne de fichier ICS<br/> Format : "formation specialite/semestre matiere type groupe -- intervenant"
	 * @return HashMap contenant les entr�es :<br/>
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
	public HashMap<String, String> parseSummary(String summaryFromICS) throws ParsingProblemException {
		
		HashMap<String, String> resultat = new HashMap<String, String>();
		String[] split = summaryFromICS.split(" ");
		
		// Premi�re v�rification de format :
		if(split.length != 8) {
			throw new ParsingProblemException("Le summary ne contient pas le bon nombre d'espaces.");
		}
		
		// On lit les diff�rentes informations
		resultat.put("formation", split[0]);
		resultat.put("matiere", split[2]);
		resultat.put("type", split[3]);
		resultat.put("groupe", split[5]);
		resultat.put("intervenant", split[7]);

		
		// SPECIAL - On s�pare la sp�cialit� du semestre (un /)
		String[] splitPartie2 = split[1].split("/");
		if(splitPartie2.length != 2) {
			throw new ParsingProblemException("Le deuxi�me bloc du summary est malform�.");
		}
		resultat.put("specialite", splitPartie2[0]);
		resultat.put("semestre", splitPartie2[1]);
		
		// On v�rifie qu'il n'y ai pas de valeurs vide
		for(String key : resultat.keySet()) {
			if(resultat.get(key).equals("")) {
				throw new ParsingProblemException("Une des valeurs est vide.");
			}
		}
		
		return resultat;
	}
	
	
}
