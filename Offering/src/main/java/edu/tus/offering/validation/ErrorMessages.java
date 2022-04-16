package edu.tus.offering.validation;

public enum ErrorMessages {
	
	EMPTY_FIELDS("One or more empty fields"),
	END_AFTER_START("The start date/time happens after the end date/time");
	
	private String errorMessage;
	
	ErrorMessages(String errMsg){
		this.errorMessage=errMsg;
	}
	
	public String getMsg(){
		return errorMessage;
	}
}
