package tek.api.sqa.tests;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.service.ExtentTestManager;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.model.PrimaryAccount;
import tek.api.model.PrimaryAddressAccount;
import tek.api.model.PrimaryPhoneAccount;
import tek.api.sqa.base.DatabaseConfig;
import tek.api.utility.EndPoints;

public class CreateAccountAddAddress extends DatabaseConfig {

	
	@Test
	public void addAddressAccount() {
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

//		// add address
		
		RequestSpecification requestAddAddress = getRequestWithValidToken();
		requestAddAddress.queryParam("primaryPersonId", uniqueId);
		PrimaryAddressAccount addressRequestBody = addAddressData();
		requestAddAddress.body(addressRequestBody).log().all();
		
		Response responseAddress = requestAddAddress.when().post(EndPoints.ADD_ADDRESS.getValue());
		responseAddress.prettyPrint();
		ExtentTestManager.getTest().info(responseAddress.asPrettyString());
		asserter.assertResponseStatus(responseAddress, 201);
		PrimaryAddressAccount responseBodyAddress = responseAddress.as(PrimaryAddressAccount.class);
		
		
		// validate 
		
		StringBuilder stbAddress = new StringBuilder();
		stbAddress.append("select * from primary_person_mailing_address where primary_person_id = ");
		stbAddress.append(uniqueId);
		ResultSet addAddressResultSet = runQuery(stbAddress.toString());
		assertCreatedAccountAddAddress(responseBodyAddress,addAddressResultSet);
		
		
	}
	
	public static void assertCreatedAccountAddAddress(PrimaryAddressAccount responseBody, ResultSet queryResult) {
		try {
			if (queryResult.next()) {
				asserter.isEqual(responseBody.getAddressLine1(), queryResult.getString("address_line1"));
				asserter.isEqual(responseBody.getAddressType(), queryResult.getString("address_type"));
				asserter.isEqual(responseBody.getCity(), queryResult.getString("city"));
				asserter.isEqual(responseBody.getCountryCode(), queryResult.getString("country_code"));
				asserter.isEqual(responseBody.getPostalCode(), queryResult.getString("postal_code"));
				asserter.isEqual(responseBody.getState(), queryResult.getString("state"));
				asserter.isEqual(responseBody.getCurrent(), queryResult.getBoolean("current"));

			} else {
				asserter.fail("ResulSet is Empty");
			}
		} catch (SQLException e) {
			asserter.fail("Failed deu to SQL Exeption with message " + e.getMessage());
		}
	}
}
