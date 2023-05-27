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

public class AddPhoneTest1 extends APITestConfig{
	
	@Test
	public void addPhoneTest1() {
		String token = getValidToken();
		Map<String,String> requestBody = new HashMap<>();
		requestBody.put("phoneNumber", "2365487930");
		requestBody.put("phoneExtension", "");
		requestBody.put("phoneTime", "Morning");
		requestBody.put("phoneType", "Mobile");
		requestBody.put("current", "true");
		
		RequestSpecification request = RestAssured.given().body(requestBody);
		request.queryParam("primaryPersonId", 8662);
		request.header("Authorization" , "Bearer " + token);
		request.contentType(ContentType.JSON);
		Response response = request.when().post(EndPoints.ADD_PHONE.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 201);
		String phoneNumber = response.jsonPath().get("phoneNumber");
		Assert.assertEquals(phoneNumber, "2365487930");
		
	}

}
