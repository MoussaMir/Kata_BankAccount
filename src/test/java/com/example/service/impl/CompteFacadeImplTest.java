package com.example.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.example.bank.exception.InvalidCompteException;
import com.example.bank.exception.SoldeInsuffisantException;
import com.example.bank.model.Compte;
import com.example.bank.service.impl.CompteFacadeImpl;

public class CompteFacadeImplTest {
	private CompteFacadeImpl compteFacade;

	@Before
	public void init() {
		compteFacade = new CompteFacadeImpl();
	}

	@Test
	public void testDebiter() {
		// given
		Compte compte = new Compte("C1", 3000);
		// Act
		compteFacade.debiter(compte, 300);
		// Assert
		assertEquals("Solde du compte pas correct", 2700, compte.getSolde(), 0);
	}

	@Test
	public void testCrediter() {
		// given
		Compte compte = new Compte("C2", 2000);
		// Act
		compteFacade.crediter(compte, 200);
		// Assert
		assertEquals("Solde du compte pas correct", 2200, compte.getSolde(), 0);
	}

	@Test(expected = SoldeInsuffisantException.class)
	public void testDebiterWhenSoldeInsuffisant() {
		// given
		Compte compte = new Compte("C3", 120);
		// Act
		compteFacade.debiter(compte, 200);

	}
	
	@Test(expected = InvalidCompteException.class)
	public void testCrediterwhenCompteNull() {
		// given
		Compte compte = null;
		// Act
		compteFacade.crediter(compte, 200);
	}
	
	@Test
	public void testVirement() {
		// given
		Compte compteDebite = new Compte("C1", 2000);
		Compte compteCredite = new Compte("C2", 3000);
		float montant = 500;
		// Act
		compteFacade.virement(compteDebite, compteCredite, montant);
		assertEquals("Solde du compte pas correct", 1500, compteDebite.getSolde(), 0);
		assertEquals("Solde du compte pas correct", 3500, compteCredite.getSolde(), 0);
	}
	
	@Test(expected = SoldeInsuffisantException.class)
	public void testVirementWhenSoldeInsuffisant() {
		// given
		Compte compteDebite = new Compte("C1", 2000);
		Compte compteCredite = new Compte("C2", 3000);
		float montant = 2500;
		// Act
		compteFacade.virement(compteDebite, compteCredite, montant);		
	}
}
