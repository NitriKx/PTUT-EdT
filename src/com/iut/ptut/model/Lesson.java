package com.iut.ptut.model;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

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

	/**
	 * Le num�ro de groupe peut-�tre un chiffre (1,2,3...) ou un "A" si c'est un
	 * cour commun � toute la promotion (le groupe de TP est vide).
	 */
	private String numeroGroupe;
	/**
	 * Le num�ro de sousGroupe (ou groupe TP) est soit une lettre (A,B,C...)
	 * soit est vide.
	 */
	private String sousGroupe;

	public Lesson() {
		this.libelle = "";
		this.intervenant = "";
		this.emplacement = "";
		this.idLesson = 0;
		this.dateDebut = new Date();
		this.dateFin = new Date();
	}

	/**
	 * Contructeur de recopie.
	 * 
	 * @param lesson
	 */
	public Lesson(Lesson lesson) {
		this.idLesson = lesson.idLesson;
		this.libelle = new String(lesson.libelle);
		this.intervenant = new String(lesson.intervenant);
		this.emplacement = new String(lesson.emplacement);
		this.dateDebut = new Date(lesson.dateDebut.getTime());
		this.dateFin = new Date(lesson.dateFin.getTime());
		this.numeroGroupe = new String(lesson.numeroGroupe);
		this.sousGroupe = new String(lesson.sousGroupe);
	}

	/**
	 * Cr�er un objet Lesson � partir de son entr�e dans le Calendar iCal4j
	 * 
	 * @param comp
	 *            Le component iCal4j contenant les informations sur le cours
	 * @throws ParseException
	 * @throws ParsingProblemException
	 */
	public Lesson(Component comp) throws ParseException,
			ParsingProblemException {

		// Date de d�but
		Property prop = comp.getProperty("DTSTART");
		if (prop == null || "".equals(prop.getValue()))
			throw new ParsingProblemException(
					"La propri�t� DTSTART n'existe pas ou est vide.");
		this.dateDebut = new Date(new DateTime(prop.getValue()).getTime());

		// Date de fin
		prop = comp.getProperty("DTEND");
		if (prop == null || "".equals(prop.getValue()))
			throw new ParsingProblemException(
					"La propri�t� DTEND n'existe pas ou est vide.");
		this.dateFin = new Date(new DateTime(prop.getValue()).getTime());

		// Toute les informations du summary
		prop = comp.getProperty("SUMMARY");
		if (prop == null || "".equals(prop.getValue()))
			throw new ParsingProblemException(
					"La propri�t� SUMMARY n'existe pas ou est vide.");
		HashMap<String, String> parsedSummary = CalendarParser
				.parseSummary(prop.getValue());
		this.intervenant = parsedSummary.get("intervenant");
		this.libelle = parsedSummary.get("matiere");
		
		// - Si le groupe est d�finis, on le lit
		if (parsedSummary.get("groupe") != null) {
			this.numeroGroupe = parsedSummary.get("groupe").substring(0, 1);
			// - Si le sous groupe n'existe pas
			if (parsedSummary.get("groupe").length() == 2)
				this.sousGroupe = parsedSummary.get("groupe").substring(1, 2);
			else
				this.sousGroupe = "";
		
		// - Sinon c'est un cour commun � tous
		} else {
			this.numeroGroupe = "A";
			this.sousGroupe = "";
		}

		// Emplacement
		prop = comp.getProperty("LOCATION");
		if (prop == null || "".equals(prop.getValue()))
			throw new ParsingProblemException(
					"La propri�t� LOCATION n'existe pas ou est vide.");
		this.emplacement = prop.getValue();
	}

	/**
	 * V�rifie si le cours est pour le groupe d�sign�.
	 * 
	 * @param groupe
	 *            Le groupe. Peut-�tre de la forme : "3", "3B, "A" (commun �
	 *            tous) ou vide. Si le groupe est vide, la m�thode renverra
	 *            vrai.
	 * @return True si la cours est pour le groupe d�sign�.
	 */
	public boolean estDansLeGroupe(String groupe) {
		String groupeTD = (groupe.length() > 0) ? groupe.substring(0, 1) : "";
		String groupeTP = (groupe.length() > 1) ? groupe.substring(1, 2) : "";
		// Si les num�ro de groupe et de tp correspondent, ou que le groupe est
		// une chaine vide,
		// ou que le cours est une cours commun on retourne vrai.
		return (groupeTD.equals(this.getNumeroGroupe()) && groupeTP.equals(this.getSousGroupe()))
				|| (groupeTD.equals(this.getNumeroGroupe()) && "".equals(this.getSousGroupe()))
				|| groupe.equals("")
				|| "A".equals(this.getNumeroGroupe());
	}

	public String toString() {
		return String.format("Id=[%d] Libell�=[%s] Emplacement=[%s] Intervenant=[%s] D�but=[%s] Fin=[%s] NumeroGroupe=[%s] SousGroupe=[%s]",
						this.idLesson, this.libelle, this.emplacement,
						this.intervenant, this.dateDebut, this.dateFin, this.getNumeroGroupe(), this.getSousGroupe());
	}

	//
	//
	// GETTERS AND SETTERS
	//
	//

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

	public String getNumeroGroupe() {
		return numeroGroupe;
	}

	public void setNumeroGroupe(String numeroGroupe) {
		this.numeroGroupe = numeroGroupe;
	}

	public String getSousGroupe() {
		return sousGroupe;
	}

	public void setSousGroupe(String sousGroupe) {
		this.sousGroupe = sousGroupe;
	}

}
