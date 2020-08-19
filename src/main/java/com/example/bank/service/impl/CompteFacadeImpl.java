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

	@Override
	public void debiter(Compte compte, float montant) {
		Operation operation = new Operation(montant, OperationType.DEBIT);
		executeOperation(operation, compte);
	}

	@Override
	public void crediter(Compte compte, float montant) {
		Operation operation = new Operation(montant, OperationType.CREDIT);
		executeOperation(operation, compte);
	}

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

	@Override
	public void storeTransaction(Compte compteSource, Compte compteTarget, float montant, StatutTransaction statut) {
		Transaction transaction = new Transaction(compteSource, compteTarget, montant, statut, new Date());
		transactions.add(transaction);
		TransactionList listTransactions = new TransactionList(transactions);
		CacheHelper.getInstance().getTransactionHistoryCacheFromCacheManager().put("virementHistory", listTransactions);
	}

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

	private Predicate<Transaction> betweenSpecifiedAccounts(Compte c1, Compte c2) {
		return t -> t.getCompteCredite().equals(c1) && t.getCompteDebite().equals(c2)
				|| t.getCompteCredite().equals(c2) && t.getCompteDebite().equals(c1);
	}
}
