package com.iut.ptut.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.iut.ptut.view.MainActivity;

import android.content.res.AssetManager;

/**
 * Cette classe permet de manipuler le fichier de cofiguration de l'application.
 * @author Benoît Sauvère
 *
 */
public class ConfigManager {
	
	private static ConfigManager _instance;
	
	private Logger _log = Logger.getLogger(this.getClass().getName());
	
	private Properties prop;
	private AssetManager assetManager;
	private File configFile = new File(MainActivity.context.getFilesDir(), "config.properties");
	
	private ConfigManager() {
		
		// Si le fichier de configuration n'existe pas, on charge la sauvegarde depuis les assets
		try {
			if(!configFile.exists()) {
	        	_log.log(Level.SEVERE, "Recréation du ficheir de configuration depuis la sauvegarde...");
				configFile.createNewFile();
				byte[] buf = new byte[30];
				FileOutputStream writer = new FileOutputStream(configFile);
				InputStream backupReader = AssetsManager.ouvrirInputStreamAsset("config.properties");
				
				while(backupReader.read(buf) > 0)
					writer.write(buf);
				
				writer.close();
				backupReader.close();
			}
			
			this.prop = new Properties();
			
        	FileInputStream stream = new FileInputStream(configFile);
            this.prop.load(stream);
            stream.close();
            
        } catch (IOException e) {
        	String message = "Impossible de charger le ficher de configuration Raison=[" + e.getMessage() + "]";
        	_log.log(Level.SEVERE, message);
        	throw new RuntimeException(message);
        }
			
	}
	
	public static ConfigManager getInstance() {
		if(_instance == null) {
			_instance = new ConfigManager();
		}
		
		return _instance;
	}
	
	public void sauvegarderValeurs() {
		try {
			OutputStream os = new FileOutputStream(configFile);
			this.prop.store(os, null);
			os.close();
		} catch (Exception e) {
			throw new RuntimeException("Impossible de charger le fichier de configuration pour sauvegarder les données... Raison=[" + e.getMessage()+"]");
		}
	}
	
	/**
	 * Récupère une propriété du fichier de configuration.
	 * @param name Le nom de la propriété
	 * @return La valeur associée
	 */
	public String getProperty(String name) {
		return this.prop.getProperty(name);
	}
	
	/**
	 * Modifie une valeur du fichier de configuration.
	 * @param name
	 * @param value
	 */
	public void setProperty(String name, String value) {
		this.prop.setProperty(name, value);
		sauvegarderValeurs();
	}
}
