package edge.appCore.modules.mailSender;

import edge.core.modules.mailSender.EventDetails;

public class EventDetailsEnum {
	
	public static EventDetails NEW_ACCT_CREATION =
			new EventDetails(
			"Congratulations! Your account has been successfully created!",
			"NewAccountCreationSuccess.html"
			);
	
	public static EventDetails SEND_VERIFICATION_CODE =
			new EventDetails(
			"Verification Code for Password Reset!",
			"VerificationCode.html"
			);
	
}
