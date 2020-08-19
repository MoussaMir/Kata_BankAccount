package com.example.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.bank.exception.InvalidCompteException;
import com.example.bank.model.Compte;
import com.example.bank.model.StatutTransaction;
import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionList;
import com.example.bank.service.impl.CompteFacadeImpl;
import com.example.bank.utils.CacheHelper;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompteFacadeImplTest {
	private CompteFacadeImpl compteFacade = new CompteFacadeImpl();;

	@Test
	public void test1_testDebiter() {
		// given
		Compte compte = new Compte("C1", 3000);
		// Act
		compteFacade.debiter(compte, 300);
		// Assert
		assertEquals("Solde du compte pas correct", 2700, compte.getSolde(), 0);
	}

	@Test
	public void test2_testCrediter() {
		// given
		Compte compte = new Compte("C2", 2000);
		// Act
		compteFacade.crediter(compte, 200);
		// Assert
		assertEquals("Solde du compte pas correct", 2200, compte.getSolde(), 0);
	}

	@Test
	public void test3_testDebiterWhenSoldeInsuffisant() {
		// given
		Compte compte = new Compte("C3", 120);
		// Act
		compteFacade.debiter(compte, 200);
		// Assert
		assertEquals("Solde du compte pas correct", 120, compte.getSolde(), 0);

	}

	@Test(expected = InvalidCompteException.class)
	public void test4_testCrediterwhenCompteNull() {
		// given
		Compte compte = null;
		// Act
		compteFacade.crediter(compte, 200);
	}

	@Test
	public void test5_testVirementWhenSoldeInsuffisant() {
		// given
		Compte compteDebite = new Compte("C1", 2000);
		Compte compteCredite = new Compte("C2", 3000);
		float montant = 2500;
		// Act
		boolean transactionDone = compteFacade.virement(compteDebite, compteCredite, montant);
		StatutTransaction statutTransaction = transactionDone ? StatutTransaction.DONE : StatutTransaction.CANCELED;

		compteFacade.storeTransaction(compteDebite, compteCredite, montant, statutTransaction);
		// Assert
		assertEquals("Solde du compte pas correct", 2000, compteDebite.getSolde(), 0);
		assertEquals("Solde du compte pas correct", 3000, compteCredite.getSolde(), 0);
	}

	@Test
	public void test6_testVirementWhenSoldeSuffisant() {
		// given
		Compte compteDebite = new Compte("C1", 5000);
		Compte compteCredite = new Compte("C2", 3000);
		float montant = 2500;
		// Act
		boolean transactionDone = compteFacade.virement(compteDebite, compteCredite, montant);
		StatutTransaction statutTransaction = transactionDone ? StatutTransaction.DONE : StatutTransaction.CANCELED;
		compteFacade.storeTransaction(compteDebite, compteCredite, montant, statutTransaction);
		// Assert
		assertEquals("Solde du compte pas correct", 2500, compteDebite.getSolde(), 0);
		assertEquals("Solde du compte pas correct", 5500, compteCredite.getSolde(), 0);
	}

	@Test
	public void test7_testZStoreTansactionAfterVirementOperation() {
		// Act
		TransactionList list = CacheHelper.getInstance().getTransactionHistoryCacheFromCacheManager()
				.get("virementHistory");
		assertNotNull(list);
		assertEquals(2, list.size());

	}

	@Test
	public void test8_testTransactionHistory() {
		// given
		Compte compte1 = new Compte("C1", 5000);
		Compte compte2 = new Compte("C2", 3000);
		// Act
		List<Transaction> transationsCommunes = compteFacade.getTransactionHistorybetweenAccounts(compte1, compte2);
		// Assert
		assertNotNull(transationsCommunes);
		assertEquals(2, transationsCommunes.size());
		System.out.println(transationsCommunes.toString());

	}

	@Test
	public void test9_testTransactionHistoryWhenPermuteComptes() {
		// given
		Compte compte1 = new Compte("C2", 5000);
		Compte compte2 = new Compte("C1", 3000);
		// Act
		List<Transaction> transationsCommunes = compteFacade.getTransactionHistorybetweenAccounts(compte1, compte2);
		// Assert
		assertNotNull(transationsCommunes);
		assertEquals(2, transationsCommunes.size());
		System.out.println(transationsCommunes.toString());

	}

	@Test
	public void test10_testTransactionHistoryWhenEmpty() {
		// given
		Compte compte1 = new Compte("C4", 5000);
		Compte compte2 = new Compte("C66", 3000);
		// Act
		List<Transaction> transationsCommunes = compteFacade.getTransactionHistorybetweenAccounts(compte1, compte2);
		// Assert
		assertNull(transationsCommunes);

	}

	@Test(expected = InvalidCompteException.class)
	public void test11_testTransactionHistoryWhenCompteNull() {
		// given
		Compte compte1 = null;
		Compte compte2 = new Compte("C66", 3000);
		// Act
		compteFacade.getTransactionHistorybetweenAccounts(compte1, compte2);
	}

	@AfterClass
	public static void cleanCache() {
		CacheHelper.getInstance().getTransactionHistoryCacheFromCacheManager().clear();
	}

}
