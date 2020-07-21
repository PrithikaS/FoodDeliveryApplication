package com.main.foodDelivery.exception;

public class DataException extends Exception{

	final String errorMessage;
	
	public DataException(String errorMessage,Throwable err) {
		super(errorMessage,err);
		this.errorMessage = errorMessage;		
	}
	
}
