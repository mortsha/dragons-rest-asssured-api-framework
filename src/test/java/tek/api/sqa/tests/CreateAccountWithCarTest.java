package tek.api.sqa.tests;

import static tek.api.utility.DateUtility.toDateString;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.service.ExtentTestManager;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.model.PrimaryAccount;
import tek.api.model.PrimaryCarAccount;
import tek.api.sqa.base.DatabaseConfig;
import tek.api.utility.EndPoints;

public class CreateAccountWithCarTest extends DatabaseConfig {
	
	private CreateAccountWithDBVerification createdAccount = new CreateAccountWithDBVerification();

	@Test
	public void createAccountAddCarTest() {
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
		createdAccount.assertCreatedAccount(responseBody, createdAccountResultSet);
		long uniqueId = responseBody.getId();
		
		// add car
		RequestSpecification requestAddCar = getRequestWithValidToken();
		requestAddCar.queryParam("primaryPersonId",uniqueId);
		PrimaryCarAccount carRequestBody = addCarData();
		requestAddCar.body(carRequestBody);
		Response responseAddCar = requestAddCar.when().post(EndPoints.ADD_CAR.getValue());
		responseAddCar.prettyPrint();
		ExtentTestManager.getTest().info(responseAddCar.asPrettyString());
		asserter.assertResponseStatus(responseAddCar, 201);
		PrimaryCarAccount responseBodyCar = responseAddCar.as(PrimaryCarAccount.class);
		
		// validate database with created account with add car
		StringBuilder stbCar = new StringBuilder();
		stbCar.append("select * from primary_person_car where primary_person_id = ");
		stbCar.append(uniqueId);
		ResultSet addCarResultSet = runQuery(stbCar.toString());
		assertCreatedAccountAddCar(responseBodyCar,addCarResultSet);
		
		
	}
	
	
	public void assertCreatedAccountAddCar(PrimaryCarAccount responseBody, ResultSet queryResult) {
		try {
			if (queryResult.next()) {
				asserter.isEqual(responseBody.getMake(), queryResult.getString("make"));
				asserter.isEqual(responseBody.getModel(), queryResult.getString("model"));
				asserter.isEqual(responseBody.getYear(), queryResult.getLong("year"));
				asserter.isEqual(responseBody.getLicensePlate(), queryResult.getString("license_plate"));

			} else {
				asserter.fail("ResulSet is Empty");
			}
		} catch (SQLException e) {
			asserter.fail("Failed deu to SQL Exeption with message " + e.getMessage());
		}
	}
	
}
