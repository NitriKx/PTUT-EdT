package com.iut.ptut.model;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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
	private int idTimeTable;

	private Group groupe;

	public Lesson() {
		this.libelle = "";
		this.intervenant = "";
		this.emplacement = "";
		this.idLesson = -1;
		this.idTimeTable = -1;
		this.dateDebut = new Date();
		this.dateFin = new Date();
		this.groupe = new Group();
	}

	public Lesson(int idLesson, String libelle, String intervenant, String emplacement, Date dateDebut, Date dateFin, int idTimeTable, Group groupe) {
		this.idLesson = idLesson;
		this.libelle = libelle;
		this.intervenant = intervenant;
		this.emplacement = emplacement;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.idTimeTable = idTimeTable;
		this.groupe = groupe;
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
		this.idTimeTable = lesson.idTimeTable;
		this.groupe = new Group(lesson.getGroupe());
	}

	/**
	 * Créer un objet Lesson à partir de son entrée dans le Calendar iCal4j
	 * 
	 * @param comp
	 *            Le component iCal4j contenant les informations sur le cours
	 * @throws ParseException
	 * @throws ParsingProblemException
	 */
	public Lesson(Component comp) throws ParseException,
			ParsingProblemException {
		
		this.idTimeTable = -1;
		this.idLesson = -1;
		
		// Date de début
		Property prop = comp.getProperty("DTSTART");
		if (prop == null || "".equals(prop.getValue()))
			throw new ParsingProblemException(
					"La propriété DTSTART n'existe pas ou est vide.");
		this.dateDebut = new Date(new DateTime(prop.getValue()).getTime());

		// Date de fin
		prop = comp.getProperty("DTEND");
		if (prop == null || "".equals(prop.getValue()))
			throw new ParsingProblemException(
					"La propriété DTEND n'existe pas ou est vide.");
		this.dateFin = new Date(new DateTime(prop.getValue()).getTime());

		// Toute les informations du summary
		prop = comp.getProperty("SUMMARY");
		if (prop == null || "".equals(prop.getValue()))
			throw new ParsingProblemException(
					"La propriété SUMMARY n'existe pas ou est vide.");
		HashMap<String, String> parsedSummary = CalendarParser
				.parseSummary(prop.getValue());
		this.intervenant = parsedSummary.get("intervenant");
		this.libelle = parsedSummary.get("matiere");
		
		// - Si le groupe est définis, on le lit
		this.groupe = new Group();
		String sousGroupe = "";
		String numeroGroupe = "";
		if (parsedSummary.get("groupe") != null) {
			numeroGroupe = parsedSummary.get("groupe").substring(0, 1);
			// - Si le sous groupe n'existe pas
			if (parsedSummary.get("groupe").length() == 2)
				sousGroupe = parsedSummary.get("groupe").substring(1, 2);
			else
				sousGroupe = "";
		
		// - Sinon c'est un cour commun à tous
		} else {
			numeroGroupe = "A";
			sousGroupe = "";
		}
		this.groupe.setGroupe(numeroGroupe + sousGroupe);
		this.groupe.setAnnee(CalendarParser.getAnneeScolairePourDate(this.dateDebut));
		this.groupe.setSemestre(Integer.parseInt(parsedSummary.get("semestre").replaceAll("S", "")));
		
		// Emplacement
		prop = comp.getProperty("LOCATION");
		if (prop == null || "".equals(prop.getValue()))
			throw new ParsingProblemException(
					"La propriété LOCATION n'existe pas ou est vide.");
		this.emplacement = prop.getValue();
	}

	/**
	 * Vérifie si le cours est pour le groupe désigné.
	 * 
	 * @param groupe
	 *            Le groupe. Peut-être de la forme : "3", "3B, "A" (commun à
	 *            tous) ou vide. Si le groupe est vide, la méthode renverra
	 *            vrai.
	 * @return True si le cours est pour le groupe désigné.
	 */
	public boolean estDansLeGroupe(Group groupe) {
		// Si les numéro de groupe et de tp correspondent, ou que le groupe est
		// une chaine vide,
		// ou que le cours est une cours commun on retourne vrai.
		return (groupe.equals(this.getGroupe()))
				|| (this.groupe.getGroupe().length() == 1 && groupe.getAnnee() == this.getGroupe().getAnnee() && groupe.getSemestre() == this.getGroupe().getSemestre() 
					&& groupe.getGroupe().substring(0, 1).equals(this.getGroupe().getGroupe().substring(0, 1)))
				|| "".equals(groupe.getGroupe())
				|| "A".equals(this.getGroupe().getGroupe().substring(0, 1));
	}

	public String toString() {
		return String.format(Locale.getDefault(), "Id=[%d] Libellé=[%s] Emplacement=[%s] Intervenant=[%s] Début=[%s] Fin=[%s] Groupe=[%s]",
						this.idLesson, this.libelle, this.emplacement,
						this.intervenant, this.dateDebut, this.dateFin, this.getGroupe());
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

	public int getIdTimeTable() {
		return idTimeTable;
	}

	public void setIdTimeTable(int idTimeTable) {
		this.idTimeTable = idTimeTable;
	}

	public Group getGroupe() {
		return groupe;
	}

	public void setGroupe(Group groupe) {
		this.groupe = groupe;
	}
	
}
