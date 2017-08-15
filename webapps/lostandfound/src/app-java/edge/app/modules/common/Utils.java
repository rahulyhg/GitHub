package edge.app.modules.common;

import edge.app.modules.foundReport.FoundReport;
import edge.app.modules.lostReport.LostReport;

public class Utils {

	public static String deriveMatchingKey(LostReport lostReport) {
		String returnValue = "";
		switch(lostReport.getIdType()){
		case UNIQUE_ID: returnValue = lostReport.getUniqueIdType() + "-" + lostReport.getUniqueId(); break;
		case LOST_AND_FOUND_ID: returnValue = ""; break;
		case NONE:
				returnValue = lostReport.getCity()  + "-" + lostReport.getMonth() + "-" + lostReport.getYear() + "-" + lostReport.getBrandName();
				break;
		}
		return returnValue.replace(" ", "").toUpperCase();
	}
	
	public static String deriveMatchingKey(FoundReport foundReport) {
		String returnValue = "";
		switch(foundReport.getIdType()){
		case UNIQUE_ID: returnValue = foundReport.getUniqueIdType() + "-" + foundReport.getUniqueId(); break;
		case LOST_AND_FOUND_ID: returnValue = foundReport.getLostAndFoundId(); break;
		case NONE:
				returnValue = foundReport.getCity()  + "-" + foundReport.getMonth() + "-" + foundReport.getYear() + "-" + foundReport.getBrandName();
				break;
		}
		return returnValue.replace(" ", "").toUpperCase();
	}

}
