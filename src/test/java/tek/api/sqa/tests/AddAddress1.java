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

public class AddAddress1 extends APITestConfig {

	@Test
	public void addAddressToAccount1() {
		String token = getValidToken();
		
		Map<String, String> body = new HashMap<>();
		body.put("addressType", "Home");
		body.put("addressLine1", "4585 alaki st");
		body.put("city", "Toronto");
		body.put("state", "Ontraio");
		body.put("postalCode", "95856");
		body.put("countryCode", "");
		body.put("current", "true");
		
		RequestSpecification request = RestAssured.given().body(body);
		request.queryParam("primaryPersonId", 8720);
		request.header("Authorization" , "Bearer " + token);
		request.contentType(ContentType.JSON);
		
		Response response = request.when().post(EndPoints.ADD_ADDRESS.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 201);
		String address = response.jsonPath().getString("addressLine1");
		Assert.assertEquals(address, "4585 alaki st");
		
		
				
	}
	
}
