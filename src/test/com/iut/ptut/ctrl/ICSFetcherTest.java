package test.com.iut.ptut.ctrl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.junit.Test;

import com.iut.ptut.model.fetcher.ICSFetcher;

public class ICSFetcherTest extends TestCase{
	
	public static void main (String args[]) throws IOException {
		
		ICSFetcher fetcher = new ICSFetcher("4", "13");
		byte[] buf = new byte[30];
		StringBuffer str = new StringBuffer();
		InputStream is = fetcher.getFile();
		while(is.read(buf) > 0)
			str.append(new String(buf));
		
		System.out.println(str.toString());
	}
	
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
