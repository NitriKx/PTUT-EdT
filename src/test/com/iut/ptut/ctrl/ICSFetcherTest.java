package test.com.iut.ptut.ctrl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.junit.Test;

import com.iut.ptut.model.fetcher.ICSFetcher;

import junit.framework.TestCase;

public class ICSFetcherTest extends TestCase{
	
	public void setUp() {
		
	}
	
	public void tearDown() {
		
	}
	
	
	
	@Test
	public void testRecuperationTimeTableURLValide() throws MalformedURLException, IOException {
		ICSFetcher fetcher = new ICSFetcher("S4", "13");
		InputStream is = fetcher.getFile();
	}
	
	@Test 
	public void testRecuperationTimeTableSemestreInconnu() throws MalformedURLException, IOException {
		
		ICSFetcher fetcher = new ICSFetcher("S5", "13");
		boolean throwed = false;
		
		try {
			InputStream is = fetcher.getFile();
		} catch (FileNotFoundException e) {
			throwed = true;
		}
		
		assertTrue(throwed);
	}
	
	
}
