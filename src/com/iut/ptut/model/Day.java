package com.iut.ptut.model;

import java.util.Date;
import java.util.Vector;

public class Day {

	private int idDay;
	private Date DateDebut;
	private Date DateFin;
	private Vector<Lesson> ListLesson;
	
	public Day() {
		this(0, new Date(), new Date(), new Vector<Lesson>());
	}
	
	public Day(int idDay, Date dateDebut, Date dateFin,Vector<Lesson> listLesson) {
		this.idDay = idDay;
		DateDebut = dateDebut;
		DateFin = dateFin;
		ListLesson = listLesson;
	}
	
	public int getIdDay() {
		return this.idDay;
	}
	public void setIdDay(int pIdDay) {
		this.idDay = pIdDay;
	}
	public Date getDateDebut() {
		return this.DateDebut;
	}
	public void setpDateDebut(Date pDateDebut) {
		this.DateDebut = pDateDebut;
	}
	public Date getDateFin() {
		return DateFin;
	}
	public void setpDateFin(Date pDateFin) {
		this.DateFin = pDateFin;
	}
	public Vector<Lesson> getListLesson() {
		return this.ListLesson;
	}
	public void setListLesson(Vector<Lesson> listLesson) {
		this.ListLesson = listLesson;
	}
	
	//ajout d'un cours  dans une journée
	public void addLesson(Lesson pLesson)
	{
		this.ListLesson.add(pLesson);
		recompterDates();
	}
	
	//suppression d'un cours  dans une journée
	public void removeLesson(Lesson pLesson)
	{
		this.ListLesson.remove(pLesson);
		recompterDates();
	}
	
	//suppression de tous les cours d'une journée
	public void removeAllLessons(Lesson pLesson)
	{
		this.ListLesson.removeAllElements();
		recompterDates();
	}
		
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("id=[%s] dateDebut=[%s] dateFin=[%s] \n", this.idDay, this.DateDebut, this.DateFin));
		for(Lesson cour : this.ListLesson) {
			builder.append("\t\t" + cour + "\n");
		}
		return builder.toString();
	}
	
	private void recompterDates() {
		this.DateDebut = new Date();
		this.DateFin = new Date();
		
		if(this.ListLesson.size() > 0) {
			Lesson jour0 = this.ListLesson.get(0);
			this.DateDebut = jour0.getDateDebut();
			this.DateFin = jour0.getDateFin();
			
			for(int i = 1; i < ListLesson.size(); i++) {
				Lesson jour = ListLesson.get(i);
				
				if(this.DateDebut.after(jour.getDateDebut()))
					this.DateDebut = jour.getDateDebut();
				if(this.DateFin.before(jour.getDateDebut())) 
					this.DateFin = jour.getDateFin();
			}
		}
	}
	
}
