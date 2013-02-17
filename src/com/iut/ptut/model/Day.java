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
	}
	
	//suppression d'un cours  dans une journée
	public void removeLesson(Lesson pLesson)
	{
		this.ListLesson.remove(pLesson);
	}
	
	//suppression de tous les cours d'une journée
	public void removeAllLessons(Lesson pLesson)
	{
		this.ListLesson.removeAllElements();
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
	
}
