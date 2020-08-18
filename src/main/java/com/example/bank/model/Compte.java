package com.example.bank.model;


public class Compte {
	private String id;
	private String rib;
	private float solde;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRib() {
		return rib;
	}
	public void setRib(String rib) {
		this.rib = rib;
	}
	public float getSolde() {
		return solde;
	}
	public void setSolde(float solde) {
		this.solde = solde;
	}
	public Compte(String id, float solde) {
		super();
		this.id = id;
		this.solde = solde;
	}
	
	
	
}
