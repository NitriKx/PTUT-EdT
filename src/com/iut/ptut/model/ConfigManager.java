package com.iut.ptut.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.iut.ptut.view.MainActivity;

import android.content.res.AssetManager;

/**
 * Cette classe permet de manipuler le fichier de cofiguration de l'application.
 * @author Beno�t Sauv�re
 *
 */
public class ConfigManager {
	
	private static ConfigManager _instance;
	
	private Properties prop;
	private AssetManager assetManager;
	
	private ConfigManager() {
		
		this.prop = new Properties();
		this.assetManager = MainActivity.context.getAssets();
		
        try {
        	InputStream stream = this.assetManager.open("config.properties");
            this.prop.load(stream);
            stream.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
		
	}
	
	public static ConfigManager getInstance() {
		if(_instance == null) {
			_instance = new ConfigManager();
		}
		
		return _instance;
	}
	
	/**
	 * R�cup�re une propri�t� du fichier de configuration.
	 * @param name Le nom de la propri�t�
	 * @return La valeur associ�e
	 */
	public String getProperty(String name) {
		return this.prop.getProperty(name);
	}
	
}
