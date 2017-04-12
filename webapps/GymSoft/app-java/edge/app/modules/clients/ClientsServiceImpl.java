package edge.app.modules.clients;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.common.AppConstants;
import edge.app.modules.memberships.Membership;
import edge.app.modules.memberships.MembershipsService;
import edge.app.modules.payments.Payment;
import edge.app.modules.payments.PaymentsService;
import edge.core.exception.AppException;
import edge.core.modules.auth.SecurityRoles;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.mailSender.AppMailSender;
import edge.core.modules.parents.Parent;
import edge.core.modules.parents.ParentsService;
import edge.core.utils.CoreDateUtils;

@WebService
@Component
public class ClientsServiceImpl implements ClientsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private MembershipsService membershipsService;
	
	@Autowired
	private PaymentsService paymentService;
	
	@Autowired
	private ParentsService parentsService;

	@Override
	@Transactional
	public NewClient saveAll(NewClient newClient, String loggedInId) throws Exception {
		Client client = newClient.getClient();
		Membership membership = newClient.getMembership();
		Payment payment = newClient.getPayment();
		
		saveClient(client, loggedInId);
		
		// MEMBERSHIP
		membership.setClient(client);
		membershipsService.saveMembership(membership, loggedInId);
		
		// PAYMENT
		payment.setClient(client);
		paymentService.savePayment(payment, loggedInId);
		
		return newClient;
	}
	
	@Override
	@Transactional
	public Client saveClient(Client client, String loggedInId) {
		try{
			int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
			
			client.setUpdatedBy(loggedInId);			
			client.setParentId(parentId);
			
			if(client.getClientId() == 0){
				client.setCreatedBy(loggedInId);
				// NEW CLIENT CASE - SET ENQUIRY FLAG
				Date today = new Date();
				client.setReminderOn(DateUtils.addDays(today, 5));
				client.setReminderAbout("Enquiry");
				client.setComment("  New Client Added.");
				client.addComment("     " + client.getComment(), loggedInId);	
			}else{
				//client.setComment("     Client Updated.");
			}
			
			commonHibernateDao.saveOrUpdate(client);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return client;
	}
	
	@Override
	@Transactional
	public Client saveReminder(Client client, String loggedInId) throws Exception {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		
		long count = CoreDateUtils.getDaysBetweenDates(client.getReminderOn(), new Date());
		if(count > AppConstants.MAX_DAYS_NEXT_REMINDER || count < 1){
			throw new AppException(null, "Next reminder should be within " + AppConstants.MAX_DAYS_NEXT_REMINDER + " days from today.");
		}
		
		client.setUpdatedBy(loggedInId);			
		client.setParentId(parentId);
		
		String comment = 	" Comment: " + client.getComment()
							+ "<br> Next Reminder after " + count + " days On " + CoreDateUtils.dateToStandardSting(client.getReminderOn()) + " about " + client.getReminderAbout();
		
		client.addComment(comment, loggedInId);		
		commonHibernateDao.saveOrUpdate(client);
		
		sendActivityThroughMail(client, loggedInId);
		
		return client;
	}

	@Override
	public List<Client> getAllReminders(String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Client where parentId = '" + parentId +"' and reminderOn <= current_date order by reminderOn ");
	}
	
	@Override
	public List<Client> getAllClients(String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Client where parentId = '" + parentId +"' order by name ");
	}

	@Override
	public Client updateClientAsPerMembership(Membership membership, String loggedInId, int parentId) {
		Client client = (Client) commonHibernateDao.getHibernateTemplate().find("from Client where parentId = '" + parentId +"' and clientId = " + membership.getClientId()).get(0);
		client.setUpdatedBy(membership.getUpdatedBy());
		client.setBalanceAmount(client.getBalanceAmount().add(membership.getEffectiveAmount()));
		client.setTotalAmount(client.getTotalAmount().add(membership.getEffectiveAmount()));
		if(client.getMembershipEndDate() == null || membership.getToDate().after(client.getMembershipEndDate())){
			client.setMembershipEndDate(membership.getToDate());
		}
		String comment = 
				    "  Membership Added : " + membership.toComment() 
				+ "<br>      Balance : " + client.getTotalAmount() + " - " + client.getPaidAmount() + " = " + client.getBalanceAmount();
		
		client.addComment(comment, loggedInId);		
		saveClient(client, loggedInId);
		return client;
	}

	@Override
	public Client updateClientAsPerPayment(Payment payment, String loggedInId, int parentId) throws Exception {
		Client client = (Client) commonHibernateDao.getHibernateTemplate().find("from Client where parentId = '" + parentId +"' and clientId = " + payment.getClientId()).get(0);
		client.setLastPaidOn(payment.getPaidOn());
		client.setBalanceAmount(client.getBalanceAmount().subtract(payment.getPaidAmount()));
		client.setPaidAmount(client.getPaidAmount().add(payment.getPaidAmount()));
		
		int signum = client.getBalanceAmount().signum();
		if(signum == 0){
			// If Balance is Zero
			Date today = new Date();
			client.setReminderOn(DateUtils.addDays(client.getMembershipEndDate(), -15));
			client.setReminderAbout("Renewal");
			
		}else{
			client.setReminderOn(DateUtils.addDays(payment.getPaidOn(), 20));
			client.setReminderAbout("Balance Due");
		}
		String comment = 
					    "  Payment Added : " + payment.toComment() 
					+ "<br>      Balance : " + client.getTotalAmount() + " - " + client.getPaidAmount() + " = " + client.getBalanceAmount();
		
		client.addComment(comment, loggedInId);
		saveClient(client, loggedInId);

		notifyPayment(client, payment, loggedInId);
		
		return client;
	}
	
	private void sendActivityThroughMail(Client client, String loggedInId) throws Exception {
		Parent parent = commonHibernateDao.getEntityById(Parent.class, client.getParentId());
		String today = CoreDateUtils.dateToStandardSting(new Date());
		String subject = "Activity As On " + today + " : " + client.getName();
		String text = "<B> Activity Log For Client : " + client.getName() + "</B> <br> <br>" + client.getActivity();
		String[] toAddresses = new String[]{client.getEmailId()};
		String[] ccAddresses = new String[]{parent.getEmailId(), loggedInId};
		
		AppMailSender.sendEmail(parent.getName(), parent.getEmailId(), subject, text, toAddresses, ccAddresses);
	}
	
	private void notifyPayment(Client client, Payment payment, String loggedInId) throws Exception {
		Parent parent = commonHibernateDao.getEntityById(Parent.class, client.getParentId());
		String today = CoreDateUtils.dateToStandardSting(new Date());
		String subject = "Invoice #" + payment.getPaymentId() +" - " + today + " : " + client.getName() + " - Received " + payment.getPaidAmount() + " on " + CoreDateUtils.dateToStandardSting(payment.getPaidOn()) + " through " + payment.getPymtMode();
		String text = "<B> Activity Log For Client : " + client.getName() + "</B> <br> <br>" + client.getActivity();
		String[] toAddresses = new String[]{client.getEmailId()};
		String[] ccAddresses = new String[]{parent.getEmailId(), loggedInId};
		
		AppMailSender.sendEmail(parent.getName(), parent.getEmailId(), subject, text, toAddresses, ccAddresses);
	}

	public ParentsService getParentsService() {
		return parentsService;
	}

	public void setParentsService(ParentsService parentsService) {
		this.parentsService = parentsService;
	}

	@Override
	public Invoice generateInvoice(int clientId, String loggedInId) throws Exception {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		Client client = (Client) commonHibernateDao.getHibernateTemplate().find("from Client where parentId = '" + parentId + "' and clientId = '" + clientId + "'").get(0);
		List<Membership> memberships = commonHibernateDao.getHibernateTemplate().find("from Membership where clientId = '" + clientId + "' order by fromDate " );
		List<Payment> payments = commonHibernateDao.getHibernateTemplate().find("from Payment where clientId = '" + clientId + "' order by paidOn " );
		Parent parent = (Parent) commonHibernateDao.getHibernateTemplate().find("from Parent where parentId = '" + parentId + "'" ).get(0);
		Invoice invoice = new Invoice(client, memberships, payments, parent);
		sendActivityThroughMail(client, loggedInId);
		return invoice;
		
	}
	
	public static void main(String[] args) {
		String str = "first line \n second line";
		System.out.println(str.replace("\n", "<br>"));
	}

}
