import java.util.Date;
import java.util.Vector;


public class Day {

	
	
	private int idDay;
	private Date DateDebut;
	private Date DateFin;
	private Vector<Lesson> ListLesson;
	
	
	
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
	
	//ajout d'un cours  dans une journ�e
	public void addLesson(Lesson pLesson)
	{
		this.ListLesson.add(pLesson);
	}
	
	//suppression d'un cours  dans une journ�e
	public void removeLesson(Lesson pLesson)
	{
		this.ListLesson.remove(pLesson);
	}
	
	//suppression de tous les cours d'une journ�e
	public void removeAllLessons(Lesson pLesson)
	{
		this.ListLesson.removeAllElements();
	}
	
	
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
}