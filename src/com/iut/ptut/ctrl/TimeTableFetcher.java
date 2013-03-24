package com.iut.ptut.ctrl;

import java.io.IOException;
import java.net.MalformedURLException;

import net.fortuna.ical4j.data.ParserException;

import com.iut.ptut.model.Group;
import com.iut.ptut.model.TimeTable;
import com.iut.ptut.model.fetcher.ICSFetcher;

/**
 * Cette classe fournit une interface permettant de récupérer un TimeTable créer
 * à partir du fichier récupéré sur le serveur de l'IUT (algec.iut-blagnac.fr en 2012-2013).
 * @author Benoît Sauvère
 *
 */
public class TimeTableFetcher {
	
	public TimeTableFetcher() {
		
	}
	
	/**
	 * Créer un TimeTable en récupérant le fichier depuis le serveur de l'IUT. 
	 * @param semestre Le semestre 
	 * @param semaine La semaine (2 digit)
	 * @param groupe Le groupe dont on veut l'emploi du temps
	 * @return Un TimeTable contenant les cours pour le grouep demandé.
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParserException
	 */
	public TimeTable recuperer(String semestre, String semaine, Group groupe) throws MalformedURLException, IOException, ParserException {
		
		// Si la première lettre du semestre n'est pas S on le rajoute
		if(!semestre.startsWith("S")) {
			semestre  = "S" + semestre;
		}
		
		// Si l'utiliateur à fournit un numéro de semaine sans le zéro on le rajoute
		if(semaine.length() < 2) {
			semaine = "0" + semaine;
		}
		
		// On créer le parseur
		ICSFetcher file = new ICSFetcher(semestre, semaine);
		
		// On retourne le TimeTable créer à partir du fichier
		return CalendarParser.getTimeTableDepuisICS(file.getFile(), groupe);
	}
	
	
	
}
