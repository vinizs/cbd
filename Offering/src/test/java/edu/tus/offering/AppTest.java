package edu.tus.offering;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.jsonpath.JsonPath;

import edu.tus.offering.controllers.OfferingService;
import edu.tus.offering.dao.OfferingRepository;
import edu.tus.offering.dto.Offering;
import edu.tus.offering.exception.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AppTest {
	
	
	
	
	
	
	//Unit Test
	
	
	
	
	
	
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
    

    
    
    
	//Integration Test
    
    
    
    

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
  
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	
	
	
	//tests successful post offering.
	//should return 2xx
	@Test
	public void postOffering() throws Exception {
		MvcResult result = 	mockMvc.perform(get("/api/v1/offerings")).andReturn();
		String response = result.getResponse().getContentAsString();
		Integer id;		
		if (response.isBlank()) {
			id = 0;
		} else {
			id = JsonPath.parse(response).read("$.content.length()");
		}		
		id = (id+1)*10;
		mockMvc.perform(post("/api/v1/offerings")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\n"
	            		+ "    \"courseId\": "+id+",\n"
	            		+ "    \"startDateTime\": \"2021-10-11 10:00\",\n"
	            		+ "    \"endDateTime\": \"2021-10-11 11:00\"\n"
	            		+ "}"))
	            .andExpect(status().is2xxSuccessful());
	}
		
	//tests unsuccessful post offering, start date/time happens after the end date/time
	//should return 4xx
	@Test
	public void postOfferingDateTime() throws Exception {
		mockMvc.perform(post("/api/v1/offerings")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\n"
	            		+ "    \"courseId\": 10,\n"
	            		+ "    \"startDateTime\": \"2021-10-11 12:00\",\n"
	            		+ "    \"endDateTime\": \"2021-10-11 11:00\"\n"
	            		+ "}"))
				.andExpect(status().is4xxClientError());
	}

	//tests unsuccessful post offering, empty fields
	//should return 4xx
	@Test
	public void postOfferingEmptyFields() throws Exception {
		mockMvc.perform(post("/api/v1/offerings")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\n"
	            		+ "    \"courseId\": 10,\n"
	            		+ "    \"startDateTime\": \"\",\n"
	            		+ "    \"endDateTime\": \"2021-10-11 11:00\"\n"
	            		+ "}"))
	            .andExpect(status().is4xxClientError());
	}

	//test successful get all
	//should return 2xx
	@Test
	public void getAllOfferings() throws Exception {
		mockMvc.perform(get("/api/v1/offerings"))
				.andExpect(status().isOk())		
				.andExpect(content().contentType("application/json"));
	}
	
	
	
	//test successful home
	//should return 2xx
	@Test
	public void home() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());	
	}
	
	

	//test successful get id
	//should return 2xx
	@Test
	public void getOffering() throws Exception {
		mockMvc.perform(get("/api/v1/offerings/1"))
				.andExpect(status().isOk())		
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.offeringId").value("1"))
				.andExpect(jsonPath("$.courseId").value("10"));
	}

	//test unsuccessful get id
	//should return 4xx
	@Test
	public void getOfferingNonExisting() throws Exception {
		mockMvc.perform(get("/api/v1/offerings/9999"))
				.andExpect(status().is4xxClientError());
	}
	
	//test successful get course id
	//should return 2xx
	@Test
	public void getOfferingCourseId() throws Exception {
		mockMvc.perform(get("/api/v1/offerings-course-id/10"))
				.andExpect(status().isOk())		
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.offeringId").value("1"))
				.andExpect(jsonPath("$.courseId").value("10"));
	}
	
	//tests successful put offering
	//should return 2xx
	@Test
	public void putOffering() throws Exception {
		mockMvc.perform(put("/api/v1/offerings/1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\n"
	            		+ "    \"startDateTime\": \"2021-10-11 10:00\",\n"
	            		+ "    \"endDateTime\": \"2021-10-11 11:00\"\n"
	            		+ "}"))
	            .andExpect(status().is2xxSuccessful());
	}
	
	//tests unsuccessful put offering, start date/time happens after the end date/time
	//should return 4xx
	@Test
	public void putOfferingDateTime() throws Exception {
		mockMvc.perform(put("/api/v1/offerings/1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\n"
	            		+ "    \"startDateTime\": \"2021-10-11 12:00\",\n"
	            		+ "    \"endDateTime\": \"2021-10-11 11:00\"\n"
	            		+ "}"))
				.andExpect(status().is4xxClientError());
	}

	//tests unsuccessful put offering, empty fields
	//should return 4xx
	@Test
	public void putOfferingEmptyFields() throws Exception {
		mockMvc.perform(put("/api/v1/offerings/1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\n"
	            		+ "    \"startDateTime\": \"\",\n"
	            		+ "    \"endDateTime\": \"2021-10-11 11:00\"\n"
	            		+ "}"))
	            .andExpect(status().is4xxClientError());
	}
		
	//tests unsuccessful delete offering, not found
	//should return 4xx
	@Test
	public void deleteOfferingNonExisting() throws Exception {
		mockMvc.perform(delete("/api/v1/offerings/7777")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\n"
	            		+ "    \"startDateTime\": \"2021-10-11 12:00\",\n"
	            		+ "    \"endDateTime\": \"2021-10-11 11:00\"\n"
	            		+ "}"))
				.andExpect(status().is4xxClientError());
	}
	
	//test error get param start
	//should return 4xx
	@Test
	public void getAllOfferingsParamStart() throws Exception {
		mockMvc.perform(get("/api/v1/offerings?startDateTime=2"))
				.andExpect(status().is4xxClientError());	
	}

	//test error get param end
	//should return 4xx
	@Test
	public void getAllOfferingsParamEnd() throws Exception {
		mockMvc.perform(get("/api/v1/offerings?endDateTime=2"))
				.andExpect(status().is4xxClientError());	
	}
    
	//test error get param courseId
	//should return 2xx
	@Test
	public void getAllOfferingsParamCourseId() throws Exception {
		mockMvc.perform(get("/api/v1/offerings?courseId=9999"))
				.andExpect(status().is2xxSuccessful());	
	}
		
	//test error put offering 9999
	//should return 4xx
	@Test
	public void putOfferingsNonExisting() throws Exception {
		mockMvc.perform(put("/api/v1/offerings/9999"))
				.andExpect(status().is4xxClientError());	
	}	
		
	//test error delete offering 9999
	//should return 4xx
	@Test
	public void deleteOfferingsNonExisting() throws Exception {
		mockMvc.perform(delete("/api/v1/offerings/9999"))
				.andExpect(status().is4xxClientError());	
	}	

	
	
	
    
	
}