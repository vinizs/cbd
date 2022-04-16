package edu.tus.offering.exception;
public abstract class OfferingException extends Exception {
	protected OfferingException(final String message) {
		super(message);
	}
	private static final long serialVersionUID = 6013983962125460401L;
}
