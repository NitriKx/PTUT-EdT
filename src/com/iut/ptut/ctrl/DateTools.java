package com.iut.ptut.ctrl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	 * Recup�re l'heure formatt�. Exemple "08:30"
	 * @param date La date dont on veut l'heure.
	 * @return Une chaine de caract�re contenant l'heure et les minutes.
	 */
	public static String recupererHeureFormatte(Date date) {
		DateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(date);
	}
	
}
