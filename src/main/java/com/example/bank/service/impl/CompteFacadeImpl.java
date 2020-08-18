package com.example.bank.service.impl;

import com.example.bank.exception.InvalidCompteException;
import com.example.bank.exception.SoldeInsuffisantException;
import com.example.bank.model.Compte;
import com.example.bank.model.Operation;
import com.example.bank.model.OperationType;
import com.example.bank.service.CompteFacade;

public class CompteFacadeImpl implements CompteFacade {

	public void debiter(Compte compte, float montant) {
		Operation operation = new Operation(montant, OperationType.DEBIT);
		executeOperation(operation, compte);
	}

	public void crediter(Compte compte, float montant) {
		Operation operation = new Operation(montant, OperationType.CREDIT);
		executeOperation(operation, compte);
	}

	private boolean executeOperation(Operation operation, Compte compte) {
		boolean operationDone = false;
		if (compte == null)
			throw new InvalidCompteException("le compte ne peut pas être null");

		if (OperationType.CREDIT.equals(operation.getType())) {
			compte.setSolde(compte.getSolde() + operation.getMontant());
			operationDone = true;
		} else if (OperationType.DEBIT.equals(operation.getType())) {
			if (compte.getSolde() >= operation.getMontant()) {
				compte.setSolde(compte.getSolde() - operation.getMontant());
				operationDone = true;
			} else {
				throw new SoldeInsuffisantException("le montant demandé est supérieur à votre solde");
			}
		}
		return operationDone;
	}

	public void virement(Compte compteSource, Compte comptetarget, float montant) {
		Operation operationDebit = new Operation(montant, OperationType.DEBIT);
		if (executeOperation(operationDebit, compteSource)) {
			Operation operationCredit = new Operation(montant, OperationType.CREDIT);
			executeOperation(operationCredit, comptetarget);
		}
	}

}
