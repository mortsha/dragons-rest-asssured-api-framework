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

public class UpdatePhoneAccount extends APITestConfig{

	@Test
	public void updatePhoneAccount () {
		String token = getValidToken();
		
		Map<String, String> body = new HashMap<>();
		body.put("id", "2809");
		body.put("phoneNumber", "3210336667");
		body.put("phoneExtension", "");
		body.put("phoneTime", "Morning");
		body.put("phoneType", "Mobile");
		
		RequestSpecification request = RestAssured.given().body(body);
		request.queryParam("primaryPersonId", 8720);
		request.header("Authorization" , "Bearer " + token);
		request.contentType(ContentType.JSON);
		
		Response response = request.when().put(EndPoints.UPDATE_PHONE.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		String phone = response.jsonPath().get("phoneNumber");
		Assert.assertEquals(phone, "3210336667");
		
	}
}
