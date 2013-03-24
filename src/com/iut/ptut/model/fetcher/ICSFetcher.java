package com.iut.ptut.model.fetcher;

import java.io.FileOutputStream;
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

	public String getFile() throws MalformedURLException, IOException {
		return this.getFile(new URL("http://algec.iut-blagnac.fr/~jmi/edt/INFO/edt/" + semaine + "/" + semestre + "_" + semaine + ".ics"), "test.ics");
	}
		
	private String getFile(URL u, String FileName) throws IOException {
		URLConnection uc = u.openConnection();
		StringBuffer contenu = new StringBuffer();
		int FileLenght = uc.getContentLength();
		if (FileLenght == -1) {
			throw new IOException("Fichier non valide.");
		}
		
		InputStream in = uc.getInputStream();
		
		FileOutputStream WritenFile = new FileOutputStream(FileName);
		byte[]buff = new byte[1024];
		int l = in.read(buff);
		//On écrit tout
		while(l>0)
		{
			WritenFile.write(buff, 0, l);
			contenu.append(new String(buff));
			l = in.read(buff);
		}
		WritenFile.flush();
		WritenFile.close();
		return contenu.toString();
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
