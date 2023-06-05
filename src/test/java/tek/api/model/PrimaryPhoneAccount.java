package tek.api.model;

public class PrimaryPhoneAccount {

	private long id;
	private String phoneExtension;
	private String phoneNumber;
	private String phoneType;
	private String phoneTime;

	public PrimaryPhoneAccount() {
	}

	public PrimaryPhoneAccount(long id, String phoneExtension, String phoneNumber, String phoneType, String phoneTime) {
		this.id = id;
		this.phoneExtension = phoneExtension;
		this.phoneNumber = phoneNumber;
		this.phoneType = phoneType;
		this.phoneTime = phoneTime;
	}

	public PrimaryPhoneAccount(String phoneExtension, String phoneNumber, String phoneType, String phoneTime) {
		this.phoneExtension = phoneExtension;
		this.phoneNumber = phoneNumber;
		this.phoneType = phoneType;
		this.phoneTime = phoneTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhoneExtension() {
		return phoneExtension;
	}

	public void setPhoneExtension(String phoneExtension) {
		this.phoneExtension = phoneExtension;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getPhoneTime() {
		return phoneTime;
	}

	public void setPhoneTime(String phoneTime) {
		this.phoneTime = phoneTime;
	}

}
