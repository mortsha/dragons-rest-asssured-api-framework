package tek.api.model;

public class PrimaryAddressAccount {

	private long id;
	private String addressLine;
	private String addressType;
	private String city;
	private String countryCode;
	private String postalCode;
	private String state;

	public PrimaryAddressAccount() {

	}

	public PrimaryAddressAccount(long id, String addressLine, String addressType, String city, String countryCode,
			String postalCode, String state) {
		this.id = id;
		this.addressLine = addressLine;
		this.addressType = addressType;
		this.city = city;
		this.countryCode = countryCode;
		this.postalCode = postalCode;
		this.state = state;
	}

	public PrimaryAddressAccount(String addressLine, String addressType, String city, String countryCode,
			String postalCode, String state) {
		this.addressLine = addressLine;
		this.addressType = addressType;
		this.city = city;
		this.countryCode = countryCode;
		this.postalCode = postalCode;
		this.state = state;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
