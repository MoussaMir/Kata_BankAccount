package com.example.bank.exception;

public class SoldeInsuffisantException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SoldeInsuffisantException(String msg) {
		super(msg);
	}

}
