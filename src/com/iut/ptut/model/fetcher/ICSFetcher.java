package com.iut.ptut.model.fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.iut.ptut.model.ConfigManager;

/**
 * 
 * @author Jérémie Rouelle
 *
 */
public class ICSFetcher {

	private String semestre="";
	private String semaine="";
	
	public ICSFetcher(String pSemestre, String pSemaine){
		
		// Si la semaine nest que sur 1 seul digit on rajoute un zéro au début
		if(pSemaine.length() < 2)
			pSemaine = "0" + pSemaine;
		
		
		this.semestre=pSemestre;
		this.semaine=pSemaine;
	}

	public InputStream getFile() throws MalformedURLException, IOException {
		
		// On récupère les infos serveur dans le fichier de configuration
		String protocol = ConfigManager.getInstance().getProperty("srv_protocol");
		String host = ConfigManager.getInstance().getProperty("srv_host_name");
		String chemin = ConfigManager.getInstance().getProperty("srv_path");
		String templateFichier = ConfigManager.getInstance().getProperty("srv_file_template");
		
		// On remplace les éléments "template" de la ligne du fichier de configuration
		String nomFichier = templateFichier.replaceAll("@semestre@", this.semestre).replaceAll("@semaine@", this.semaine);
		
		// On récupère le fichier
		return this.getFile(new URL(String.format("%s://%s", protocol, host + chemin + nomFichier)));
	}
		
	private InputStream getFile(URL u) throws IOException {
		URLConnection uc = u.openConnection();
		int FileLenght = uc.getContentLength();
		if (FileLenght == -1) {
			throw new IOException("Fichier non valide.");
		}
		return uc.getInputStream();
	}
	
	public String getSemestre ()
	{
		return this.semestre;
	}
	
	public String getSemaine ()
	{
		return this.semaine;
	}
}
