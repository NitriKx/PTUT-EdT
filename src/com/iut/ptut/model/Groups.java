package com.iut.ptut.model;

public class Groups {

	private String idGroup;
	private int annee;

	public Groups(String idGroupe, int annee) {
		this.setIdGroup(idGroupe);
		this.setAnnee(annee);
	}

	public String getIdGroup() {
		return this.idGroup;
	}

	public void setIdGroup(String pIdGroup) {
		this.idGroup = pIdGroup;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}
	
	//
	// TO STRING
	// 
	
	public String toString() {
		return "id Groupe : " + this.idGroup + " Année : " + this.annee.getIdYear();
	}

	
}
