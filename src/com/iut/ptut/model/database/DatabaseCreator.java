package com.iut.ptut.model.database;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.iut.ptut.model.AssetsManager;
import com.iut.ptut.model.ConfigManager;

public class DatabaseCreator extends SQLiteOpenHelper {	
	
	private Logger _log = Logger.getLogger(this.getClass().getName());
	
	private String[] scriptCreation;
	private String[] scriptDestruction;
	/**
	 * 
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 * @throws IOException 
	 */
	public DatabaseCreator(Context context, CursorFactory factory,	int version) {
		super(context, ConfigManager.getInstance().getProperty("db_name"), factory, version);
		
		try {
			this.scriptCreation = DatabaseCreator.chargerScriptCreation();
			this.scriptDestruction = DatabaseCreator.chargerScriptDestruction();
		} catch (Exception e){
			_log.log(Level.SEVERE, "Impossible de charger tout les scripts de manipulation de la base de donn�es.");
		}
		
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		_log.log(Level.INFO, "Cr�ation de la base de donn�es...");
		
		for(String sql : this.scriptCreation)
			db.execSQL(sql.trim() + ";");
		
		_log.log(Level.INFO, "Base de donn�es cr�e...");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		_log.log(Level.INFO, "Destruction de la base de donn�es...");
		for(String sql : this.scriptDestruction)
			db.execSQL(sql + ";");
		onCreate(db);
	}

	private static String[] chargerScriptDestruction() throws IOException {
		String sql =  AssetsManager.lireContenuAssets("db_destruction_script_name");
		return sql.split(";(\\s)*[\n\r]");
	}
	
	private static String[] chargerScriptCreation() throws IOException {
		String sql = AssetsManager.lireContenuAssets("db_creation_script_name");
		return sql.split(";(\\s)*[\n\r]");
	}
	
}
