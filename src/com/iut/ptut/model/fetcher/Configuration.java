package com.iut.ptut.model.fetcher;

public class Configuration {
	
	private String configuration;
	private boolean isNotificationActive;
	private int intervalRafraichissement;
	
	public Configuration ()
	{
		
	}

	public String getConfiguration() {
		return this.configuration;
	}

	public void setConfiguration(String pConfiguration) {
		this.configuration = pConfiguration;
	}

	public boolean isNotificationActive() {
		return this.isNotificationActive;
	}

	public void setNotificationActive(boolean pIsNotificationActive) {
		this.isNotificationActive = pIsNotificationActive;
	}

	public int getIntervalRafraichissement() {
		return this.intervalRafraichissement;
	}

	public void setIntervalRafraichissement(int pIntervalRafraichissement) {
		this.intervalRafraichissement = pIntervalRafraichissement;
	}
	
	public String toString()
	{
		return("id Configuration : "+this.configuration +" Etat notification : "+this.isNotificationActive
				+" Intervalle de rafraichissement : " + this.intervalRafraichissement);
	}
	

}
