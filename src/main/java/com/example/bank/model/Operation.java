package com.example.bank.model;

import java.util.Date;

public class Operation {
	private String id;
	private float montant;
	private Date date;
	private OperationType type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public float getMontant() {
		return montant;
	}
	public void setMontant(float montant) {
		this.montant = montant;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public OperationType getType() {
		return type;
	}
	public void setType(OperationType type) {
		this.type = type;
	}
	public Operation(float montant, OperationType type) {
		super();
		this.montant = montant;
		this.type = type;
	}
	
}
