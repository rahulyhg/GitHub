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
	

	public static EventDetails MATCH_NOT_FOUND =
			new EventDetails(
			"We apologies but there is no 'Found' request submitted yet! :( We would inform you as soon as we find one.",
			"MatchNotFound.html"
			);
	

	public static EventDetails MATCH_NOT_FOUND_LFI =
			new EventDetails(
			"We apologies but there is no 'Found' request submitted yet! :( We would inform you as soon as we find one.",
			"MatchNotFoundLFI.html"
			);
}
