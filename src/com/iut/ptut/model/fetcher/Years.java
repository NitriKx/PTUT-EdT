package com.iut.ptut.model.fetcher;

public class Years {

	
	private int idYear;
	
	
	public Years (){
		
		
	}


	public int getIdYear() {
		return this.idYear;
	}


	public void setIdYear(int pIdYear) {
		this.idYear = pIdYear;
	}
	
	
	public String toString()
	{
		return("Année : "+this.idYear);
	}
	
}
