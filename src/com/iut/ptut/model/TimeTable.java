package com.iut.ptut.model;

import java.util.Date;
import java.util.Vector;

public class TimeTable {

	private int idTT;
	private Date dateDebut;
	private Date dateFin;
	private Vector<Day> ListDay;

	public int getId() {
		return idTT;
	}

	public void setId(int pId) {
		this.idTT = pId;
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

	public Vector<Day> getListJour() {
		return this.ListDay;
	}

	public void setListJour(Vector<Day> pListJour) {
		this.ListDay = pListJour;
	}

	// ajout d'un jour pDay à la liste de jours ListDay
	public void addDay(Day pDay) {
		this.ListDay.add(pDay);
	}

	// suppression d'un jour pDay à la liste de jours ListDay
	public void removeDay(Day pDay) {
		this.ListDay.remove(pDay);
	}

	// suppression de TOUS les jours pDay de la liste de jours ListDay
	public void removeAllDays() {
		this.ListDay.removeAllElements();
	}

}
