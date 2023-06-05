package tek.api.sqa.tests;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.service.ExtentTestManager;
import com.mysql.cj.xdevapi.Result;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.model.PrimaryAccount;
import tek.api.sqa.base.DatabaseConfig;
import tek.api.utility.Asserter;
import static tek.api.utility.DateUtility.toDateString;
import tek.api.utility.EndPoints;

public class CreateAccountWithDBVerification extends DatabaseConfig {

	@Test
	public void createAccountTestDB() {
		RequestSpecification request = getRequestWithValidToken();
		PrimaryAccount requestBody = createAccountData();
		request.body(requestBody);
		Response response = request.when().post(EndPoints.CREATE_NEW_ACCOUNT.getValue());
		response.prettyPrint();
		ExtentTestManager.getTest().info(response.asPrettyString());
		asserter.assertResponseStatus(response, 201);
		PrimaryAccount responseBody = response.as(PrimaryAccount.class);

		// validate Database with created account

		StringBuilder stb = new StringBuilder();
		stb.append("Select * from primary_person where id = ");
		stb.append(responseBody.getId());
		ResultSet createdAccountResultSet = runQuery(stb.toString());
		assertCreatedAccount(responseBody, createdAccountResultSet);

	}

	public void assertCreatedAccount(PrimaryAccount responseBody, ResultSet queryResult) {
		try {
			if (queryResult.next()) {
				asserter.isEqual(responseBody.getId(), queryResult.getLong("id"));
				asserter.isEqual(responseBody.getEmail(), queryResult.getString("email"));
				asserter.isEqual(responseBody.getFirstName(), queryResult.getString("first_name"));
				asserter.isEqual(responseBody.getLastName(), queryResult.getString("last_name"));
				asserter.isEqual(responseBody.getTitle(), queryResult.getString("title"));
				asserter.isEqual(responseBody.getGender(), queryResult.getString("gender"));
				asserter.isEqual(responseBody.getMaritalStatus(), queryResult.getString("marital_status"));
				asserter.isEqual(responseBody.getEmploymentStatus(), queryResult.getString("employment_status"));

				String actualDOB = toDateString(responseBody.getDateOfBirth());
				String expectedDOB = toDateString(queryResult.getDate("date_of_birth"));
				System.out.println(actualDOB);
				System.out.println(expectedDOB);
				asserter.isEqual(actualDOB, expectedDOB);
				
			} else {
				asserter.fail("ResulSet is Empty");
			}
		} catch (SQLException e) {
			asserter.fail("Failed deu to SQL Exeption with message " + e.getMessage());
		}
	}

}
