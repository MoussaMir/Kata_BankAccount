package com.example.bank.exception;

public class InvalidCompteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidCompteException(String msg) {
		super(msg);
	}

}
