package com.iut.ptut.model.fetcher;

import java.io.IOException;
import java.net.MalformedURLException;

public class MAIN {

	/**
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub
		try{
			ICSFetcher file = new ICSFetcher("S4", "09");
			System.out.println(file.getFile());
		}
		catch (Exception e){
			System.out.println("Veuillez vous connecter à Internet");
			System.exit(-1);
		}
	}

}
