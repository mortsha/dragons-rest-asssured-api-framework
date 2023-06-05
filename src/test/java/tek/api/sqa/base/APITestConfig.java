package tek.api.sqa.base;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerAdapter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.model.PrimaryAccount;
import tek.api.model.PrimaryAddressAccount;
import tek.api.model.PrimaryCarAccount;
import tek.api.model.PrimaryPhoneAccount;
import tek.api.utility.Asserter;
import tek.api.utility.DataGenerator;
import tek.api.utility.EndPoints;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ExtentITestListenerAdapter.class})
public class APITestConfig extends BaseConfig {

	private DataGenerator dataGenerator = new DataGenerator();
	public Asserter asserter = new Asserter();
	
    @BeforeMethod
    public void setupApiTest() {
    	// First Step Setup BaseURL RestAssured
        System.out.println("Setting up Test");
        RestAssured.baseURI = getBaseUrl();
    }
    
    public String getValidToken() {
    	Map<String,String> tokenRequestBody = new HashMap<>();
		tokenRequestBody.put("username", "supervisor");
		tokenRequestBody.put("password", "tek_supervisor");
		
		RequestSpecification request = RestAssured.given().body(tokenRequestBody);
		request.contentType(ContentType.JSON);
		Response response = request.when().post(EndPoints.TOKEN_GENERATION.getValue());
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		String token = response.jsonPath().get("token");
		return token;
    }
    
    
    public RequestSpecification getRequestWithValidToken() {
    	String token = getValidToken();
    	return RestAssured.given().header("Authorization", "Bearer " + token)
    			.contentType(ContentType.JSON);
    }
    
    private Object[][]validToken(){
    	Object[][] data = {
    			{"supervisor" , "tek_supervisor",}
    	};
    	return data;
    }
    
    public PrimaryAccount createAccountData() {
    	PrimaryAccount account = new PrimaryAccount();
    	String firstName = dataGenerator.getFirstName();
    	String lastName = dataGenerator.getLastName();
    	account.setEmail(dataGenerator.getEmail(firstName , lastName, "gmail.com"));
    	account.setFirstName(firstName);
    	account.setLastName(lastName);
    	account.setTitle("Mr.");
    	account.setGender("MALE");
    	account.setMaritalStatus("SINGLE");
    	account.setEmploymentStatus(dataGenerator.getJobTitle());
    	account.setDateOfBirth(dataGenerator.getDateOfBirth());
    
    	return account;
    }
    
    public PrimaryCarAccount addCarData() {
    	PrimaryCarAccount carData = new PrimaryCarAccount();
    	carData.setMake("Toyota");
    	carData.setModel("Camry");
    	carData.setLicensePlate(dataGenerator.getRandomLicense());
    	carData.setYear(2023);
    	return carData;
    }
    
    public PrimaryPhoneAccount addPhoneData() {
    	PrimaryPhoneAccount phoneData = new PrimaryPhoneAccount();
    	phoneData.setPhoneExtension(dataGenerator.getExtension());
    	phoneData.setPhoneNumber(dataGenerator.getPhoneNumber());
    	phoneData.setPhoneTime("Morning");
    	phoneData.setPhoneType("Mobile");
    	return phoneData;
    }
    
    public PrimaryAddressAccount addAddressData() {
    	PrimaryAddressAccount addressData = new PrimaryAddressAccount();
    	addressData.setAddressLine(dataGenerator.getAddressLine());
    	addressData.setAddressType("Home");
    	addressData.setCity(dataGenerator.getCity());
    	addressData.setCountryCode(dataGenerator.getCountryCode());
    	addressData.setPostalCode(dataGenerator.getPostalCode());
    	addressData.setState(dataGenerator.getState());
    	return addressData;
    	
    }
}
