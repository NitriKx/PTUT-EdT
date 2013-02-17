package com.iut.ptut.model;

import java.util.Date;
import java.util.Vector;

public class TimeTable {

	private int idTT;
	private Date dateDebut;
	private Date dateFin;
	private Vector<Day> ListDay;

	public TimeTable() {
		this.idTT = 0;
		this.dateDebut = new Date();
		this.dateFin = new Date();
		this.ListDay = new Vector<Day>();
	}
	
	public TimeTable(int idTT, Date dateDebut, Date dateFin, Vector<Day> listDay) {
		this.idTT = idTT;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		ListDay = listDay;
	}

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

	// ajout d'un jour pDay � la liste de jours ListDay
	public void addDay(Day pDay) {
		this.ListDay.add(pDay);
		this.recompterDates();
	}

	// suppression d'un jour pDay � la liste de jours ListDay
	public void removeDay(Day pDay) {
		this.ListDay.remove(pDay);
		this.recompterDates();
	}

	// suppression de TOUS les jours pDay de la liste de jours ListDay
	public void removeAllDays() {
		this.ListDay.removeAllElements();
		this.recompterDates();
	}
	
	/**
	 * Cette m�thode recompte les limites des dates dans la liste des jours.
	 */
	private void recompterDates() {
		this.dateDebut = new Date();
		this.dateFin = new Date();
		
		if(this.ListDay.size() > 0) {
			Day jour0 = this.ListDay.get(0);
			this.dateDebut = jour0.getDateDebut();
			this.dateFin = jour0.getDateFin();
			
			for(int i = 1; i < ListDay.size(); i++) {
				Day jour = ListDay.get(i);
				
				if(this.dateDebut.after(jour.getDateDebut()))
					this.dateDebut = jour.getDateDebut();
				if(this.dateFin.before(jour.getDateDebut())) 
					this.dateFin = jour.getDateFin();
			}
		}
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("id=[%s] dateDebut=[%s] dateFin=[%s] \n", this.idTT, this.dateDebut, this.dateFin));
		for(Day jour : this.ListDay) {
			builder.append("\t" + jour);
		}
		return builder.toString();
	}

}
