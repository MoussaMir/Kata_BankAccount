package com.example.bank.model;

import java.util.Date;

public class Transaction {
	
	private Compte compteDebite;
	private Compte compteCredite;
	private float montant;
	private StatutTransaction status;
	private Date dateCreation;
	
	
	public Compte getCompteDebite() {
		return compteDebite;
	}
	public void setCompteDebite(Compte compteDebite) {
		this.compteDebite = compteDebite;
	}
	public Compte getCompteCredite() {
		return compteCredite;
	}
	public void setCompteCredite(Compte compteCredite) {
		this.compteCredite = compteCredite;
	}
	public float getMontant() {
		return montant;
	}
	public void setMontant(float montant) {
		this.montant = montant;
	}
	public StatutTransaction getStatus() {
		return status;
	}
	public void setStatus(StatutTransaction status) {
		this.status = status;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	@Override
	public String toString() {
		return "Transaction [montant=" + montant + ", status=" + status + ", dateCreation=" + dateCreation + "]";
	}
	public Transaction(Compte compteDebite, Compte compteCredite, float montant, StatutTransaction status,
			Date dateCreation) {
		super();
		this.compteDebite = compteDebite;
		this.compteCredite = compteCredite;
		this.montant = montant;
		this.status = status;
		this.dateCreation = dateCreation;
	}
	
	
	
	
}
