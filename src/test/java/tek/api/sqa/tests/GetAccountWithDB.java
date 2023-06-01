package tek.api.sqa.tests;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.sqa.base.DatabaseConfig;
import tek.api.utility.EndPoints;

public class GetAccountWithDB extends DatabaseConfig {

	@Test
	public void getAccountWithDBQuery() throws SQLException {
		String query = "select * from primary_person where id = 8720";
		ResultSet resultSet = runQuery(query);
		long queryResultId = 0;
		while (resultSet.next()) {
			queryResultId = resultSet.getLong("id");
		}
		String validToken = getValidToken();
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + validToken);
		request.queryParam("primaryPersonId", queryResultId);

		Response response = request.when().get(EndPoints.GET_ACCOUNT.getValue());
		Assert.assertEquals(response.getStatusCode(), 200);
		int actualPrimaryPersonId = response.jsonPath().get("primaryPerson.id");
		Assert.assertEquals(actualPrimaryPersonId, queryResultId);
	}

}
