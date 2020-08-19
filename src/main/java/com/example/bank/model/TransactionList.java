package com.example.bank.model;

import java.util.ArrayList;
import java.util.Collection;

public class TransactionList extends ArrayList<Transaction>{
	private static final long serialVersionUID = 1L;

	public TransactionList(final Collection<? extends Transaction> c) {
         super(c);
     }
}
