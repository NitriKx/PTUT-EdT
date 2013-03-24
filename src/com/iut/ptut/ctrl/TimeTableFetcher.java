package com.iut.ptut.ctrl;

import java.io.IOException;
import java.net.MalformedURLException;

import net.fortuna.ical4j.data.ParserException;

import com.iut.ptut.model.Group;
import com.iut.ptut.model.TimeTable;
import com.iut.ptut.model.fetcher.ICSFetcher;

/**
 * Cette classe fournit une interface permettant de r�cup�rer un TimeTable cr�er
 * � partir du fichier r�cup�r� sur le serveur de l'IUT (algec.iut-blagnac.fr en 2012-2013).
 * @author Beno�t Sauv�re
 *
 */
public class TimeTableFetcher {
	
	public TimeTableFetcher() {
		
	}
	
	/**
	 * Cr�er un TimeTable en r�cup�rant le fichier depuis le serveur de l'IUT. 
	 * @param semestre Le semestre (Juste le num�ro)
	 * @param semaine La semaine (2 digit)
	 * @param groupe Le groupe dont on veut l'emploi du temps
	 * @return Un TimeTable contenant les cours pour le grouep demand�.
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParserException
	 */
	public TimeTable recuperer(String semestre, String semaine, Group groupe) throws MalformedURLException, IOException, ParserException {
		// On cr�er le parseur
		ICSFetcher file = new ICSFetcher(semestre, semaine);
		
		// On retourne le TimeTable cr�er � partir du fichier
		return CalendarParser.getTimeTableDepuisICS(file.getFile(), groupe);
	}
	
	
	
}
