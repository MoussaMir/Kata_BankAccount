package com.example.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

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
		Compte compte1 = new Compte("C1", 3000);
		// Act
		compteFacade.debiter(compte1, 300);
		// Assert
		assertEquals("Solde du compte pas correct", 3300, compte1.getSolde(), 0);
	}

	@Test
	public void testCrediter() {
		// given
		Compte compte1 = new Compte("C1", 2000);
		// Act
		compteFacade.crediter(compte1, 200);
		// Assert
		assertEquals("Solde du compte pas correct", 1800, compte1.getSolde(), 0);
	}

}
