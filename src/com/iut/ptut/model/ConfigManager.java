package com.iut.ptut.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.iut.ptut.view.MainActivity;

/**
 * Cette classe permet de manipuler le fichier de cofiguration de l'application.
 * @author Beno�t Sauv�re
 *
 */
public class ConfigManager {
	
	private static ConfigManager _instance;
	
	private Logger _log = Logger.getLogger(this.getClass().getName());
	
	private Properties prop;
	private File configFile = new File(MainActivity.context.getFilesDir(), "config.properties");
	
	private ConfigManager() {
		
		// Si le fichier de configuration n'existe pas, on charge la sauvegarde depuis les assets
		try {
			if(!configFile.exists()) {
	        	_log.log(Level.SEVERE, "Recr�ation du ficheir de configuration depuis la sauvegarde...");
				configFile.createNewFile();
				byte[] buf = new byte[30];
				FileOutputStream writer = new FileOutputStream(configFile);
				InputStream backupReader = AssetsManager.ouvrirInputStreamAsset("config.properties");

				int len = 0;
				while((len = backupReader.read(buf)) > 0)
					writer.write(buf, 0, len);
				
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
	
	public synchronized static ConfigManager getInstance() {
		if(_instance == null) {
			_instance = new ConfigManager();
		}
		
		return _instance;
	}
	
	public synchronized void sauvegarderValeurs() {
		try {
			OutputStream os = new FileOutputStream(configFile);
			this.prop.store(os, null);
			os.close();
		} catch (Exception e) {
			throw new RuntimeException("Impossible de charger le fichier de configuration pour sauvegarder les donn�es... Raison=[" + e.getMessage()+"]");
		}
	}
	
	/**
	 * R�cup�re une propri�t� du fichier de configuration.
	 * @param name Le nom de la propri�t�
	 * @return La valeur associ�e
	 */
	public synchronized String getProperty(String name) {
		return this.prop.getProperty(name);
	}
	
	/**
	 * Modifie une valeur du fichier de configuration.
	 * @param name
	 * @param value
	 */
	public synchronized void setProperty(String name, String value) {
		this.prop.setProperty(name, value);
		sauvegarderValeurs();
	}
	
	/**
	 * Supprime une propri�t�.
	 * @param name Le nom de la propri�t�
	 * @return True si l'�l�ment � �t� supprimer ou false si l'�l�ment n'existe pas.
	 */
	public synchronized boolean supprimerProperty(String name) {
		boolean r = this.prop.remove(name) != null;
		sauvegarderValeurs();
		return r;
	}
}
