package com.iut.ptut.model;

import java.util.Date;

import net.fortuna.ical4j.model.Calendar;

public class Lesson {

	private int idLesson;
	private String Libelle;
	private String intervenant;
	private String emplacement;
	private Date dateDebut;
	private Date dateFin;
	
	public Lesson(Calendar cal) {
		
	}

	public int getIdLesson() {
		return this.idLesson;
	}

	public void setIdLesson(int pIdLesson) {
		this.idLesson = pIdLesson;
	}

	public String getLibelle() {
		return this.Libelle;
	}

	public void setLibelle(String pLibelle) {
		this.Libelle = pLibelle;
	}

	public String getIntervenant() {
		return this.intervenant;
	}

	public void setIntervenant(String pIntervenant) {
		this.intervenant = pIntervenant;
	}

	public String getEmplacement() {
		return this.emplacement;
	}

	public void setEmplacement(String pEmplacement) {
		this.emplacement = pEmplacement;
	}

	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date pDateDebut) {
		this.dateDebut = pDateDebut;
	}

	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date pDateFin) {
		this.dateFin = pDateFin;
	}

}
