package org.gmm.gatewayserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayApplicationTests {

	@Test
	public void contextLoads() {
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		String testUrl = "http://localhost:8181";
		 
		ResponseEntity<String> response = testRestTemplate
		  .getForEntity(testUrl + "/book-service/books", String.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertNotNull(response.getBody());
		
		response = testRestTemplate  
				.getForEntity(testUrl + "/book-service/books/1", String.class);
				Assert.assertEquals(HttpStatus.FOUND, response.getStatusCode());
				Assert.assertEquals("http://localhost:8181/login", response.getHeaders()
				.get("Location").get(0));
	
				
				MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
				form.add("username", "user");
				//default password que se genera cuando se levanta la app
				form.add("password", "f7d313f0-65d7-49cb-9494-ac0d5e82862a");
				response = testRestTemplate
				  .postForEntity(testUrl + "/login", form, String.class);
				
				String sessionCookie = response.getHeaders().get("Set-Cookie")
						  .get(0).split(";")[0];
						HttpHeaders headers = new HttpHeaders();
						headers.add("Cookie", sessionCookie);
						HttpEntity<String> httpEntity = new HttpEntity<>(headers);
						
						response = testRestTemplate.exchange(testUrl + "/book-service/books/1",
								  HttpMethod.GET, httpEntity, String.class);
								Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
								Assert.assertNotNull(response.getBody());
								
								form.clear();
								form.add("username", "user");
								form.add("password", "f7d313f0-65d7-49cb-9494-ac0d5e82862a");
								response = testRestTemplate
								  .postForEntity(testUrl + "/login", form, String.class);
								 
								sessionCookie = response.getHeaders().get("Set-Cookie").get(0).split(";")[0];
								headers = new HttpHeaders();
								headers.add("Cookie", sessionCookie);
								httpEntity = new HttpEntity<>(headers);
								 
								response = testRestTemplate.exchange(testUrl + "/rating-service/ratings/all",
								  HttpMethod.GET, httpEntity, String.class);
								Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
								Assert.assertNotNull(response.getBody());
								
								response = testRestTemplate.exchange(testUrl + "/discovery",
										  HttpMethod.GET, httpEntity, String.class);
										Assert.assertEquals(HttpStatus.OK, response.getStatusCode());						
	}

}
