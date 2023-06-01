package tek.api.sqa.tests;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.model.PrimaryAccount;
import tek.api.sqa.base.DatabaseConfig;
import tek.api.utility.DataGenerator;
import tek.api.utility.EndPoints;

public class HomeWork2 extends DatabaseConfig {

	private DataGenerator data = new DataGenerator();
	private String emailCreated;
	private long accountId;

	@Test
	public void createAccountHomeWorkTest() {
		String token = getValidToken();

		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + token);
		request.contentType(ContentType.JSON);

		PrimaryAccount requestBody = new PrimaryAccount();
		String firstName = data.getFirstName();
		String lastName = data.getLastName();
		requestBody.setEmail(data.getEmail(firstName, lastName, "canada.com"));
		requestBody.setFirstName(firstName);
		requestBody.setLastName(lastName);
		requestBody.setTitle("Mr.");
		requestBody.setGender("MALE");
		requestBody.setMaritalStatus("SINGLE");
		requestBody.setEmploymentStatus(data.getJobTitle());
		requestBody.setDateOfBirth(data.getDateOfBirth());

		request.body(requestBody);

		Response response = request.when().post(EndPoints.CREATE_NEW_ACCOUNT.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 201);

		PrimaryAccount responseBody = response.as(PrimaryAccount.class);
		Assert.assertEquals(responseBody.getEmail(), requestBody.getEmail());
		Assert.assertEquals(responseBody.getFirstName(), requestBody.getFirstName());
		accountId = responseBody.getId();
		emailCreated = responseBody.getEmail();

	}

	@Test
	public void getAccountWithDBQueryHomeWork() throws SQLException {
		String returnAllAccountQueryWithId = "select * from primary_person where id = " + accountId;

		String returnAllAccountQuery = "select * from primary_person order by id desc";
		ResultSet resultSet = runQuery(returnAllAccountQuery);

		String token = getValidToken();
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer " + token);
		request.queryParam("primaryPersonId", accountId);

		Response response = request.when().get(EndPoints.GET_ACCOUNT.getValue());
		Assert.assertEquals(response.getStatusCode(), 200);

		String validateAccount = "select * from primary_person where id = " + accountId;
		ResultSet validateAccountResult = runQuery(validateAccount);

		PrimaryAccount responseBody = response.jsonPath().getObject("primaryPerson", PrimaryAccount.class);

		if (validateAccountResult.next()) {
			String expectedEmail = validateAccountResult.getString("email");
			String expectedFirstName = validateAccountResult.getString("first_name");
			String expectedLastName = validateAccountResult.getString("last_name");
			String expectedTitle = validateAccountResult.getString("title");
			String expectedGender = validateAccountResult.getString("gender");
			String expectedMaritalStatus = validateAccountResult.getString("marital_status");
			String expectedEmploymentStatus = validateAccountResult.getString("employment_status");
			String expectedDateOfBirth = validateAccountResult.getString("date_of_birth");

			Assert.assertEquals(responseBody.getEmail(), expectedEmail);
			Assert.assertEquals(responseBody.getFirstName(), expectedFirstName);
			Assert.assertEquals(responseBody.getLastName(), expectedLastName);
			Assert.assertEquals(responseBody.getTitle(), expectedTitle);
			Assert.assertEquals(responseBody.getGender(), expectedGender);
			Assert.assertEquals(responseBody.getMaritalStatus(), expectedMaritalStatus);
			Assert.assertEquals(responseBody.getEmploymentStatus(), expectedEmploymentStatus);
//			Assert.assertEquals(responseBody.getDateOfBirth(), expectedDateOfBirth);

		} else {
			Assert.fail("Test Fail validate account test did not return result for id " + accountId);
		}

	}
}
