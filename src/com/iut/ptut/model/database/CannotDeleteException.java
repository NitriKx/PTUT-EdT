package com.iut.ptut.model.database;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CannotDeleteException extends Exception {

	private Logger _log = Logger.getLogger(this.getClass().getName());
	
	private static final long serialVersionUID = -3813061925684763553L;
	
	public CannotDeleteException(String message) {
		_log.log(Level.SEVERE, message);
	}

}
