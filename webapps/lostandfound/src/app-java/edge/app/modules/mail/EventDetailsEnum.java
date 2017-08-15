package edge.app.modules.mail;

import edge.core.modules.mailSender.EventDetails;

public class EventDetailsEnum {
	public static EventDetails LOST_REPORT_SAVED =
			new EventDetails(
			"Your Lost Report has been successfully Submitted! We would find it soon.. :)",
			"LostReportSaved.html"
			);
	
	public static EventDetails FOUND_REPORT_SAVED =
			new EventDetails(
			"Thank You to be so selfless! Found Report has been saved successfully!",
			"FoundReportSaved.html"
			);
	
	public static EventDetails MATCH_FOUND =
			new EventDetails(
			"Hurray!! Cogratulations.. We have found a match :). Here are the details.",
			"MatchFound.html"
			);
	

	public static EventDetails MATCH_NOT_FOUND =
			new EventDetails(
			"We apologies but there is no 'Found' report submitted yet! :( We would inform you as soon as we find one.",
			"MatchNotFound.html"
			);
	

	public static EventDetails MATCH_NOT_FOUND_LFI =
			new EventDetails(
			"We apologies but there is no 'Found' report submitted yet! :( We would inform you as soon as we find one.",
			"MatchNotFoundLFI.html"
			);
}
