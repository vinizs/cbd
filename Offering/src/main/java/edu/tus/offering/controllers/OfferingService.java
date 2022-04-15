package edu.tus.offering.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.tus.offering.dao.OfferingRepository;
import edu.tus.offering.dto.Offering;
import edu.tus.offering.exception.NotFoundException;
import edu.tus.offering.exception.ValidationException;
import edu.tus.offering.validation.Validator;

@RestController
@Service
public class OfferingService{
	
	//Logger log = (Logger) LoggerFactory.getLogger(OfferingService.class);
			

	@Autowired
	OfferingRepository oRepo;
	
	@Autowired
	Validator valid;
		
	
		
	
	//OFFERINGS
	
	
	//get all or specific (anything else but id)
	@GetMapping("/api/v1/offerings")
	public Page<Offering> getAllOfferings(	@RequestParam(required = false) String startDateTime,  //date parsed below
									@RequestParam(required = false) String endDateTime,  //date parsed below
									@RequestParam(required = false) Long courseId, 
									@PageableDefault(sort = {"offeringId"}, direction = Sort.Direction.ASC, value = 50) final Pageable pageable)
	{
		
		try {
			
			if (oRepo.findAll(pageable).isEmpty()) 
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Entities Found");			
			else if (startDateTime == null && endDateTime == null && courseId == null) 
				return oRepo.findAll(pageable);			
			else if (startDateTime != null)
				return oRepo.findByStartDateTime(LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),pageable);
			else if (endDateTime != null)
				return oRepo.findByEndDateTime(LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),pageable);			

			else if (courseId != null)
				return oRepo.findByCourseId(courseId,pageable);			

			
			else
				return null;
			
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Entities Found");
		}
	}

		
	
	
	//get by offering id
	@GetMapping("/api/v1/offerings/{offeringId}")
	public ResponseEntity<Offering> getOfferingById(@PathVariable("offeringId") long offeringId) {
		Optional<Offering> optionalOffering = oRepo.findById(offeringId);
		if (optionalOffering.isPresent()) {
			return new ResponseEntity<>(optionalOffering.get(), HttpStatus.OK);
		} else {
			throw new NotFoundException("Entity Not Found");
			
			
		}
	}

	//get by course id
	@GetMapping("/api/v1/offerings-course-id/{courseId}")
	public ResponseEntity<Offering> getOfferingByCourseId(@PathVariable("courseId") long courseId) {
		Optional<Offering> optionalOffering = oRepo.findByCourseId(courseId);
		if (optionalOffering.isPresent()) {
			return new ResponseEntity<>(optionalOffering.get(), HttpStatus.OK);
		} else {
			throw new NotFoundException("Entity Not Found");
		}
	}
	
	
	
		
	//post offering
	@RequestMapping(value="/api/v1/offerings", method = RequestMethod.POST)
	ResponseEntity<Offering> postOffering(@RequestBody Offering offering) throws ValidationException {
		valid.validateOffering(offering);
		Offering savedOffering = oRepo.save(offering);
		return new ResponseEntity<Offering>(savedOffering,HttpStatus.CREATED);
	}
	
		
	
				
	//put offering
	@RequestMapping(value="/api/v1/offerings/{offeringId}", method = RequestMethod.PUT)
	Offering updateOffering(@PathVariable("offeringId") long offeringId, @RequestBody Offering offering) throws ValidationException {
		//check if it exists
		if (oRepo.findById(offeringId).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity Not Found");
		}
		else {
			valid.validateOffering(offering);
			//get an existing offering and update it
			Optional<Offering> optionalOffering = oRepo.findById(offeringId);
			Offering existingOffering=optionalOffering.get();
			existingOffering.setStartDateTime(offering.getStartDateTime());
			existingOffering.setEndDateTime(offering.getEndDateTime());

			Offering savedOffering = oRepo.save(existingOffering);
			return savedOffering;
		}
	}
	
	//delete
	@RequestMapping(value="/api/v1/offerings/{offeringId}", method = RequestMethod.DELETE)
	Offering deleteOffering(@PathVariable("offeringId") long offeringId) {
		//check if it exists
		if (oRepo.findById(offeringId).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity Not Found");
		}
		else {
			try {
				//get an existing offering and delete it
				Optional<Offering> optionalOffering = oRepo.findById(offeringId);
				Offering existingOffering=optionalOffering.get();
				oRepo.delete(existingOffering);
				return existingOffering;
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
				}
		}
	}
			


	
	//delete offering by course id
	@RequestMapping(value="/api/v1/offerings-course-id/{courseId}", method = RequestMethod.DELETE)
	Offering deleteOfferingByCourse(@PathVariable("courseId") long courseId) {
		//check if it exists
		if (oRepo.findByCourseId(courseId).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity Not Found");
		}
		else {
			try {
				//get an existing offering with course and delete it
				Optional<Offering> optionalOffering = oRepo.findByCourseId(courseId);
				Offering existingOffering=optionalOffering.get();
				oRepo.delete(existingOffering);
				return existingOffering;
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
				}
		}
	}
	
		
		
		
		
	
	
	
	

	
	
	
}
