package edu.tus.offering;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.tus.offering.controllers.OfferingService;
import edu.tus.offering.dao.OfferingRepository;
import edu.tus.offering.dto.Offering;
import edu.tus.offering.exception.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AppUT{
	
	@InjectMocks
    private OfferingService offeringService;
 
    @Mock
    private OfferingRepository oRepo;
 
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
    //unit test local repo and check returned id
    @Test
    public void testCourseIdOK() {
        Offering offering = new Offering();
        offering.setCourseId(Long.valueOf(10));        
        Mockito.when(oRepo.findByCourseId(10)).thenReturn(Optional.ofNullable(offering));
        ResponseEntity<Offering> result = offeringService.getOfferingByCourseId(10);
        Assert.assertEquals(offering.getCourseId(), result.getBody().getCourseId());
    }
	
    //unit test local repo and check returned id not OK
    @Test(expected = NotFoundException.class)
    public void testCourseIdNOK() {
        Offering offering = new Offering();
        offering.setCourseId(Long.valueOf(10));
        offeringService.getOfferingByCourseId(1);
    }
    
    //unit test local repo and check returned status 200 OK
    @Test
    public void testStartDateTimeOK() {
        Offering offering = new Offering();
        offering.setCourseId(Long.valueOf(10));
        Mockito.when(oRepo.findByCourseId(10)).thenReturn(Optional.ofNullable(offering));
        ResponseEntity<Offering> result = offeringService.getOfferingByCourseId(10);
        Assert.assertEquals("200 OK", result.getStatusCode().toString());
    }

    //unit test local repo and check returned status not OK
    @Test(expected = NotFoundException.class)
    public void testStartDateTimeNOK() {
        Offering offering = new Offering();
        offering.setCourseId(Long.valueOf(10));
        offeringService.getOfferingByCourseId(1);
    }
    

    
    
    
    
    
    
    
    
    
    
	
}