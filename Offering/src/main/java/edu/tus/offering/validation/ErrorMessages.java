package edu.tus.offering.validation;

public enum ErrorMessages {
	
	EMPTY_FIELDS("One or more empty fields"),
	ALREADY_EXISTS("Course with given name already exists"),
//	INVALID_COUNTRY("Not accepting more wines from that country"),
	END_AFTER_START("The start date/time happens after the end date/time"),
	NEW_COURSE_EXISTING_OFFERING("Use null, 0 or empty entry for offeringId"),
	PUT_COURSE_OFFERING("In order to update offerings, use existing ones");
	
	private String errorMessage;
	
	ErrorMessages(String errMsg){
		this.errorMessage=errMsg;
	}
	
	public String getMsg(){
		return errorMessage;
	}
}
