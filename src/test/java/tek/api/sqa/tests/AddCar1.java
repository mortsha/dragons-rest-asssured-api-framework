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

public class AddCar1 extends APITestConfig {

		@Test
		public void addCartTest1() {
			String token = getValidToken();
			
			Map<String,String> body = new HashMap<>();
			body.put("make","Toyota");
			body.put("model", "Camry");
			body.put("year", "2023");
			body.put("licensePlate", "OUYT 8745");
			
			RequestSpecification request = RestAssured.given().body(body);
			request.queryParam("primaryPersonId", 8664);
			request.header("Authorization" , "Bearer " + token);
			request.contentType(ContentType.JSON);
			Response response = request.when().post(EndPoints.ADD_CAR.getValue());
			response.prettyPrint();
			Assert.assertEquals(response.getStatusCode(), 201);
			String licensePlate = response.jsonPath().get("licensePlate");
			Assert.assertEquals(licensePlate, "OUYT 8745");
		}
	
		                      
}
