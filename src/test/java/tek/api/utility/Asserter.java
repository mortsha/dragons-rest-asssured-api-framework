package tek.api.utility;

import org.testng.Assert;

import io.restassured.response.Response;

public class Asserter {

	public void assertResponseStatus(Response response, int code) {
		Assert.assertEquals(response.getStatusCode(), code);
	}

	public void isEqual(String actual, String expected) {
		Assert.assertEquals(actual, expected);

	}

	public void isEqual(int actual, int expected) {
		Assert.assertEquals(actual, expected);

	}

	public void isEqual(long actual, long expected) {
		Assert.assertEquals(actual, expected);

	}

	public void fail(String message) {
		Assert.fail(message);
	}

}
