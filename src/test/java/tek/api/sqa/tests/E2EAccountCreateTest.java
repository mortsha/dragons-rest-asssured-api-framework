package tek.api.sqa.tests;

import org.testng.annotations.Test;

import tek.api.sqa.base.DatabaseConfig;

public class E2EAccountCreateTest extends DatabaseConfig{
	
	private CreateAccountWithDBVerification createdAccount = new CreateAccountWithDBVerification();
	
	@Test
	public void e2EAccountCreate() {
		
		createdAccount.createAccountTestDB();
	}

}
