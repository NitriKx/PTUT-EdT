package com.iut.ptut.model.fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author Jérémie Rouelle
 *
 */
public class ICSFetcher {

	private String semestre="";
	private String semaine="";
	
	public ICSFetcher(String pSemestre, String pSemaine){
		this.semestre=pSemestre;
		this.semaine=pSemaine;
	}

	public InputStream getFile() throws MalformedURLException, IOException {
		return this.getFile(new URL("http://algec.iut-blagnac.fr/~jmi/edt/INFO/edt/" + semaine + "/" + semestre + "_" + semaine + ".ics"));
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
