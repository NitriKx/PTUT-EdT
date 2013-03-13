package com.iut.ptut.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.iut.ptut.MainActivity;

public class AssetsManager {
	
	/**
	 * Cette méthode permet de lire le contenu d'un fichier assets
	 * @param nom Le chemin vers le fichier assets (nom compris)
	 * @return Le contenu doud la forme d'une chaine de caractères.
	 * @throws IOException
	 */
	public static final String lireContenuAssets(String nom) throws IOException {
		StringBuffer contenu = new StringBuffer();
		byte buf[] = new byte[30];
		InputStream is = MainActivity.context.getAssets().open(ConfigManager.getInstance().getProperty(nom)); 
		BufferedInputStream bis = new BufferedInputStream(is);
		while (bis.read(buf) > 0) {
			contenu.append(new String(buf));
		}
		return contenu.toString();
	}
	
	public static InputStream ouvrirInputStreamAsset(String nom) throws IOException {
		return new BufferedInputStream(MainActivity.context.getAssets().open(ConfigManager.getInstance().getProperty(nom))); 
	}
	
}
