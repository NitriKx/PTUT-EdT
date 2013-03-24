package test.com.iut.ptut.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.iut.ptut.ctrl.CalendarParser;
import com.iut.ptut.model.Group;
import com.iut.ptut.model.Lesson;
import com.iut.ptut.model.ParsingProblemException;

/**
 * Classe de test de la classe CalendarParser.
 * @author Benoît Sauvère
 */
public class CalendarParserTest extends TestCase {
	
	/**
	 * Initialisation.
	 */
	public void setUp() {
		
	}
	
	/**
	 * Nettoyage.
	 */
	public void tearDown() {
		
	}
	
	@Test
	public void testResultatLectureSummaryCorrectTD() throws ParsingProblemException {
		
		final String summary = "DUT INFO/S4 CAA TD Groupe 3 -- LC";
		
		// On parse le summary
		HashMap<String, String> resultat = CalendarParser.parseSummary(summary);
		
		assertEquals("DUT", resultat.get("formation"));
		assertEquals("INFO", resultat.get("specialite"));
		assertEquals("S4", resultat.get("semestre"));
		assertEquals("CAA", resultat.get("matiere"));
		assertEquals("TD", resultat.get("type"));
		assertEquals("3", resultat.get("groupe"));
		assertEquals("LC", resultat.get("intervenant"));

	}
	
	@Test
	public void testResultatLectureSummaryCorrectTP() throws ParsingProblemException {
		
		final String summary = "DUT INFO/S4 NPC TP Groupe 3B -- IO";
		
		// On parse le summary
		HashMap<String, String> resultat = CalendarParser.parseSummary(summary);
		
		assertEquals("DUT", resultat.get("formation"));
		assertEquals("INFO", resultat.get("specialite"));
		assertEquals("S4", resultat.get("semestre"));
		assertEquals("NPC", resultat.get("matiere"));
		assertEquals("TP", resultat.get("type"));
		assertEquals("3B", resultat.get("groupe"));
		assertEquals("IO", resultat.get("intervenant"));
	}
	
	@Test
	public void testLectureException() {
		
		final String summary = "DUT S4 NPC TP Groupe 3B -- IO";
		boolean throwed = false;
		
		// On parse le summary
		try {
			CalendarParser.parseSummary(summary);
		} catch (ParsingProblemException e) {
			throwed = true;
		}
		
		assertTrue(throwed);
	}
	
	@Test
	public void testFiltrageLessons() {
		
		List<Lesson> listeDonnees = new ArrayList<Lesson>();
		
		Lesson cour = new Lesson();
		// On met la date courrante
		cour.setDateDebut(new Date());
		cour.setDateFin(new Date());
		cour.setEmplacement("");
		cour.setIdLesson(0);
		cour.setLibelle("Test");
		cour.setIntervenant("");
		
		cour.setGroupe(new Group("3B", 4, 2012));
		listeDonnees.add(new Lesson(cour)); // Good
		listeDonnees.add(new Lesson(cour)); // Good
		listeDonnees.add(new Lesson(cour)); // Good

		cour.setGroupe(new Group("3A", 4, 2012));

		listeDonnees.add(new Lesson(cour)); // Bad
		
		cour.setGroupe(new Group("4B", 4, 2012));

		listeDonnees.add(new Lesson(cour)); // Bad
		
		cour.setGroupe(new Group("A", 4, 2012));
		listeDonnees.add(new Lesson(cour)); // Good
		
		cour.setGroupe(new Group("AB", 4, 2012));
		listeDonnees.add(new Lesson(cour)); // Good
		
		List<Lesson> resultat = CalendarParser.filterCoursGroupeDonne(listeDonnees, new Group("3B", 4, 2012));
		
		assertEquals(5, resultat.size());
		
	}
	
}
