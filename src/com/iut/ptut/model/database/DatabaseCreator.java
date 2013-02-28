package com.iut.ptut.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.iut.ptut.model.ConfigManager;

public class DatabaseCreator extends SQLiteOpenHelper {	
	
	/**
	 * 
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DatabaseCreator(Context context, CursorFactory factory,	int version) {
		super(context, ConfigManager.getInstance().getProperty("db_name"), factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

	
	private static final String chargerScriptDepuisFichier(String sqlFilePath) {
		return "";
	}
	
	
}
