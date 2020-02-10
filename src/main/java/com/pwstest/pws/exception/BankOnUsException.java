package com.pwstest.pws.exception;

/**
 * 
 * @author Sudhakar Custom exception for Bankonus
 */
public class BankOnUsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String msg;

	public BankOnUsException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}
