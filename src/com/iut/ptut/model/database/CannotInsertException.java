package com.iut.ptut.model.database;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CannotInsertException extends Exception {
	
	private Logger _log = Logger.getLogger(this.getClass().getName());

	private static final long serialVersionUID = 3796984501635273797L;
	
	public CannotInsertException(String message) {
		_log.log(Level.SEVERE, message);
	}
	
}
