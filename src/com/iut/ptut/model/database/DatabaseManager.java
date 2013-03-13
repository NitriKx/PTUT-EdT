package com.iut.ptut.model.database;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.ContentValues;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.iut.ptut.MainActivity;
import com.iut.ptut.model.Day;
import com.iut.ptut.model.Group;
import com.iut.ptut.model.Lesson;
import com.iut.ptut.model.TimeTable;

/**
 * Cette classe fournit une collection de m�thodes pour int�ragir avec la base de donn�es.
 * SINGLETON
 * @author Beno�t Sauv�re
 *
 */
public class DatabaseManager {
	
	private Logger _log = Logger.getLogger(this.getClass().getName());
	
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
	 * Ouvre la base de donn�es.
	 */
	public void open() {
		this.bdd = this.bdd_creator.getWritableDatabase();
	}
	
	/**
	 * Ferme la base de donn�es.
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
	
	public long insererGroupe(Group groupe) throws CannotInsertException {
		
		long nouvelId = -1;
		
		// On contruit la liste des associations colonne -> valeur
		ContentValues vals = new ContentValues();
		vals.put(GroupTable.col_id, formaterGroupe(groupe));
		
		nouvelId = this.bdd.insert(GroupTable.nom, null, vals);
		
		if(nouvelId < 0) {
			throw new CannotInsertException("Error lors de l'ajout du Group=[" + groupe  +"]");
		}
		
		_log.log(Level.INFO, "Le Group=[" + groupe + "] � bien �t� ins�r�.");
		
		return nouvelId;
	}
	
	public void supprimerGroupe(Group groupe) throws CannotDeleteException{
		if(this.bdd.delete(GroupTable.nom, GroupTable.col_id + " = '" + formaterGroupe(groupe) + "'", null) == 0)
			throw new CannotDeleteException("Impossible de supprimer le Group=[" + groupe + "]");
		else
			_log.log(Level.INFO, "Le Group=[" + groupe + "] � bien �t� supprim�.");
	}
	
	public static String formaterGroupe(Group g) {
		return g.getAnnee() + "-" + g.getSemestre() + "-" + g.getGroupe();
	}
	
	//
	//
	// TIMETABLES
	//
	//
	
	/**
	 * Ins�re un objet TimeTable dans la base de donn�es. <br/>
	 * L'identifiant sera automatiquement rajout� dans l'objet.
	 * @param timetable Le TimeTable a ins�rer.
	 * @return L'id du TimeTable ins�r� (�galement rajout� dans l'objet).
	 * @throws Exception Est lev�e lorsque le groupe n'existe pas.
	 */
	public long insererTimeTable(TimeTable timetable) throws Exception {
		
		long nouvelId = -1;
		
		// N�cessaire pour convertir les date en texte au format SQL.
		// En effet les ContentValues ne peuvent recevoir directement un objet Date
		// pour mettre dans une colonne DATETIME, il faut le convertir en texte.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()); 
		
		// On contruit la liste des associations colonne -> valeur
		ContentValues vals = new ContentValues();
		vals.put(TimeTableTable.col_date_debut, dateFormat.format(timetable.getDateDebut()));
		vals.put(TimeTableTable.col_date_fin, dateFormat.format(timetable.getDateFin()));
		vals.put(TimeTableTable.col_groupe, formaterGroupe(timetable.getGroupe()));
		
		// On ajoute l'entr�e
		nouvelId = this.bdd.insert(TimeTableTable.nom, null, vals);
		
		// Si il y a eu une erreur lors de l'ajout
		if(nouvelId < 0) {
			throw new CannotInsertException("Error lors de l'ajout du TimeTable [" + timetable + "]");
		}
		
		_log.log(Level.INFO, "Le TimeTable=[" + timetable + "] � bien �t� ins�r�.");
		
		timetable.setId((int)nouvelId);
		
		// On ins�re tout les jours en leur ayant ajout� l'indentifiant du timetable.
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
	 * Cette m�thode ins�re un objet Lesson dans la base de donn�es. <br/>
	 * <b>L'objet Lesson sera modifier pour y ajouter l'identifiant dans la base de donn�es.</b>
	 * @param lesson L'objet Lesson � ins�rer.
	 * @return L'id dans la bdd de l'objet Lesson.
	 * @throws CannotInsertException 
	 */
	public long insererLesson(Lesson lesson) throws CannotInsertException {
		
		// N�cessaire pour convertir les date en texte au format SQL.
		// En effet les ContentValues ne peuvent recevoir directement un objet Date
		// pour mettre dans une colonne DATETIME, il faut le convertir en texte.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()); 
		
		// On contruit la liste des associations colonne -> valeur
		ContentValues vals = new ContentValues();
		vals.put(LessonTable.col_libelle, lesson.getLibelle());
		vals.put(LessonTable.col_date_debut, dateFormat.format(lesson.getDateDebut()));
		vals.put(LessonTable.col_date_fin, dateFormat.format(lesson.getDateFin()));
		vals.put(LessonTable.col_intervenant, lesson.getIntervenant());
		vals.put(LessonTable.col_emplacement, lesson.getEmplacement());
		vals.put(LessonTable.col_id_timetable, lesson.getIdTimeTable());
		
		// On ins�re la lesson.
		long nouvelId = this.bdd.insert(LessonTable.nom, null, vals);
		
		if(nouvelId < 0) {
			throw new CannotInsertException("Error lors de l'ajout de la Lesson=[" + lesson + "] voir les logs.");
		}

		_log.log(Level.INFO, "La Lesson=[" + lesson + "] � bien �t� ins�r�.");
		
		// On ajoute le nouvel id � l'objet Lesson.
		lesson.setIdLesson((int) nouvelId);
		// On retourne le nouvel id.
		return nouvelId;
	}
	
	/**
	 * Supprime un objet de type Lesson pr�sent dans la base de donn�es.<br/>
	 * La suppression se fait en se basant sur le champs "id" de l'objet.
	 * @param lesson La lesson � supprimer.
	 * @throws CannotDeleteException 
	 */
	public void supprimerLesson(Lesson lesson)  throws CannotDeleteException {
		if(this.bdd.delete(LessonTable.nom, LessonTable.col_id + "=" + lesson.getIdLesson(), null) == 0)
			throw new CannotDeleteException("Impossible de supprimer cette Lesson=[" + lesson + "]");
		else
			_log.log(Level.INFO, "La Lesson=[" + lesson + "] � bien �t� supprim�.");
	}
	
	/**
	 * Met � jour un objet dans la base de donn�es.<br/>
	 * La mise � jour se fait en se basant sur le champs "id" de l'objet.
	 * @param lesson La Lesson � modifier.
	 * @return True si la mise � jour s'est bien pass�e.
	 */
	public boolean majLesson(Lesson lesson) {
		
		// N�cessaire pour convertir les date en texte au format SQL.
		// En effet les ContentValues ne peuvent recevoir directement un objet Date
		// pour mettre dans une colonne DATETIME, il faut le convertir en texte.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()); 
		
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
