package com.example.bank.model;

public enum OperationType {

	DEBIT("1"), CREDIT("2"), VIREMENT("3");

	private String code;

	private OperationType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
