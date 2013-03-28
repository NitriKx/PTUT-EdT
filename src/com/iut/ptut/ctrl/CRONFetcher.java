package com.iut.ptut.ctrl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.fortuna.ical4j.data.ParserException;
import android.content.ContextWrapper;
import android.os.AsyncTask;

import com.buzzbox.mob.android.scheduler.Task;
import com.buzzbox.mob.android.scheduler.TaskResult;
import com.iut.ptut.model.ConfigManager;
import com.iut.ptut.model.Group;
import com.iut.ptut.model.TimeTable;
import com.iut.ptut.model.database.DatabaseManager;
import com.iut.ptut.model.database.DatabaseManipulationException;

public class CRONFetcher extends AsyncTask<String, Void, Void> implements Task {

	private Logger _log = Logger.getLogger(this.getClass().getName());
	
	public TaskResult doWork(ContextWrapper arg0) {

		_log.log(Level.INFO, "Lancement du CRON de récupération de l'emploi du temps.");
		
		TaskResult result = new TaskResult();
		
		_log.log(Level.FINE, "CRON terminé !");
		
		return result;
	}
	
	@Override
	protected Void doInBackground(String...params) {
		
		// TEST // 
		try {
			
			DatabaseManager manager = DatabaseManager.getInstance();
			manager.open();
			
			CRONFetcher f = new CRONFetcher();
			f.recupererEtStockerEdT("04", "13");
			f.recupererEtStockerEdT("04", "14");
			
		} catch (Exception e) {
			_log.log(Level.WARNING, "Erreur lors de la récupération de l'emploi du temps. Message = [" + e.getMessage() + "]");
		}
		
		return null;
	}
	
	/**
	 * Récupère et stocke dans la base de données l'emplois du temps de la semaine passée en paramètre.
	 * @param semestre Le numéro du semestre dont on veut l'emploi du temps.
	 * @param noSemaine Le numéro de la semaine à récupéré.
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
		
		_log.log(Level.INFO, String.format("Récupération du fichier ICS pour semestre=[%s] semaine=[%s] groupe=[%s]...", semestre, noSemaine, groupUtilisateur));
		
		TimeTable resultat = fetcher.recuperer(semestre, noSemaine, groupUtilisateur);
		
		manager.open();
		// On met à jour le TimeTable pour la semaine et le groupe contenu dans le résultat.
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
