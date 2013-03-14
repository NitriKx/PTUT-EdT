package com.iut.ptut.model.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.ContentValues;
import android.database.Cursor;
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
	
	/**
	 * Insère le groupe s'il ne se trouve pas déjà dans la base de données.
	 * @param groupe Le groupe à tester
	 * @return -1 si le groupe existe déjà, le nouvel rowid du groupe si il n'existait pas.
	 * @throws CannotInsertException
	 */
	public long insererGroupeSiIlNExistePas(Group groupe) throws CannotInsertException {
		// On récupère la liste des Group dans la bdd
		List<Group> list = this.getAllGroups();
		
		// Si le groupe n'y est pas
		if(!list.contains(groupe))
			return this.insererGroupe(groupe);
		
		return -1;
	}
	
	/**
	 * Insère un groupe dans la base de données.
	 * @param groupe Le groupe à ajouter.
	 * @return L'id du groupe (rowid)
	 * @throws CannotInsertException Exception levée si ce n'est pas possible d'insérer le groupe. (déjà présent, ...)
	 */
	public long insererGroupe(Group groupe) throws CannotInsertException {
		
		long nouvelId = -1;
		
		// On contruit la liste des associations colonne -> valeur
		ContentValues vals = new ContentValues();
		vals.put(GroupTable.col_id, formaterGroupe(groupe));
		
		// On ajoute le groupe
		nouvelId = this.bdd.insert(GroupTable.nom, null, vals);
		
		// Si l'id est inférieur à -1 il y une erreur
		if(nouvelId < 0) {
			throw new CannotInsertException("Error lors de l'ajout du Group=[" + groupe  +"]");
		}
		
		_log.log(Level.INFO, "Le Group=[" + groupe + "] à bien été inséré.");
		
		return nouvelId;
	}
	
	/**
	 * Supprime le groupe de la base de données.
	 * Cette méthode se base sur les champs du groupe pour déterminer l'id de la ligne à supprimer.
	 * @param groupe Le groupe à supprimer.
	 * @throws CannotDeleteException Levé si il y a une erreur lors de la supprission (existe pas, ...)
	 */
	public void supprimerGroupe(Group groupe) throws CannotDeleteException{
		// On tente de supprimer le groupe
		if(this.bdd.delete(GroupTable.nom, GroupTable.col_id + " = '" + formaterGroupe(groupe) + "'", null) == 0)
			throw new CannotDeleteException("Impossible de supprimer le Group=[" + groupe + "]");
		else
			_log.log(Level.INFO, "Le Group=[" + groupe + "] à bien été supprimé.");
	}
	
	/**
	 * Récupère la liste des groupes présents dans la base de données.
	 * @return La liste de Group.
	 */
	public List<Group> getAllGroups() {
		List<Group> list = new ArrayList<Group>();
		// On récupère un Cursor contenant toute les lignes
		Cursor c = this.bdd.query(GroupTable.nom, new String[]{GroupTable.col_id}, null, null, null, null, null);
		while(c.moveToNext())
			list.add(lireGroupe(c.getString(0)));
		c.close();
		return list;
	}
	
	
	public static String formaterGroupe(Group g) {
		return g.getAnnee() + "-" + g.getSemestre() + "-" + g.getGroupe();
	}
	
	public static Group lireGroupe(String s) {
		String[] l = s.split("-");
		return new Group(l[2], Integer.parseInt(l[1]), Integer.parseInt(l[0]));
	}
	
	//
	//
	// TIMETABLES
	//
	//
	
	/**
	 * Insère un objet TimeTable dans la base de données. <br/>
	 * L'identifiant sera <b>automatiquement rajouté</b> dans l'objet.
	 * @param timetable Le TimeTable a insérer.
	 * @return L'id du TimeTable inséré (également rajouté dans l'objet).
	 * @throws Exception Est levée lorsque le groupe n'existe pas.
	 */
	public long insererTimeTable(TimeTable timetable) throws Exception {
		
		long nouvelId = -1;
		
		// Si le groupe du timetable n'existe pas on l'ajoute.
		this.insererGroupeSiIlNExistePas(timetable.getGroupe());
		
		// Nécessaire pour convertir les date en texte au format SQL.
		// En effet les ContentValues ne peuvent recevoir directement un objet Date
		// pour mettre dans une colonne DATETIME, il faut le convertir en texte.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()); 
		
		// On contruit la liste des associations colonne -> valeur
		ContentValues vals = new ContentValues();
		vals.put(TimeTableTable.col_date_debut, dateFormat.format(timetable.getDateDebut()));
		vals.put(TimeTableTable.col_date_fin, dateFormat.format(timetable.getDateFin()));
		vals.put(TimeTableTable.col_groupe, formaterGroupe(timetable.getGroupe()));
		
		// On ajoute l'entrée
		nouvelId = this.bdd.insert(TimeTableTable.nom, null, vals);
		
		// Si il y a eu une erreur lors de l'ajout
		if(nouvelId < 0) {
			throw new CannotInsertException("Error lors de l'ajout du TimeTable [" + timetable + "]");
		}
		
		_log.log(Level.INFO, "Le TimeTable=[" + timetable + "] à bien été inséré.");
		
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
	 * @throws CannotInsertException 
	 */
	public long insererLesson(Lesson lesson) throws CannotInsertException {
		
		// Nécessaire pour convertir les date en texte au format SQL.
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
		
		// On insère la lesson.
		long nouvelId = this.bdd.insert(LessonTable.nom, null, vals);
		
		if(nouvelId < 0) {
			throw new CannotInsertException("Error lors de l'ajout de la Lesson=[" + lesson + "] voir les logs.");
		}

		_log.log(Level.INFO, "La Lesson=[" + lesson + "] à bien été inséré.");
		
		// On ajoute le nouvel id à l'objet Lesson.
		lesson.setIdLesson((int) nouvelId);
		// On retourne le nouvel id.
		return nouvelId;
	}
	
	/**
	 * Supprime un objet de type Lesson présent dans la base de données.<br/>
	 * La suppression se fait en se basant sur le champs "id" de l'objet.
	 * @param lesson La lesson à supprimer.
	 * @throws CannotDeleteException 
	 */
	public void supprimerLesson(Lesson lesson)  throws CannotDeleteException {
		if(this.bdd.delete(LessonTable.nom, LessonTable.col_id + "=" + lesson.getIdLesson(), null) == 0)
			throw new CannotDeleteException("Impossible de supprimer cette Lesson=[" + lesson + "]");
		else
			_log.log(Level.INFO, "La Lesson=[" + lesson + "] à bien été supprimé.");
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
