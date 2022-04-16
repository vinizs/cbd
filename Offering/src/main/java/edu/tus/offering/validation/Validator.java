package edu.tus.offering.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.tus.offering.dao.OfferingRepository;
import edu.tus.offering.dto.*;
import edu.tus.offering.exception.ValidationException;

@Component
public class Validator {
	Offering offering;
	
	@Autowired
	OfferingRepository oRepo;
	
	public void validateOffering(Offering offering) throws ValidationException {
		this.offering = offering;
		checkEmptyFields(offering);
		checkDateTime(offering);		
	}
	
	//check for empty fields
	private void checkEmptyFields(Offering offering) throws ValidationException {
		if ((offering.getStartDateTime() == null) || (offering.getEndDateTime() == null)) {
			throw new ValidationException(ErrorMessages.EMPTY_FIELDS.getMsg());
		}
	}
	
	//offerings can happen in the past for legacy purposes
	private void checkDateTime(Offering offering) throws ValidationException {
		int diff = offering.getStartDateTime().compareTo(offering.getEndDateTime());
		if (diff > 0) {
			throw new ValidationException(ErrorMessages.END_AFTER_START.getMsg());
		}
	}
}