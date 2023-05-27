package tek.api.utility;

public enum EndPoints {

	TOKEN_GENERATION("/api/token"),
	TOKEN_VERIFY("/api/token/verify"),
	CREATEA_CCOUNT("/api/accounts/add-primary-account"),
	GET_ALL_ACCOUNT("/api/accounts/get-all-accounts"),
	GET_ACCOUNT("/api/accounts/get-account"),
	CREATE_NEW_ACCOUNT("/api/accounts/add-primary-account"),
	ADD_PHONE("/api/accounts/add-account-phone"),
	ADD_CAR("/api/accounts/add-account-car"),
	ADD_ADDRESS("/api/accounts/add-account-address");
	
	private String value;

	EndPoints(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
