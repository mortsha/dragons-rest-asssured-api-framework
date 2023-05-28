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

public class UpdateAddressAccount extends APITestConfig {
	
	@Test
	public void updateAddressAccount() {
		String token = getValidToken();
		
		Map<String, String> body = new HashMap<>();
		body.put("id", "2619");
		body.put("addressType", "Home");
		body.put("addressLine1", "9867 havid");
		body.put("city", "Windsor");
		body.put("state", "Ontraio");
		body.put("postalCode", "45678");
		body.put("countryCode", "");
		body.put("current", "true");
		
		RequestSpecification request = RestAssured.given().body(body);
		request.header("Authorization" , "Bearer " + token);
		request.queryParam("primaryPersonId", 8720);
		request.contentType(ContentType.JSON);
		
		Response response = request.when().put(EndPoints.UPDATE_ADDRESS.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 202);
		
		
	}
	

}
