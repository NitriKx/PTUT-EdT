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
	
	private String scriptCreation;
	private String scriptDestruction;
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
			_log.log(Level.SEVERE, "Impossible de charger tout les scripts de manipulation de la base de données.");
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		_log.log(Level.INFO, "Création de la base de données...");
		db.execSQL(this.scriptCreation);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(this.scriptDestruction);
		onCreate(db);
	}

	private static String chargerScriptDestruction() throws IOException {
		return AssetsManager.lireContenuAssets("db_creation_script_name");
	}
	
	private static String chargerScriptCreation() throws IOException {
		return AssetsManager.lireContenuAssets("db_destruction_script_name");
	}
	
}
