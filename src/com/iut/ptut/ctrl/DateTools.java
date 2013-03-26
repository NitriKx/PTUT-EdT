package com.iut.ptut.ctrl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTools {
	
	/**
	 * Retourne le timestamp du d�but du jour dans lequel est la date pass�e en param�tre.
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
	 * Retourne une date pointant vers le d�but de la semaine d�sign�e.
	 * @param noSemaine Le num�ro de la semaine dans l'ann�e (1 � 52)
	 * @param annee L'ann�e dans laquelel est la semaine
	 * @return Une date pointant vers le d�but de la semaine d�sign�e
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
	 * Retourne une date pointant vers la fin de la semaine d�sign�e.
	 * @param noSemaine Le num�ro de la semaine dans l'ann�e (1 � 52)
	 * @param annee L'ann�e dans laquelel est la semaine
	 * @return Une date pointant vers la fin de la semaine d�sign�e
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
	 * Recup�re l'heure formatt�. Exemple "08:30"
	 * @param date La date dont on veut l'heure.
	 * @return Une chaine de caract�re contenant l'heure et les minutes.
	 */
	public static String recupererHeureFormatte(Date date) {
		DateFormat df = new SimpleDateFormat("HH:mm", Locale.FRANCE);
		return df.format(date);
	}
	
}
