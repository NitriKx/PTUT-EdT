package com.iut.ptut.ctrl;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette exception est lev�e lorsque un des parseurs rencontre un probl�me (malformations).
 * @author Beno�t Sauv�re
 */
public class ParsingProblemException extends Exception {

	private Logger _log = Logger.getLogger(this.getClass().getName());
	
	private static final long serialVersionUID = 5325384522450686433L;
	
	public ParsingProblemException() {
		_log.log(Level.SEVERE, "Probl�me durant la lecture du fichier ICS.");
	}
	
	public ParsingProblemException(String message) {
		_log.log(Level.SEVERE, String.format("Probl�me durant la lecture du fichier ICS. Message = [%s].", message));
	}
}
