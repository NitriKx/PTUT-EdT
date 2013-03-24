package test.com.iut.ptut.ctrl;

import java.io.IOException;
import java.net.MalformedURLException;

import net.fortuna.ical4j.data.ParserException;

import com.iut.ptut.ctrl.TimeTableFetcher;
import com.iut.ptut.model.Group;
import com.iut.ptut.model.TimeTable;

public class TimeTableFetcherTest {

	public static void main(String args[]) throws MalformedURLException, IOException, ParserException {
		
		TimeTableFetcher fetcher = new TimeTableFetcher();
		TimeTable resultat = fetcher.recuperer("4", "12", new Group("2B", 4, 2012));
		
		System.out.println(resultat);
	}

}
