package com.iut.ptut.model;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;

import com.iut.ptut.ctrl.CalendarParser;
import com.iut.ptut.ctrl.ParsingProblemException;

public class Lesson {

	private int idLesson;
	private String libelle;
	private String intervenant;
	private String emplacement;
	private Date dateDebut;
	private Date dateFin;
	
	public Lesson() {
	}

	/**
	 * Créer un objet Lesson à partir de son entrée dans le Calendar iCal4j
	 * @param comp Le component iCal4j contenant les informations sur le cours
	 * @throws ParseException 
	 * @throws ParsingProblemException 
	 */
	public Lesson(Component comp) throws ParseException, ParsingProblemException {
		
		// Date de début
		Property prop = comp.getProperty("DTSTART");
		if(prop == null || "".equals(prop.getValue())) 
			throw new ParsingProblemException("La propriété DTSTART n'existe pas ou est vide.");
		this.dateDebut = new Date(new DateTime(prop.getValue()).getTime());
		
		// Date de fin
		prop = comp.getProperty("DTEND");
		if(prop == null || "".equals(prop.getValue())) 
			throw new ParsingProblemException("La propriété DTEND n'existe pas ou est vide.");
		this.dateFin = new Date(new DateTime(prop.getValue()).getTime());
		
		// Toute les informations du summary
		prop = comp.getProperty("SUMMARY");
		if(prop == null || "".equals(prop.getValue())) 
			throw new ParsingProblemException("La propriété SUMMARY n'existe pas ou est vide.");
		HashMap<String, String> parsedSummary = CalendarParser.parseSummary(prop.getValue());
		this.intervenant = parsedSummary.get("intervenant");
		this.libelle = parsedSummary.get("matiere");
		
		// Emplacement
		prop = comp.getProperty("LOCATION");
		if(prop == null || "".equals(prop.getValue())) 
			throw new ParsingProblemException("La propriété LOCATION n'existe pas ou est vide.");
		this.emplacement = prop.getValue();
	}
	
	public int getIdLesson() {
		return this.idLesson;
	}

	public void setIdLesson(int pIdLesson) {
		this.idLesson = pIdLesson;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String pLibelle) {
		this.libelle = pLibelle;
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
	
	public String toString() {
		return String.format("Id=[%d] Libellé=[%s] Emplacement=[%s] Intervenant=[%s] Début=[%s] Fin=[%s]", 
				this.idLesson, this.libelle, this.emplacement, this.intervenant, this.dateDebut, this.dateFin);
	}
	
}
