package com.iut.ptut.model.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iut.ptut.MainActivity;
import com.iut.ptut.ctrl.DateTools;
import com.iut.ptut.model.Day;
import com.iut.ptut.model.Group;
import com.iut.ptut.model.Lesson;
import com.iut.ptut.model.TimeTable;

/**
 * Cette classe fournit une collection de m�thodes pour int�ragir avec la base
 * de donn�es. SINGLETON
 * 
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

	private class TimeTableTable {
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
		if (_instance == null) {
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

	/**
	 * Ins�re le groupe s'il ne se trouve pas d�j� dans la base de donn�es.
	 * 
	 * @param groupe
	 *            Le groupe � tester
	 * @return -1 si le groupe existe d�j�, le nouvel rowid du groupe si il
	 *         n'existait pas.
	 * @throws DatabaseManipulationException
	 */
	public long insererGroupeSiIlNExistePas(Group groupe) throws DatabaseManipulationException {
		// On r�cup�re la liste des Group dans la bdd
		List<Group> list = this.getAllGroups();

		// Si le groupe n'y est pas
		if (!list.contains(groupe))
			return this.insererGroupe(groupe);

		return -1;
	}

	/**
	 * Ins�re un groupe dans la base de donn�es.
	 * 
	 * @param groupe
	 *            Le groupe � ajouter.
	 * @return L'id du groupe (rowid)
	 * @throws DatabaseManipulationException
	 *             Exception lev�e si ce n'est pas possible d'ins�rer le groupe.
	 *             (d�j� pr�sent, ...)
	 */
	public long insererGroupe(Group groupe) throws DatabaseManipulationException {

		long nouvelId = -1;

		// On contruit la liste des associations colonne -> valeur
		ContentValues vals = new ContentValues();
		vals.put(GroupTable.col_id, formaterGroupe(groupe));

		// On ajoute le groupe
		nouvelId = this.bdd.insert(GroupTable.nom, null, vals);

		// Si l'id est inf�rieur � -1 il y une erreur
		if (nouvelId < 0) {
			throw new DatabaseManipulationException("Error lors de l'ajout du Group=[" + groupe + "]");
		}

		_log.log(Level.INFO, "Le Group=[" + groupe + "] � bien �t� ins�r�.");

		return nouvelId;
	}

	/**
	 * Supprime le groupe de la base de donn�es. Cette m�thode se base sur les
	 * champs du groupe pour d�terminer l'id de la ligne � supprimer.
	 * 
	 * @param groupe
	 *            Le groupe � supprimer.
	 * @throws DatabaseManipulationException
	 *             Lev� si il y a une erreur lors de la supprission (existe pas,
	 *             ...)
	 */
	public void supprimerGroupe(Group groupe) throws DatabaseManipulationException {
		// On tente de supprimer le groupe
		if (this.bdd.delete(GroupTable.nom, GroupTable.col_id + " = '" + formaterGroupe(groupe) + "'", null) == 0)
			throw new DatabaseManipulationException("Impossible de supprimer le Group=[" + groupe + "]");
		else
			_log.log(Level.INFO, "Le Group=[" + groupe + "] � bien �t� supprim�.");
	}

	/**
	 * R�cup�re la liste des groupes pr�sents dans la base de donn�es.
	 * 
	 * @return La liste de Group.
	 */
	public List<Group> getAllGroups() {
		List<Group> list = new ArrayList<Group>();
		// On r�cup�re un Cursor contenant toute les lignes
		Cursor c = this.bdd.query(GroupTable.nom, new String[] { GroupTable.col_id }, null, null, null, null, null);

		// Pour ne pas perdre le premier enregistrement
		list.add(lireGroupe(c.getString(0)));
		while (c.moveToNext())
			list.add(lireGroupe(c.getString(0)));

		// On ferme le cursor pour lib�rer les ressources
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
	 * Ins�re un objet TimeTable dans la base de donn�es. <br/>
	 * L'identifiant sera <b>automatiquement rajout�</b> dans l'objet.
	 * 
	 * @param timetable
	 *            Le TimeTable a ins�rer.
	 * @return L'id du TimeTable ins�r� (�galement rajout� dans l'objet).
	 * @throws DatabaseManipulationException 
	 * 				Est lev�e lorsque le groupe n'existe pas.
	 *             
	 */
	public long insererTimeTable(TimeTable timetable) throws DatabaseManipulationException {

		long nouvelId = -1;

		// Si le groupe du timetable n'existe pas on l'ajoute.
		this.insererGroupeSiIlNExistePas(timetable.getGroupe());

		// N�cessaire pour convertir les date en texte au format SQL.
		// En effet les ContentValues ne peuvent recevoir directement un objet
		// Date
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
		if (nouvelId < 0) {
			throw new DatabaseManipulationException("Error lors de l'ajout du TimeTable [" + timetable + "]");
		}

		_log.log(Level.INFO, "Le TimeTable=[" + timetable + "] � bien �t� ins�r�.");

		timetable.setId((int) nouvelId);

		// On ins�re tout les jours en leur ayant ajout� l'indentifiant du
		// timetable.
		for (int i = 0; i < timetable.getListJour().size(); i++) {
			Day t = timetable.getListJour().get(i);
			for (int j = 0; j < t.getListLesson().size(); j++) {
				Lesson l = t.getListLesson().get(j);
				l.setIdTimeTable((int) nouvelId);
				this.insererLesson(l);
			}
		}

		return nouvelId;
	}

	/**
	 * R�cup�re un TimeTable depuis la base de donn�es.
	 * 
	 * @param groupe
	 *            Le groupe du TimeTable
	 * @param semaine
	 *            La semaine de cours que l'on souhaite r�cup�rer
	 * @param annee
	 *            L'ann�e de cette semaine
	 * @return Une TimeTable correctement remplis ou null si aucun TimeTable n'a �t� trouv�
	 * @throws ParseException 
	 */
	public TimeTable recupererTimeTable(Group groupe, int semaine, int annee) throws ParseException {
		
		// N�cessaire pour convertir les date en texte au format SQL.
		// En effet les ContentValues ne peuvent recevoir directement un objet
		// Date
		// pour mettre dans une colonne DATETIME, il faut le convertir en texte.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		
		Date debutSemaine = DateTools.calculeTimestampDebutSemaine(semaine, annee);
		Date finSemaine = DateTools.calculeTimestampFinSemaine(semaine, annee);
		
		String selectionSQL = "dateDebut >= " + dateFormat.format(debutSemaine) +
				" AND " +
				"dateFin <= " + dateFormat.format(finSemaine) +
				" AND " +
				"groupe = " + formaterGroupe(groupe);
		
		// On r�cup�re un Cursor contenant l'emploi du temps
		Cursor cTT = this.bdd.query(TimeTableTable.nom, null, selectionSQL, null, null, null, null);
		
		// Si le TimeTable n'existe pas on retourne null
		if(cTT.getCount() <= 0) {
			return null;
		}
		
		// On cr�er un TimeTable (sans les jours) avec les donn�es de la base
		TimeTable timeTable = new TimeTable(
				cTT.getInt(cTT.getColumnIndex(TimeTableTable.col_id)), 
				dateFormat.parse(cTT.getString(cTT.getColumnIndex(TimeTableTable.col_date_debut))), 
				dateFormat.parse(cTT.getString(cTT.getColumnIndex(TimeTableTable.col_date_fin))), 
				new Vector<Day>(), 
				lireGroupe(cTT.getString(cTT.getColumnIndex(TimeTableTable.col_groupe))));
		
		// On r�cup�re le sjours associ� au TimeTable
		selectionSQL = LessonTable.col_id_timetable + " = " + timeTable.getId();
		Cursor cLesson = this.bdd.query(LessonTable.nom, null, selectionSQL, null, null, null, null);
		
		// Pour les 5 jours de cours
		for(int i = 2; i < 2+5; i++) {
			
			Day jour = new Day();
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, annee);
			cal.set(Calendar.WEEK_OF_YEAR, semaine);
			// Ici le i est une valeur de l'�numrtion des jour de la semaine de Calendar (2 = MONDAY, ...)
			cal.set(Calendar.DAY_OF_WEEK, i);
			
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date debutJour = cal.getTime();
			
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			Date finJour = cal.getTime();
			
			// Pour toute les lesson recup�r�es
			for (int j = 0; j < cLesson.getCount(); j++) {
				
				// On convertit les date du jour selectionn�
				Date debutCours = dateFormat.parse(cLesson.getString(cLesson.getColumnIndex(LessonTable.col_date_debut)));
				Date finCours = dateFormat.parse(cLesson.getString(cLesson.getColumnIndex(LessonTable.col_date_fin)));

				// Si le cours est dans le jour actuel
				if(debutJour.before(debutCours) && finJour.after(finCours)) {
					// On cr�er la Lesson et on l'ajout au jour.
					Lesson l = new Lesson(
							cLesson.getInt(cLesson.getColumnIndex(LessonTable.col_id)), 
							cLesson.getString(cLesson.getColumnIndex(LessonTable.col_libelle)), 
							cLesson.getString(cLesson.getColumnIndex(LessonTable.col_intervenant)), 
							cLesson.getString(cLesson.getColumnIndex(LessonTable.col_emplacement)), 
							debutCours, 
							finCours, 
							timeTable.getId(), 
							timeTable.getGroupe());
					jour.addLesson(l);
				}
				
			}
			
			// On reset le curseur
			cLesson.moveToFirst();
			timeTable.addDay(jour);
		}
		
		
		return timeTable;
	}

	//
	//
	// LESSONS
	//
	//

	/**
	 * R�cup�re la liste des Lessons pour une p�riode donn�e.
	 * 
	 * @param debut
	 *            Le d�but de la pr�iode (compris)
	 * @param fin
	 *            La fin de la p�riode (non compris)
	 * @return Une liste d'objet Lesson o� le cours se d�roule dans la p�riode.
	 * @throws DatabaseManipulationException 
	 */
	public List<Lesson> getListeLessonPourPeriode(Date debut, Date fin) throws DatabaseManipulationException {
		List<Lesson> resultat = new ArrayList<Lesson>();
		SimpleDateFormat parserSQLite = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
		
		Cursor c = this.bdd
				.query(LessonTable.nom, new String[] {}, LessonTable.col_date_debut + " > datetime(" + (debut.getTime() / 100) + ", 'unixepoch', 'localtime')", null, null, null, null, null);

		// Si on a des r�sultats
		if(c.getCount() > 0) {
			do {
				try {
					
					// On cr�er l'objet Lesson et on l'ajout � la liste de r�sultats
					resultat.add(new Lesson(
							c.getInt(c.getColumnIndex(LessonTable.col_id)),
							c.getString(c.getColumnIndex(LessonTable.col_libelle)),
							c.getString(c.getColumnIndex(LessonTable.col_intervenant)),
							c.getString(c.getColumnIndex(LessonTable.col_emplacement)),
							parserSQLite.parse(c.getString(c.getColumnIndex(LessonTable.col_date_debut))),
							parserSQLite.parse(c.getString(c.getColumnIndex(LessonTable.col_date_fin))),
							c.getInt(c.getColumnIndex(LessonTable.col_id_timetable)),
							new Group()
					));
					
				} catch (ParseException e) {
					throw new DatabaseManipulationException("Erreur lors de la lecture de la date pour le Lesson.");
				}
			} while (c.moveToNext());
		}
		
		c.close();
		
		return resultat;
	}

	/**
	 * Cette m�thode ins�re un objet Lesson dans la base de donn�es. <br/>
	 * <b>L'objet Lesson sera modifier pour y ajouter l'identifiant dans la base
	 * de donn�es.</b>
	 * 
	 * @param lesson
	 *            L'objet Lesson � ins�rer.
	 * @return L'id dans la bdd de l'objet Lesson.
	 * @throws DatabaseManipulationException
	 */
	public long insererLesson(Lesson lesson) throws DatabaseManipulationException {

		// N�cessaire pour convertir les date en texte au format SQL.
		// En effet les ContentValues ne peuvent recevoir directement un objet
		// Date
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

		if (nouvelId < 0) {
			throw new DatabaseManipulationException("Error lors de l'ajout de la Lesson=[" + lesson + "] voir les logs.");
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
	 * 
	 * @param lesson
	 *            La lesson � supprimer.
	 * @throws DatabaseManipulationException
	 */
	public void supprimerLesson(Lesson lesson) throws DatabaseManipulationException {
		if (this.bdd.delete(LessonTable.nom, LessonTable.col_id + "=" + lesson.getIdLesson(), null) == 0)
			throw new DatabaseManipulationException("Impossible de supprimer cette Lesson=[" + lesson + "]");
		else
			_log.log(Level.INFO, "La Lesson=[" + lesson + "] � bien �t� supprim�.");
	}

	/**
	 * Met � jour un objet dans la base de donn�es.<br/>
	 * La mise � jour se fait en se basant sur le champs "id" de l'objet.
	 * 
	 * @param lesson
	 *            La Lesson � modifier.
	 * @return True si la mise � jour s'est bien pass�e.
	 */
	public boolean majLesson(Lesson lesson) {

		// N�cessaire pour convertir les date en texte au format SQL.
		// En effet les ContentValues ne peuvent recevoir directement un objet
		// Date
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
