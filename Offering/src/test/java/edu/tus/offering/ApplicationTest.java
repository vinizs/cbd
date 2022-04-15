package edu.tus.offering;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class ApplicationTest{

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	
	
//	@MockBean
//	private OfferingRepository oRepo;
//	
//	@SpyBean
//	private OfferingService offeringService;
//
////	@InjectMocks
////	private OrderController orderController;

  
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testOffering() throws Exception {
		mockMvc.perform(get("/api/v1/offerings/2")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.offeringId").value("2")).andExpect(jsonPath("$.courseId").value("4"));

		
		
		
	}

}