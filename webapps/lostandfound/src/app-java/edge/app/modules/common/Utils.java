package edge.app.modules.common;

import edge.app.modules.foundRequest.FoundRequest;
import edge.app.modules.lostRequest.LostRequest;

public class Utils {

	public static String deriveMatchingKey(LostRequest lostRequest) {
		String returnValue = "";
		switch(lostRequest.getIdType()){
		case UNIQUE_ID: returnValue = lostRequest.getUniqueIdType() + "-" + lostRequest.getUniqueId(); break;
		case LOST_AND_FOUND_ID: returnValue = ""; break;
		case NONE:
				returnValue = lostRequest.getCity()  + "-" + lostRequest.getMonth() + "-" + lostRequest.getYear() + "-" + lostRequest.getBrandName();
				break;
		}
		return returnValue.replace(" ", "").toUpperCase();
	}
	
	public static String deriveMatchingKey(FoundRequest foundRequest) {
		String returnValue = "";
		switch(foundRequest.getIdType()){
		case UNIQUE_ID: returnValue = foundRequest.getUniqueIdType() + "-" + foundRequest.getUniqueId(); break;
		case LOST_AND_FOUND_ID: returnValue = foundRequest.getLostAndFoundId(); break;
		case NONE:
				returnValue = foundRequest.getCity()  + "-" + foundRequest.getMonth() + "-" + foundRequest.getYear() + "-" + foundRequest.getBrandName();
				break;
		}
		return returnValue.replace(" ", "").toUpperCase();
	}

}
