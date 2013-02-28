package com.iut.ptut.model.database;

/**
 * Cette classe fournit une collection de méthodes pour intéragir avec la base de données.
 * SINGLETON
 * @author Benoît Sauvère
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
