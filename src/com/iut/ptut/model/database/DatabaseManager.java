package com.iut.ptut.model.database;

/**
 * Cette classe fournit une collection de m�thodes pour int�ragir avec la base de donn�es.
 * SINGLETON
 * @author Beno�t Sauv�re
 *
 */
public class DatabaseManager {
	
	private static DatabaseManager _instance;
	
	private DatabaseManager() {
		
		
		
		
		
	}
	
	public static DatabaseManager getInstance() {
		if(_instance == null) {
			_instance = new DatabaseManager();
		}
		
		return _instance;
	}
	
	
	
}
