package edu.tus.offering.validation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import edu.tus.offering.dao.CourseRepository;
import edu.tus.offering.dao.OfferingRepository;
import edu.tus.offering.dto.*;
import edu.tus.offering.exception.ValidationException;

@Component
public class Validator {
//	Course course;
	String name;
	Offering offering;
	long id;
	List<Offering> offerings; 

	
	
//	@Autowired
//	CourseRepository cRepo;
	
	@Autowired
	OfferingRepository oRepo;
	
//	public void validateCourse(Course course) throws ValidationException {
//		this.course = course;
//		checkEmptyFields(course);
//	}
	
//	public void validateCourseName(String name) throws ValidationException {
//		this.name = name;
//		//checkName(name);
//	}

//	public void validateCourseNamePUT(String name, long id) throws ValidationException {
//		this.name = name;
//		this.id = id;
//		//checkNamePUT(name, id);
//	}

	
	public void validateOffering(Offering offering) throws ValidationException {
		this.offering = offering;
		checkEmptyFields(offering);
		checkDateTime(offering);		
	}

	public void newCourseWithOffering(List<Offering> offerings) throws ValidationException {
		this.offerings = offerings;
		checkNewCourseWithOffering(offerings);
		
	}	
	
	public void validateOfferingExists(List<Offering> offerings) throws ValidationException {
		this.offerings = offerings;
//		this.id = id;
		checkValidateOfferingExists(offerings);
		
	}	

	
	
	
	
	
	
	
	
	/////////////////////
	
	
	
	
	
	
	
	
	
	
	
//	private void checkEmptyFields(Course course) throws ValidationException {
//		System.out.println("here validate!");
//		if ((course.getName().length() == 0)) {
//			throw new ValidationException(ErrorMessages.EMPTY_FIELDS.getMsg());
//		}
//	}
	
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
	
//	//if course with name already exists case not sensitive!
//	private void checkName(String name) throws ValidationException {		
//		List<Course> courses = cRepo.findAll();		
//		for (int i = 0; i < courses.size(); i++) {
//			if (   courses.get(i).getName().toUpperCase().equals(name)   ) {
//				throw new ValidationException(ErrorMessages.ALREADY_EXISTS.getMsg());	
//			}
//		}
//	}

//	//PUT if course with name already exists case not sensitive! (course update can only have the same name or new name not duplicate)
//	private void checkNamePUT(String name, long id) throws ValidationException {		
//		List<Course> courses = cRepo.findAll();		
//		for (int i = 0; i < courses.size(); i++) {
//			if (   courses.get(i).getName().toUpperCase().equals(name) && (courses.get(i).getId() != id)      ) {
//				throw new ValidationException(ErrorMessages.ALREADY_EXISTS.getMsg());	
//			}
//		}
//	}
	
	//offerings can happen in the past for legacy purposes
	private void checkNewCourseWithOffering(List<Offering> offerings) throws ValidationException {
		
		if (offerings != null) {
			for (int i = 0; i < offerings.size(); i++) {
				if (   offerings.get(i).getOfferingId() != 0      ) {
					throw new ValidationException(ErrorMessages.NEW_COURSE_EXISTING_OFFERING.getMsg());	
				}
			}
		}
	}
	
	//
	private void checkValidateOfferingExists(List<Offering> offerings) throws ValidationException {
		int count = 0;

		if (offerings != null) {
			List<Offering> existingOfferings = oRepo.findAll();
			
			
			//loop offerings provided
			for (int i = 0; i < offerings.size(); i++) {
				System.out.println("i "+i+" : "+offerings.get(i).getOfferingId());
				for (int j = 0; j < existingOfferings.size(); j++) {
					if (  offerings.get(i).getOfferingId() == existingOfferings.get(j).getOfferingId()    ) {
					count++;	
						
					}
				}
			}	
			
			if (count < offerings.size()) {
				throw new ValidationException(ErrorMessages.PUT_COURSE_OFFERING.getMsg());
			}
		}
	}
	
	
	
	
	
	
	
	

}
