package tek.api.sqa.tests;

import static tek.api.utility.DateUtility.toDateString;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.service.ExtentTestManager;
import com.mysql.cj.xdevapi.Result;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.model.PrimaryAccount;
import tek.api.model.PrimaryAddressAccount;
import tek.api.model.PrimaryCarAccount;
import tek.api.model.PrimaryPhoneAccount;
import tek.api.sqa.base.DatabaseConfig;
import tek.api.utility.EndPoints;

public class E2EAccountCreateTest extends DatabaseConfig {

	private long uniqueId;

	@Test
	public void e2EAccountCreate() {
		createAccountWithDBVerification();
		addCarAccount();
		addPhoneAccount();
		addAddressAccount();
	}

//	@Test
	public void createAccountWithDBVerification() {
		RequestSpecification request = getRequestWithValidToken();
		PrimaryAccount requestBody = createAccountData();
		request.body(requestBody);
		Response response = request.when().post(EndPoints.CREATE_NEW_ACCOUNT.getValue());
		response.prettyPrint();
		ExtentTestManager.getTest().info(response.asPrettyString());
		asserter.assertResponseStatus(response, 201);
		PrimaryAccount responseBody = response.as(PrimaryAccount.class);
		uniqueId = responseBody.getId();

		// validate Database with created account

		StringBuilder stb = new StringBuilder();
		stb.append("Select * from primary_person where id = ");
		stb.append(uniqueId);
		ResultSet createdAccountResultSet = runQuery(stb.toString());
		CreateAccountWithDBVerification.assertCreatedAccount(responseBody, createdAccountResultSet);

	}

//	@Test(dependsOnMethods = "createAccountWithDBVerification")
	public void addCarAccount() {
		RequestSpecification requestAddCar = getRequestWithValidToken();
		requestAddCar.queryParam("primaryPersonId", uniqueId);
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
		CreateAccountWithCarTest.assertCreatedAccountAddCar(responseBodyCar, addCarResultSet);

	}

//	@Test(dependsOnMethods = "createAccountWithDBVerification")
	public void addPhoneAccount() {
		RequestSpecification requestAddPhone = getRequestWithValidToken();
		requestAddPhone.queryParam("primaryPersonId", uniqueId);
		PrimaryPhoneAccount phoneRequestBody = addPhoneData();
		requestAddPhone.body(phoneRequestBody);

		Response response = requestAddPhone.when().post(EndPoints.ADD_PHONE.getValue());
		response.prettyPrint();
		ExtentTestManager.getTest().info(response.asPrettyString());
		asserter.assertResponseStatus(response, 201);
		PrimaryPhoneAccount responseBodyPhone = response.as(PrimaryPhoneAccount.class);

		// validate
		StringBuilder stbPhone = new StringBuilder();
		stbPhone.append("select * from primary_person_phone where primary_person_id = ");
		stbPhone.append(uniqueId);

		ResultSet addPhoneResultSet = runQuery(stbPhone.toString());
		CreateAccountAddPhone.assertCreatedAccountAddPhone(responseBodyPhone, addPhoneResultSet);

	}

//	@Test(dependsOnMethods = "createAccountWithDBVerification")
	public void addAddressAccount() {

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
		CreateAccountAddAddress.assertCreatedAccountAddAddress(responseBodyAddress, addAddressResultSet);
	}
}
