package httptest;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import it.sander.aml.domain.model.SurveyModel;

public class httpTest {
	
	static ObjectMapper mapper = new MappingJackson2HttpMessageConverter().getObjectMapper();
	
	public static void main(String[] args) {
		
		
		//String token = httpTest.amlAuth();
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBzYW5kZXJhbWwuaXQiLCJyb2xlcyI6WyJXUklURV9HUFIiLCJBRE1JTiIsIlJFQURfR1BSIl0sImlzcyI6InNhbmRlcmFtbCIsImV4cCI6MTY3MDE2NDYyMCwiaWF0IjoxNjY5NzMyNjIwfQ.wnZToLoEeJKF-gX94pKuaEqpdrno4VQoyhwqN36tpSE";
		httpTest.amlKycServicesSubmit(token);
	}
	
	public static String amlAuth() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8084/aml/authentication";
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String req = "{\"email\": \"admin@sanderaml.it\", \"password\": \"adminpassword\"}";
		
		String token = restTemplate.postForObject(url, new HttpEntity<>(req, headers), String.class);
		Assertions.assertNotNull(token);
		
		return token;

	}
	
	public static void amlKycServicesSubmit(String token) {
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8085/amlKycServices/surveys/submit";
		
		// SurveyModel(UUID id, String group, String abi, String serviceCode, Date creationDate)
		SurveyModel survey = new SurveyModel(UUID.randomUUID(), "GROUP", "ABI", "ONLINE", new Date() );
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
		
		ObjectWriter writer = mapper.writerWithType(SurveyModel.class);
		String json = null;
		try {
			json = writer.writeValueAsString(survey);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
			return;
		}
		
		SurveyModel reponse = null;
		try {
		    reponse = restTemplate.postForObject(url, new HttpEntity<>(json, headers), SurveyModel.class);
		} catch (HttpClientErrorException e) {
			System.out.print(e.getMessage());
		}
		Assertions.assertNotNull(reponse);

	}
	
	

}
