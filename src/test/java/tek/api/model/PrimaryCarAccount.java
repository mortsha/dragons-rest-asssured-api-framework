package tek.api.model;

public class PrimaryCarAccount {

	private long id;
	private String make;
	private String model;
	private long year;
	private String licensePlate;

	public PrimaryCarAccount() {

	}

	public PrimaryCarAccount(long id, String make, String model, long year, String licensePlate) {
		this.id = id;
		this.make = make;
		this.model = model;
		this.year = year;
		this.licensePlate = licensePlate;
	}

	public PrimaryCarAccount(String make, String model, long year, String licensePlate) {
		this.make = make;
		this.model = model;
		this.year = year;
		this.licensePlate = licensePlate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

}
