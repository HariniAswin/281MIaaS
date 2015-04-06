package com.cmpe281.team2.miaas.exception;

public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8222905028683847498L;
	

	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(Exception e) {
		super(e);
	}

}
