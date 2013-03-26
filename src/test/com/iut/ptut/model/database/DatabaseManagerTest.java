package test.com.iut.ptut.model.database;

import java.io.IOException;
import java.net.MalformedURLException;

import net.fortuna.ical4j.data.ParserException;

import com.iut.ptut.ctrl.CRONFetcher;
import com.iut.ptut.model.database.DatabaseManager;
import com.iut.ptut.model.database.DatabaseManipulationException;

public class DatabaseManagerTest {
	
	public static void main (String args[]) throws MalformedURLException, IOException, ParserException, DatabaseManipulationException {
		
		DatabaseManager manager = DatabaseManager.getInstance();
		manager.open();
		
		CRONFetcher f = new CRONFetcher();
		
		f.recupererEtStockerEdT("04", "13");
		
	}
	
	
}
