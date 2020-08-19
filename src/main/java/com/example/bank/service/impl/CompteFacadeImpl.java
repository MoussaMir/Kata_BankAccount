package com.example.bank.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.example.bank.exception.InvalidCompteException;
import com.example.bank.model.Compte;
import com.example.bank.model.Operation;
import com.example.bank.model.OperationType;
import com.example.bank.model.StatutTransaction;
import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionList;
import com.example.bank.service.CompteFacade;
import com.example.bank.utils.CacheHelper;

public class CompteFacadeImpl implements CompteFacade {

	private static List<Transaction> transactions = new ArrayList<>();

	/**
	 * @param compte  concerné par l'operation
	 * @param montant de l'operation
	 */
	@Override
	public void debiter(Compte compte, float montant) {
		Operation operation = new Operation(montant, OperationType.DEBIT);
		executeOperation(operation, compte);
	}

	/**
	 * @param compte  concerné par l'operation
	 * @param montant de l'operation
	 */
	@Override
	public void crediter(Compte compte, float montant) {
		Operation operation = new Operation(montant, OperationType.CREDIT);
		executeOperation(operation, compte);
	}

	/**
	 * 
	 * @param operation à effectuer
	 * @param compte    concerner
	 * @return True si l'operation est bien efectuée et False sinon
	 */
	private boolean executeOperation(Operation operation, Compte compte) {
		boolean operationDone = false;
		if (compte == null) {
			throw new InvalidCompteException("le compte ne peut pas être null");
		}
		if (OperationType.CREDIT.equals(operation.getType())) {
			compte.setSolde(compte.getSolde() + operation.getMontant());
			operationDone = true;
		} else if (OperationType.DEBIT.equals(operation.getType())) {
			if (compte.getSolde() >= operation.getMontant()) {
				compte.setSolde(compte.getSolde() - operation.getMontant());
				operationDone = true;
			}
		}
		return operationDone;
	}

	/**
	 * @param compteSource ou compte à débiter
	 * @param compteTarget ou compte à créditer
	 * @return True si le virement est bien efectué et False sinon
	 */
	@Override
	public boolean virement(Compte compteSource, Compte comptetarget, float montant) {
		boolean creditDone = false;
		Operation operationDebit = new Operation(montant, OperationType.DEBIT);
		boolean debitDone = executeOperation(operationDebit, compteSource);
		if (debitDone) {
			Operation operationCredit = new Operation(montant, OperationType.CREDIT);
			creditDone = executeOperation(operationCredit, comptetarget);
		}
		return debitDone && creditDone;
	}

	/**
	 * @param compteSource
	 * @param compteTarget
	 * @return void => stocke la transaction dans le Cache
	 */
	@Override
	public void storeTransaction(Compte compteSource, Compte compteTarget, float montant, StatutTransaction statut) {
		Transaction transaction = new Transaction(compteSource, compteTarget, montant, statut, new Date());
		transactions.add(transaction);
		TransactionList listTransactions = new TransactionList(transactions);
		CacheHelper.getInstance().getTransactionHistoryCacheFromCacheManager().put("virementHistory", listTransactions);
	}

	/**
	 * @param compte1
	 * @param compte2
	 * @return historique des transactions communes de compte1 & compte2
	 */
	@Override
	public List<Transaction> getTransactionHistorybetweenAccounts(Compte compte1, Compte compte2) {
		if (compte1 == null || compte2 == null) {
			throw new InvalidCompteException("le compte ne peut pas être null");
		}
		TransactionList list = CacheHelper.getInstance().getTransactionHistoryCacheFromCacheManager()
				.get("virementHistory");
		return list == null ? list
				: list.stream().filter(betweenSpecifiedAccounts(compte1, compte2)).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param c1 compte 1
	 * @param c2 compte 2
	 * @return Predicate
	 */
	private Predicate<Transaction> betweenSpecifiedAccounts(Compte c1, Compte c2) {
		return t -> t.getCompteCredite().equals(c1) && t.getCompteDebite().equals(c2)
				|| t.getCompteCredite().equals(c2) && t.getCompteDebite().equals(c1);
	}
}
