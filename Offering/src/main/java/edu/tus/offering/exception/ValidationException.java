package edu.tus.offering.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends OfferingException {
	private static final long serialVersionUID = 334051992916748022L;
	public ValidationException(final String errorMessage) {
		super(errorMessage);
	}
}
