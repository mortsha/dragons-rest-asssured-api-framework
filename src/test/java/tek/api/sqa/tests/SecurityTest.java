package tek.api.sqa.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.sqa.base.APITestConfig;
import tek.api.utility.EndPoints;

public class SecurityTest extends APITestConfig {
	
	@Test
	public void testGenerateTokenValidUser() {
		
		// First setup to set Base URL done at BeforeMethod Annotation
		// 2) Prepare Request.
		// Create Request Body
		// Option 1) Creating a Map
		// Option 2) Creating an Encapsulated Object.
		// Option 3) String as JSON Object. (not recommended)
		
		
		// Map option
		Map<String,String> tokenRequestBody = new HashMap<>();
		tokenRequestBody.put("username", "supervisor");
		tokenRequestBody.put("password","tek_supervisor");
		// Given prepare request.
		RequestSpecification request = RestAssured.given().body(tokenRequestBody);
		// set Content type.
		request.contentType(ContentType.JSON);
		// When sending request to end-point
		Response response = request.when().post(EndPoints.TOKEN_GENERATION.getValue());
		// optional to print response in console.
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(),200);
		//Assert token is not null.
		// Using jsonPath we can get value of any entity in response
		String generatedToken = response.jsonPath().get("token");
		Assert.assertNotNull(generatedToken);
		
	}
	@Test
	public void testGenerateTokenValidUser1() {
		Map<String,String> tokenRequestBody = new HashMap<>();
		tokenRequestBody.put("username", "supervisor");
		tokenRequestBody.put("password", "tek_supervisor");
		
		RequestSpecification request = RestAssured.given().body(tokenRequestBody);
		request.contentType(ContentType.JSON);
		Response response = request.when().post(EndPoints.TOKEN_GENERATION.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		String generatedToken = response.jsonPath().get("token");
		Assert.assertNotNull(generatedToken);
	}
	@Test(dataProvider = "invalidTokenData")
	public void testGenerateTokenInvalidUsername(String username,String password,String expectedErrorMessage) {
		Map<String,String> tokenRequestBody = new HashMap<>();
		tokenRequestBody.put("username",username);
		tokenRequestBody.put("password",password);
		
		RequestSpecification request = RestAssured.given().body(tokenRequestBody);
		request.contentType(ContentType.JSON);
		Response response = request.when().post(EndPoints.TOKEN_GENERATION.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 400);
		String errorMessage = response.jsonPath().get("errorMessage");
		Assert.assertEquals(errorMessage,expectedErrorMessage);
		
		
	}

	@DataProvider(name = "invalidTokenData")
	private Object[][] invalidTokenData() {
		Object[][] data = {
				{ "WrongUser", "tek_supervisor", "User not found" },
				{ "supervisor", "WrongPassword", "Password Not Matched" }

		};
		return data;
	}
	
}
