package com.iut.ptut.model;

import java.util.Date;

import com.iut.ptut.ctrl.CalendarParser;

/**
 * 
 * @author Jérémie Rouelle, Benoît Sauvère
 *
 */
public class Group {

	private String groupe;
	private int semestre;
	private int annee;

	public Group() {
		this.groupe = "";
		this.semestre = 1;
		this.annee = CalendarParser.getAnneeScolairePourDate(new Date());
	}
	
	/**
	 * 
	 * @param groupe Le groupe. De la forme "3", "3B", "" ou "A"
	 * @param semestre Le numéro du semestre
	 * @param annee L'année scolaire (pour 2012-2013 prendre 2012)
	 */
	public Group(String groupe, int semestre, int annee) {
		this.groupe = groupe;
		this.semestre = semestre;
		this.annee = annee;
	}
	
	public Group(Group g) {
		this(new String(g.getGroupe()), g.getSemestre(), g.getAnnee());
	}

	
	//
	// GETTERS AND SETTERS
	//
	
	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}
	
	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}
	
	//
	// OBJECT OVERRIDES
	// 
	
	public boolean equals(Object o) {
		if(o instanceof Group) {
			Group g = (Group) o;
			return this.getGroupe().equals(g.getGroupe()) && this.getAnnee() == g.getAnnee() && this.getSemestre() == g.getSemestre();
		} 
		return false;
	}

	public String toString() {
		return "idGroupe=[" + this.groupe + "] Année=[" + this.annee + "] Semestre=[" + this.semestre + "]";
	}

	
}
