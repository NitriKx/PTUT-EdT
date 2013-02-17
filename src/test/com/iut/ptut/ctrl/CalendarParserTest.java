package test.com.iut.ptut.ctrl;

import java.util.HashMap;

import junit.framework.TestCase;

import org.junit.Test;

import com.iut.ptut.ctrl.CalendarParser;
import com.iut.ptut.ctrl.ParsingProblemException;

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
		CalendarParser parser = new CalendarParser();
		
		// On parse le summary
		HashMap<String, String> resultat = parser.parseSummary(summary);
		
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
		CalendarParser parser = new CalendarParser();
		
		// On parse le summary
		HashMap<String, String> resultat = parser.parseSummary(summary);
		
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
		CalendarParser parser = new CalendarParser();
		boolean throwed = false;
		
		// On parse le summary
		try {
			parser.parseSummary(summary);
		} catch (ParsingProblemException e) {
			throwed = true;
		}
		
		assertTrue(throwed);
	}
	
}
