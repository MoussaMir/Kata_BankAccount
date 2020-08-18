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
		assertEquals("Solde du compte pas correct", 3300, compte.getSolde(), 0);
	}

	@Test
	public void testCrediter() {
		// given
		Compte compte = new Compte("C2", 2000);
		// Act
		compteFacade.crediter(compte, 200);
		// Assert
		assertEquals("Solde du compte pas correct", 1800, compte.getSolde(), 0);
	}

	@Test(expected = SoldeInsuffisantException.class)
	public void testCrediterWhenSoldeInsuffisant() {
		// given
		Compte compte = new Compte("C3", 120);
		// Act
		compteFacade.crediter(compte, 200);

	}
	
	@Test(expected = InvalidCompteException.class)
	public void testCrediterwhenCompteNull() {
		// given
		Compte compte = null;
		// Act
		compteFacade.crediter(compte, 200);
	}
}
