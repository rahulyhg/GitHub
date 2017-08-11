package edge.app.modules.mail;

import edge.core.modules.mailSender.EventDetails;

public class EventDetailsEnum {
	public static EventDetails LOST_REQUEST_SAVED =
			new EventDetails(
			"Your Lost Request has been successfully Submitted! We would find it soon.. :)",
			"LostRequestSaved.html"
			);
	
	public static EventDetails FOUND_REQUEST_SAVED =
			new EventDetails(
			"Thank You to be so selfless! Found Request has been saved successfully!",
			"FoundRequestSaved.html"
			);
	
	public static EventDetails MATCH_FOUND =
			new EventDetails(
			"Hurray!! Cogratulations.. We have found a match :). Here are the details.",
			"MatchFound.html"
			);
}
