package edge.app.modules.payments;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.allTransactions.AllTransactionsService;
import edge.app.modules.clients.ClientsService;
import edge.app.modules.common.AppConstants;
import edge.core.exception.AppException;
import edge.core.modules.auth.SecurityRoles;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.parents.ParentsService;

@WebService
@Component
public class PaymentsServiceImpl implements PaymentsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private ClientsService clientsService;

	@Autowired
	private ParentsService parentsService;
	
	@Autowired
	private AllTransactionsService allTransactionsService;

	@Override
	@Transactional
	public Payment savePayment(Payment payment, String loggedInId) {
		try{
			int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
			
			payment.setCreatedBy(loggedInId);
			payment.setUpdatedBy(loggedInId);
			payment.setParentId(parentId);
			if(payment.getPymtMode().equalsIgnoreCase("cash")){
				payment.setStatus(AppConstants.EntityStatus.SYSTEM.getStatus());
			}
			
			commonHibernateDao.save(payment);
			
			clientsService.updateClientAsPerPayment(payment, loggedInId, parentId);			
			allTransactionsService.addTransactionAsPerPayment(payment, loggedInId);
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return payment;
	}

	@Override
	public List<Payment> getAllPayments(String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Payment where parentId = '" + parentId +"' order by updatedOn desc ");
	}

	public ParentsService getParentsService() {
		return parentsService;
	}

	public void setParentsService(ParentsService parentsService) {
		this.parentsService = parentsService;
	}

	@Override
	public Payment approvePayment(int paymentId, String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_ADMIN);
		Payment payment = (Payment) commonHibernateDao.getHibernateTemplate().find("from Payment where parentId = '" + parentId +"' and paymentId = " + paymentId).get(0);
		payment.setStatus(AppConstants.EntityStatus.APPROVED.getStatus());
		commonHibernateDao.update(payment);
		return payment;
	}

	@Override
	public Payment rejectPayment(int paymentId, String rejectReason, String loggedInId) throws Exception {
		
		if(rejectReason == null || rejectReason.trim().length() == 0){
			throw new AppException(null, "Please enter valid Reject Reason.");
		}
		
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_ADMIN);
		Payment payment = (Payment) commonHibernateDao.getHibernateTemplate().find("from Payment where parentId = '" + parentId +"' and paymentId = " + paymentId).get(0);
		payment.setRejectReason(rejectReason);		
		payment.setStatus(AppConstants.EntityStatus.REJECTED.getStatus());
		commonHibernateDao.update(payment);
		
		clientsService.updateClientAsPerRejectedPayment(payment, loggedInId, parentId);
		allTransactionsService.addTransactionAsPerRejectedPayment(payment, loggedInId);
		
		return payment;
	}

	public AllTransactionsService getAllTransactionsService() {
		return allTransactionsService;
	}

	public void setAllTransactionsService(AllTransactionsService allTransactionsService) {
		this.allTransactionsService = allTransactionsService;
	}
}
