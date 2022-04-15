package edu.tus.offering.dto;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Offering{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long offeringId;
	
	@Column(nullable=false)
	private long courseId;

	@Column(nullable=false)
	@JsonFormat(pattern = "uuuu-MM-dd HH:mm")
	private LocalDateTime startDateTime;

	@Column(nullable=false)
	@JsonFormat(pattern = "uuuu-MM-dd HH:mm")
	private LocalDateTime endDateTime;
	
	public Offering() {
	}
	
	@JsonCreator
	public Offering(
//			@JsonProperty("offeringId") long offeringId,
			@JsonProperty("courseId") long courseId,
			@JsonProperty("startDateTime") LocalDateTime startDateTime, 
			@JsonProperty("endDateTime") LocalDateTime endDateTime
			 ){
		this.courseId = courseId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	////////////////////////////////	
	public Long getOfferingId() {
		return offeringId;
	}
	
//	public void setOfferingId(Long offeringId) {
//        this.offeringId = offeringId;
//    }
		
	////////////////////////////////	
	public long getCourseId() {
		return courseId;
	}
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

	
	////////////////////////////////	
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    ////////////////////////////////	
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    
    
	
}
