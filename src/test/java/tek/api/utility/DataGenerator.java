package tek.api.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.github.javafaker.Faker;

public class DataGenerator {
	
	private Faker faker;
	
	public DataGenerator() {
		this.faker = new Faker();
	}
	
	public String getFirstName() {
		return faker.name().firstName();
	}
	
	public String getLastName() {
		return faker.name().lastName();
	}
	
	public String getEmail() {
		String firstName = getFirstName();
		String lastName = getLastName();
		
		return  firstName = "_" + lastName + "@gmail.com";
	}
	
	public String getEmail(String firstName , String lastName, String provider) {
		return firstName + "_" + lastName + "@" + provider;
	}
	
	public String getJobTitle() {
		return faker.job().position();
	}
	
	public Date getDateOfBirth() {
		Date date = faker.date().birthday();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = formatter.format(date);
		try {
			return formatter.parse(formattedDate);
		} catch (ParseException e) {
			throw new RuntimeException("Date Parse Exception ");
		}
	}
	public String getExtension() {
		return faker.phoneNumber().extension();
	}
	
	public String getPhoneNumber() {
		return faker.phoneNumber().phoneNumber();
	}
	
	public String getRandomLicense() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int count = 4;
		for (int i = 0; i < count; i++) {
			int randomIndex = random.nextInt(letters.length());
			char randomChar = letters.charAt(randomIndex);
			sb.append(randomChar);
		}
		sb.append(" ");
		for (int i = 0; i < 4; i++) {
			int randomDigit = (int) (Math.random() * 10);
			sb.append(randomDigit);
		}
		return sb.toString();
	}
	
	public String getCountryCode() {
		return faker.address().countryCode();
				
	}

	public String getCity() {
		return faker.address().city();
		
	}
	
	public String getState() {
		return faker.address().state();
	}
	
	public String getPostalCode() {
		String phoneNumber = "";
		for (int i = 0; i < 3; i++) {
			phoneNumber += (int) (Math.random() * 100);
		}
		return phoneNumber;
	}
	
	public String getAddressLine() {
		return faker.address().fullAddress();
	}
	
}
