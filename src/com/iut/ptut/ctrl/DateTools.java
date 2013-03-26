package com.iut.ptut.ctrl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTools {
	
	/**
	 * Retourne le timestamp du début du jour dans lequel est la date passée en paramètre.
	 * @param date
	 * @return
	 */
	public static long calculeTimestampDebutJour(Date date) {
		java.util.Calendar calendrier = java.util.Calendar.getInstance();
		calendrier.setTime(date);
		calendrier.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calendrier.set(java.util.Calendar.MINUTE, 0);
		calendrier.set(java.util.Calendar.SECOND, 0);
		calendrier.set(java.util.Calendar.MILLISECOND, 0);
		return calendrier.getTimeInMillis();
	}
	
	/**
	 * Retourne une date pointant vers le début de la semaine désignée.
	 * @param noSemaine Le numéro de la semaine dans l'année (1 à 52)
	 * @param annee L'année dans laquelel est la semaine
	 * @return Une date pointant vers le début de la semaine désignée
	 */
	public static Date calculeTimestampDebutSemaine(int noSemaine, int annee) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, noSemaine);
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
	/**
	 * Retourne une date pointant vers la fin de la semaine désignée.
	 * @param noSemaine Le numéro de la semaine dans l'année (1 à 52)
	 * @param annee L'année dans laquelel est la semaine
	 * @return Une date pointant vers la fin de la semaine désignée
	 */
	public static Date calculeTimestampFinSemaine(int noSemaine, int annee) {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, noSemaine);
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		
		return cal.getTime();
	}
	
	/**
	 * Recupère l'heure formatté. Exemple "08:30"
	 * @param date La date dont on veut l'heure.
	 * @return Une chaine de caractère contenant l'heure et les minutes.
	 */
	public static String recupererHeureFormatte(Date date) {
		DateFormat df = new SimpleDateFormat("HH:mm", Locale.FRANCE);
		return df.format(date);
	}
	
}
