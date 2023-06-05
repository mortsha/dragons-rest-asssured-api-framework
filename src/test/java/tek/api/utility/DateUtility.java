package tek.api.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
	
	public static String toDateString (Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		return formatter.format(date);
	}

}
