package com.iut.ptut.model.fetcher;

import java.util.Date;

public class File {

	
	private int idFile;
	private String chemin;
	private Date dateRecuperation;
	
	
	public File ()
	{
		
	}


	public int getIdFile() {
		return this.idFile;
	}


	public void setIdFile(int pIdFile) {
		this.idFile = pIdFile;
	}


	public String getChemin() {
		return this.chemin;
	}


	public void setChemin(String pChemin) {
		this.chemin = pChemin;
	}


	public Date getDateRecuperation() {
		return this.dateRecuperation;
	}


	public void setDateRecuperation(Date pDateRecuperation) {
		this.dateRecuperation = pDateRecuperation;
	}
	
	public String toString()
	{
		return("Fichier : "+this.idFile + " Chemin : "+ this.chemin + " Date de récupération : "+this.dateRecuperation);
	}
	
	
	
	
}
