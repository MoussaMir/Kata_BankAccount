package com.example.bank.model;

public enum StatutTransaction {
	DONE("1"), CANCELED("2");

	private String code;

	private StatutTransaction(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
