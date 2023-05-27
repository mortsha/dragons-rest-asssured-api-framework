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

public class AddPhoneTest extends APITestConfig {

	@Test
	public void addPhoneToAccount () {
		String token = getValidToken();
		Map<String,String> body = new HashMap<>();
		body.put("phoneNumber", "2267895969");
		body.put("phoneExtension", "");
		body.put("phoneTime", "Morning");
		body.put("phoneType", "Mobile");

		RequestSpecification request = RestAssured.given().body(body);
		request.header("Authorization" , "Bearer " + token);
		request.queryParam("primaryPersonId", 8664);
		request.contentType(ContentType.JSON);
		
		Response response = request.when().post(EndPoints.ADD_PHONE.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 201);
		String phoneNumber = response.jsonPath().get("phoneNumber");
		Assert.assertEquals(phoneNumber, "2267895969");
		
	}
}
