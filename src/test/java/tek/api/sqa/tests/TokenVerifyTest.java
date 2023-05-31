package tek.api.sqa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.sqa.base.APITestConfig;
import tek.api.utility.EndPoints;

public class TokenVerifyTest extends APITestConfig {
	
	@Test
	public void verifyValidToken() {
		// Generate Valid token
		String validToken = getValidToken();
		RequestSpecification request = RestAssured.given();
		request.queryParam("token",validToken);
		request.queryParam("username", "supervisor");
		
		Response response = request.when().get(EndPoints.TOKEN_VERIFY.getValue());
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	@Test
	public void verifyInvalidToken() {
		// Generate Valid token
		String invalidToken  = "eyJraWQiOiJUZWtzY2hvb2wgSW5zdXJhbmNlIEFwcGxpY2F0aW9uIiwidHlwIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJhdWQiOiJVc2VyIG1hbmFnZW1lbnQgcG9ydGFsIiwic3ViIjoiU1VQRVJWSVNPUiIsImlzcyI6IlRlayBJbnN1cmFuY2UgQXBwbGljYXRpb24iLCJBdXRob3JpdGllcyI6WyJBRE1JTiJdLCJleHAiOjE2ODQ5NzM3OTMsImlhdCI6MTY4NDg4NzM5M30.LTrmpWohgNGTKmzChztUiUEVahtnR5_K3Ua0xucK8KhvLc1-L_77r6H3OpBZ9tXsF4xIfwnIX_Wo0wbLFHZCFg";
		RequestSpecification request = RestAssured.given();
		request.queryParam("token",invalidToken);
		request.queryParam("username", "supervisor");
		
		Response response = request.when().get(EndPoints.TOKEN_VERIFY.getValue());
		Assert.assertEquals(response.getStatusCode(), 400);
		response.prettyPrint();
		String message = response.jsonPath().get("errorMessage");
		Assert.assertEquals(message, "Token Expired or Invalid Token");
	}

}
