package com.iut.ptut.model.database;

import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.iut.ptut.MainActivity;
import com.iut.ptut.model.Day;
import com.iut.ptut.model.Group;
import com.iut.ptut.model.Lesson;
import com.iut.ptut.model.TimeTable;

/**
 * Cette classe fournit une collection de méthodes pour intéragir avec la base de données.
 * SINGLETON
 * @author Benoît Sauvère
 *
 */
public class DatabaseManager {
	
	private static DatabaseManager _instance;
	
	private SQLiteDatabase bdd;
	private DatabaseCreator bdd_creator;
	
	
	private class LessonTable {
		public static final String nom = "lesson";
		public static final String col_id = "idLesson";
		public static final String col_libelle = "libelle";
		public static final String col_date_debut = "dateDebut";
		public static final String col_date_fin = "dateFin";
		public static final String col_intervenant = "intervenant";
		public static final String col_emplacement = "emplacement";
		public static final String col_id_timetable = "idTT";
	}
	
	private class TimeTableTable{
		public static final String nom = "timetable";
		public static final String col_id = "idTT";
		public static final String col_date_debut = "dateDebut";
		public static final String col_date_fin = "dateFin";
		public static final String col_groupe = "groupe";
	}
	
	private class GroupTable {
		public static final String nom = "groupe";
		public static final String col_id = "idGroupe";
	}
	
	private DatabaseManager() {
		this.bdd_creator = new DatabaseCreator(MainActivity.context, null, 1);
	}
	
	public static DatabaseManager getInstance() {
		if(_instance == null) {
			_instance = new DatabaseManager();
		}
		
		return _instance;
	}
	
	/**
	 * Ouvre la base de données.
	 */
	public void open() {
		this.bdd = this.bdd_creator.getWritableDatabase();
	}
	
	/**
	 * Ferme la base de données.
	 */
	public void close() {
		this.bdd.close();
		this.bdd = null;
	}
	
	//
	//
	// GROUPS
	//
	//
	
	public static String formaterGroupe(Group g) {
		return g.getAnnee() + "-" + g.getSemestre() + "-" + g.getGroupe();
	}
	
	//
	//
	// TIMETABLES
	//
	//
	
	/**
	 * Insère un objet TimeTable dans la base de données. <br/>
	 * L'identifiant sera automatiquement rajouté dans l'objet.
	 * @param timetable Le TimeTable a insérer.
	 * @return L'id du TimeTable inséré (également rajouté dans l'objet).
	 * @throws Exception Est levée lorsque le groupe n'existe pas.
	 */
	public long insererTimeTable(TimeTable timetable) throws Exception {
		
		long nouvelId = -1;
		
		// Nécessaire pour convertir les date en texte au format SQL.
		// En effet les ContentValues ne peuvent recevoir directement un objet Date
		// pour mettre dans une colonne DATETIME, il faut le convertir en texte.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		// On contruit la liste des associations colonne -> valeur
		ContentValues vals = new ContentValues();
		vals.put(TimeTableTable.col_date_debut, dateFormat.format(timetable.getDateDebut()));
		vals.put(TimeTableTable.col_date_fin, dateFormat.format(timetable.getDateFin()));
		vals.put(TimeTableTable.col_groupe, formaterGroupe(timetable.getGroupe()));
		
		// On ajoute l'entrée
		nouvelId = this.bdd.insert(TimeTableTable.nom, null, vals);
		
		// Si il y a eu une erreur lors de l'ajout
		if(nouvelId < 0) {
			throw new Exception("Error lors de l'ajout du TimeTable [" + timetable + "]");
		}
		
		timetable.setId((int)nouvelId);
		
		// On insère tout les jours en leur ayant ajouté l'indentifiant du timetable.
		for(int i = 0 ; i < timetable.getListJour().size() ; i++) {
			Day t = timetable.getListJour().get(i);
			for(int j = 0 ; j < t.getListLesson().size() ; j++) {
				Lesson l = t.getListLesson().get(j);
				l.setIdTimeTable((int) nouvelId);
				this.insererLesson(l);
			}
		}
		
		return nouvelId;
	}
	
	//
	//
	// LESSONS
	//
	//
	
	/**
	 * Cette méthode insère un objet Lesson dans la base de données. <br/>
	 * <b>L'objet Lesson sera modifier pour y ajouter l'identifiant dans la base de données.</b>
	 * @param lesson L'objet Lesson à insérer.
	 * @return L'id dans la bdd de l'objet Lesson.
	 */
	public long insererLesson(Lesson lesson) {
		
		// Nécessaire pour convertir les date en texte au format SQL.
		// En effet les ContentValues ne peuvent recevoir directement un objet Date
		// pour mettre dans une colonne DATETIME, il faut le convertir en texte.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		// On contruit la liste des associations colonne -> valeur
		ContentValues vals = new ContentValues();
		vals.put(LessonTable.col_libelle, lesson.getLibelle());
		vals.put(LessonTable.col_date_debut, dateFormat.format(lesson.getDateDebut()));
		vals.put(LessonTable.col_date_fin, dateFormat.format(lesson.getDateFin()));
		vals.put(LessonTable.col_intervenant, lesson.getIntervenant());
		vals.put(LessonTable.col_emplacement, lesson.getEmplacement());
		vals.put(LessonTable.col_id_timetable, lesson.getIdTimeTable());
		
		// On insère la lesson.
		long nouvelId = this.bdd.insert(LessonTable.nom, null, vals);
		// On ajoute le nouvel id à l'objet Lesson.
		lesson.setIdLesson((int) nouvelId);
		// On retourne le nouvel id.
		return nouvelId;
	}
	
	/**
	 * Supprime un objet de type Lesson présent dans la base de données.<br/>
	 * La suppression se fait en se basant sur le champs "id" de l'objet.
	 * @param lesson La lesson à supprimer.
	 * @return True si la suppression s'est bien passée.
	 */
	public boolean supprimerLesson(Lesson lesson) {
		return this.bdd.delete(LessonTable.nom, LessonTable.col_id + "=" + lesson.getIdLesson(), null) > 0;
	}
	
	/**
	 * Met à jour un objet dans la base de données.<br/>
	 * La mise à jour se fait en se basant sur le champs "id" de l'objet.
	 * @param lesson La Lesson à modifier.
	 * @return True si la mise à jour s'est bien passée.
	 */
	public boolean majLesson(Lesson lesson) {
		
		// Nécessaire pour convertir les date en texte au format SQL.
		// En effet les ContentValues ne peuvent recevoir directement un objet Date
		// pour mettre dans une colonne DATETIME, il faut le convertir en texte.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		// On contruit la liste des associations colonne -> valeur
		ContentValues vals = new ContentValues();
		vals.put(LessonTable.col_libelle, lesson.getLibelle());
		vals.put(LessonTable.col_date_debut, dateFormat.format(lesson.getDateDebut()));
		vals.put(LessonTable.col_date_fin, dateFormat.format(lesson.getDateFin()));
		vals.put(LessonTable.col_intervenant, lesson.getIntervenant());
		vals.put(LessonTable.col_emplacement, lesson.getEmplacement());
		vals.put(LessonTable.col_id_timetable, lesson.getIdTimeTable());
		
		return bdd.update(LessonTable.nom, vals, LessonTable.col_id + "=" + lesson.getIdLesson(), null) > 0;
	}
	
	//
	//
	// GETTERS AND SETTERS
	//
	//
	
	
	
}
