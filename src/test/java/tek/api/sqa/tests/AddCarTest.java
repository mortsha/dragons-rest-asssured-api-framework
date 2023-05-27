package tek.api.sqa.tests;


import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.sqa.base.APITestConfig;

public class AddCarTest extends APITestConfig{

	public void addCarTest() {
		String token = getValidToken();
		Map<String,String> body = new HashMap<>();
		body.put("make", "Toyota");
		body.put("model", "Rav4");
		body.put("year", "2023");
		body.put("licensePlate", "kjig4862");
		
		RequestSpecification request = RestAssured.given().body(body);
		request.header("Authorization" , "Bearer " + token);
		request.queryParam("primaryPersonId", 8664);
		request.contentType(ContentType.JSON);
		Response response = request.when().post();
		
	}
	
}
