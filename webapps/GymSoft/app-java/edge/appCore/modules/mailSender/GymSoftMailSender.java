package edge.appCore.modules.mailSender;

import java.util.Date;

import edge.app.modules.clients.Client;
import edge.core.modules.mailSender.AppMailSender;
import edge.core.modules.parents.Parent;
import edge.core.utils.CoreDateUtils;

public class GymSoftMailSender {
	

	public static void notifyGym(String subjectInp, Parent parent, String text, String loggedInId) throws Exception {
		
		String today = CoreDateUtils.dateToStandardSting(new Date());
		String subject = subjectInp + " : " + today ;
		String[] toAddresses = new String[]{parent.getEmailId()};
		String[] ccAddresses = new String[]{};
		
		AppMailSender.sendEmail(parent.getName(), parent.getEmailId(), subject, text, toAddresses, ccAddresses);
	}
	
	public static void notifyGym(String subjectInp, Parent parent, Client client, String loggedInId) throws Exception {
		
		String today = CoreDateUtils.dateToStandardSting(new Date());
		String subject = subjectInp + " : " + today + " : " + client.getName();
		String text = "<B> Activity Log For Client : " + client.getName() + "</B> <br> <br>" + client.getActivity();
		String[] toAddresses = new String[]{parent.getEmailId()};
		String[] ccAddresses = new String[]{};
		
		AppMailSender.sendEmail(parent.getName(), parent.getEmailId(), subject, text, toAddresses, ccAddresses);
	}

	public static void notifyClient(String subjectInp, Parent parent, Client client, String loggedInId) throws Exception {
		
		String today = CoreDateUtils.dateToStandardSting(new Date());
		String subject = subjectInp + " : " + today + " : " + client.getName();
		String text = "<B> Activity Log For Client : " + client.getName() + "</B> <br> <br>" + client.getActivity();
		String[] toAddresses = new String[]{client.getEmailId()};
		String[] ccAddresses = new String[]{parent.getEmailId()};
		
		AppMailSender.sendEmail(parent.getName(), parent.getEmailId(), subject, text, toAddresses, ccAddresses);
	}
}
