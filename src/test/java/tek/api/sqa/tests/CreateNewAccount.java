package tek.api.sqa.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.sqa.base.APITestConfig;
import tek.api.utility.EndPoints;

public class CreateNewAccount extends APITestConfig{

	@Test
	public void createNewAccount() {
		String token = getValidToken();
		
		
		Map<String,String> requestBody = new HashMap<>();
		requestBody.put("email","mortrz001@gmail.com");
		requestBody.put("firstName", "Morteza");
		requestBody.put("lastName", "Sharifi");
		requestBody.put("title", "Mr.");
		requestBody.put("gender", "MALE");
		requestBody.put("maritalStatus", "SINGLE");
		requestBody.put("employmentStatus", "Tester");
		requestBody.put("dateOfBirth", "1996-11-28");
		
		RequestSpecification request = RestAssured.given().body(requestBody);
		request.header("Authorization" , "Bearer " + token);
		request.contentType(ContentType.JSON);
		Response response =request.when().post(EndPoints.CREATE_NEW_ACCOUNT.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 201);
		String expectedEmail = response.jsonPath().get("email");
		Assert.assertEquals(expectedEmail, "mortrz001@gmail.com");
		
		
	}
	
}
