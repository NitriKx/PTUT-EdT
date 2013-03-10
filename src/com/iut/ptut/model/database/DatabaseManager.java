package com.iut.ptut.model.database;

import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.iut.ptut.MainActivity;
import com.iut.ptut.model.Lesson;

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
		vals.put(LessonTable.col_id_timetable, lesson.get);
		// On insère la lesson
		long nouvelId = this.bdd.insert(LessonTable.nom, null, vals);
		
		return 0L;
	}
	
	/**
	 * Supprime un objet de type Lesson présent dans la base de données.<br/>
	 * La suppression se fait en se basant sur le champs "id" de l'objet.
	 * @param lesson La lesson à supprimer.
	 * @return True si la suppression s'est bien passée.
	 */
	public boolean supprimerLesson(Lesson lesson) {
		return false;
	}
	
	/**
	 * Met à jour un objet dans la base de données.<br/>
	 * La mise à jour se fait en se basant sur le champs "id" de l'objet.
	 * @param lesson La Lesson à modifier.
	 * @return True si la mise à jour s'est bien passée.
	 */
	public boolean majLesson(Lesson lesson) {
		return false;
	}
	
	//
	//
	// GETTERS AND SETTERS
	//
	//
	
	
	
}
