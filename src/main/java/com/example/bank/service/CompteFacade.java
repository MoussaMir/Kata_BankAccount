package com.example.bank.service;

import java.util.List;

import com.example.bank.model.Compte;
import com.example.bank.model.StatutTransaction;
import com.example.bank.model.Transaction;

public interface CompteFacade {
	public void debiter(Compte compte, float montant);

	public void crediter(Compte compte, float montant);

	public boolean virement(Compte compteSource, Compte compteTarget, float montant);

	public void storeTransaction(Compte compteSource, Compte compteTarget,float montant, StatutTransaction statut);
	
	public List<Transaction> getTransactionHistorybetweenAccounts(Compte compte1, Compte compte2);
}
