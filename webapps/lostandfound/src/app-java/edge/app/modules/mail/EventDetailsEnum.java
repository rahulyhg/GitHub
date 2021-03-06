package edge.app.modules.mail;

import edge.core.modules.mailSender.EventDetails;

public class EventDetailsEnum {
	
	public static EventDetails TAG_CREATION_SAVED =
			new EventDetails(
					"Congratulations! Your Lost And Found ID has been successfully Saved!. :)",
					"TagCreationSaved.html"
					);
	
	public static EventDetails TAG_UPDATE_OTP =
			new EventDetails(
					"One Time Password for Tag Update!.",
					"TagUpdateOtp.html"
					);
	
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
		

	public static EventDetails PROBABLE_MATCH_FOUND =
			new EventDetails(
			"We have found a probable match :). Please review.",
			"ProbableMatchFound.html"
			);
		
	public static EventDetails MATCH_NOT_FOUND =
			new EventDetails(
			"We apologies but there is no 'Found' report submitted yet! :( We would inform you as soon as we find one.",
			"MatchNotFound.html"
			);

}
