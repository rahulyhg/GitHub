package edge.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CoreDateUtils {
	static SimpleDateFormat standard = new SimpleDateFormat("yyyy-MM-dd");

	public static String dateToStandardSting(Date date) {
		return standard.format(date);
	}
	
	public static long getDaysBetweenDates(Date d1, Date d2){
		return TimeUnit.MILLISECONDS.toDays(d1.getTime() - d2.getTime());
	}
}
