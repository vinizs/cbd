package edu.tus.offering;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApplicationTest{

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
  
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getOffering() throws Exception {
		mockMvc.perform(get("/api/v1/offerings/1")).andExpect(status().isOk())		
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.offeringId").value("1")).andExpect(jsonPath("$.courseId").value("9"));
	}

	
	@Test
	public void postOffering() throws Exception {
		mockMvc.perform(post("/api/v1/offerings")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\n"
	            		+ "    \"courseId\": 9,\n"
	            		+ "    \"startDateTime\": \"2021-10-11 10:00\",\n"
	            		+ "    \"endDateTime\": \"2021-10-11 11:00\"\n"
	            		+ "}"))
	            .andDo(print())
	            .andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void postOfferingDateTime() throws Exception {
		mockMvc.perform(post("/api/v1/offerings")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\n"
	            		+ "    \"courseId\": 8,\n"
	            		+ "    \"startDateTime\": \"2021-10-11 12:00\",\n"
	            		+ "    \"endDateTime\": \"2021-10-11 11:00\"\n"
	            		+ "}"))
	            .andDo(print())
	            .andExpect(status().is4xxClientError());
	}

	@Test
	public void postOfferingEmptyFields() throws Exception {
		mockMvc.perform(post("/api/v1/offerings")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\n"
	            		+ "    \"courseId\": 8,\n"
	            		+ "    \"startDateTime\": \"\",\n"
	            		+ "    \"endDateTime\": \"2021-10-11 11:00\"\n"
	            		+ "}"))
	            .andDo(print())
	            .andExpect(status().is4xxClientError());
	}

	
	
	
	
}