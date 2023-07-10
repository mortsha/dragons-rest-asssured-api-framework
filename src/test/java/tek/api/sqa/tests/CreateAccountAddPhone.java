package tek.api.sqa.tests;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.service.ExtentTestManager;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.model.PrimaryAccount;
import tek.api.model.PrimaryCarAccount;
import tek.api.model.PrimaryPhoneAccount;
import tek.api.sqa.base.DatabaseConfig;
import tek.api.utility.EndPoints;

public class CreateAccountAddPhone extends DatabaseConfig{
	
		
	@Test
	public void addPhone() {
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
		CreateAccountWithDBVerification.assertCreatedAccount(responseBody, createdAccountResultSet);
		long uniqueId = responseBody.getId();
		
		
		// add phone
		
		RequestSpecification requestAddPhone = getRequestWithValidToken();
		requestAddPhone.queryParam("primaryPersonId",uniqueId);
		PrimaryPhoneAccount phoneRequestBody = addPhoneData();
		requestAddPhone.body(phoneRequestBody);
		
		Response responsePhone = requestAddPhone.when().post(EndPoints.ADD_PHONE.getValue());
		responsePhone.prettyPrint();
		ExtentTestManager.getTest().info(responsePhone.asPrettyString());
		asserter.assertResponseStatus(responsePhone, 201);
		PrimaryPhoneAccount responseBodyPhone = responsePhone.as(PrimaryPhoneAccount.class);
		
		// validate 
		StringBuilder stbPhone = new StringBuilder();
		stbPhone.append("select * from primary_person_phone where primary_person_id = ");
		stbPhone.append(uniqueId);
		
		ResultSet addPhoneResultSet = runQuery(stbPhone.toString());
		assertCreatedAccountAddPhone(responseBodyPhone,addPhoneResultSet);
		
	}

	public static void assertCreatedAccountAddPhone(PrimaryPhoneAccount responseBody, ResultSet queryResult) {
		try {
			if (queryResult.next()) {
				asserter.isEqual(responseBody.getPhoneExtension(), queryResult.getString("phone_extension"));
				asserter.isEqual(responseBody.getPhoneNumber(), queryResult.getString("phone_number"));
				asserter.isEqual(responseBody.getPhoneTime(), queryResult.getString("phone_time"));
				asserter.isEqual(responseBody.getPhoneType(), queryResult.getString("phone_type"));

			} else {
				asserter.fail("ResulSet is Empty");
			}
		} catch (SQLException e) {
			asserter.fail("Failed deu to SQL Exeption with message " + e.getMessage());
		}
	}
	
}
