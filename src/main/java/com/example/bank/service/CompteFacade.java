package com.example.bank.service;

import com.example.bank.model.Compte;

public interface CompteFacade {
	public void debiter(Compte compte, float montant);

	public void crediter(Compte compte, float montant);

}
