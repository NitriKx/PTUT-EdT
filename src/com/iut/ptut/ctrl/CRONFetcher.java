package com.iut.ptut.ctrl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.fortuna.ical4j.data.ParserException;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.widget.Toast;

import com.buzzbox.mob.android.scheduler.Task;
import com.buzzbox.mob.android.scheduler.TaskResult;
import com.iut.ptut.model.ConfigManager;
import com.iut.ptut.model.Group;
import com.iut.ptut.model.TimeTable;
import com.iut.ptut.model.database.DatabaseManager;
import com.iut.ptut.model.database.DatabaseManipulationException;
import com.iut.ptut.view.MainActivity;

public class CRONFetcher extends AsyncTask<String, Void, Void> implements Task {

	private Logger _log = Logger.getLogger(this.getClass().getName());
	
	public TaskResult doWork(ContextWrapper arg0) {

		_log.log(Level.INFO, "Lancement du CRON de r�cup�ration de l'emploi du temps.");
		
		TaskResult result = new TaskResult();
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		
		try {
			
			DatabaseManager manager = DatabaseManager.getInstance();
			manager.open();
			
			CRONFetcher f = new CRONFetcher();
			f.recupererEtStockerEdT(ConfigManager.getInstance().getProperty("user_semestre"), ""+cal.get(Calendar.WEEK_OF_YEAR));
			// On ajoute une semaine
			cal.add(Calendar.WEEK_OF_YEAR, 1);
			f.recupererEtStockerEdT(ConfigManager.getInstance().getProperty("user_semestre"), ""+cal.get(Calendar.WEEK_OF_YEAR));
			
		} catch (Exception e) {
			_log.log(Level.WARNING, "Erreur lors de la r�cup�ration de l'emploi du temps. Message = [" + e.getMessage() + "]");
		}
				
		_log.log(Level.FINE, "CRON termin� !");
		
		return result;
	}
	
	@Override
	protected Void doInBackground(String...params) {
		
		// On appelle la routine de r�cup�ration
		this.doWork(null);
		
		return null;
	}
	
	/**
	 * R�cup�re et stocke dans la base de donn�es l'emplois du temps de la semaine pass�e en param�tre.
	 * @param semestre Le num�ro du semestre dont on veut l'emploi du temps.
	 * @param noSemaine Le num�ro de la semaine � r�cup�r�.
	 * @throws ParserException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws DatabaseManipulationException 
	 * @throws Exception 
	 */
	public void recupererEtStockerEdT(String semestre, String noSemaine) throws MalformedURLException, IOException, ParserException, DatabaseManipulationException  {
		
		DatabaseManager manager = DatabaseManager.getInstance();
		
		TimeTableFetcher fetcher = new TimeTableFetcher();
		Group groupUtilisateur = new Group(
				ConfigManager.getInstance().getProperty("user_group"), 
				Integer.parseInt(ConfigManager.getInstance().getProperty("user_semestre")), 
				CalendarParser.getAnneeScolairePourDate(new Date()));
		
		_log.log(Level.INFO, String.format("R�cup�ration du fichier ICS pour semestre=[%s] semaine=[%s] groupe=[%s]...", semestre, noSemaine, groupUtilisateur));
		
		TimeTable resultat = fetcher.recuperer(semestre, noSemaine, groupUtilisateur);
		
		manager.open();
		// On met � jour le TimeTable pour la semaine et le groupe contenu dans le r�sultat.
		manager.majTimeTable(resultat);
		manager.close();
		
	}
	
	
	
	
	
	
	
	
	
	

	public String getId() {
		return "cron_fetcher";
	}

	public String getTitle() {
		return "IUT BLagnac EdT Fetcher";
	}


	

}
