package edge.app.modules.payments;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.cashTransactions.CashTransactionsService;
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
	private CashTransactionsService cashTransactionsService;

	@Override
	@Transactional
	public Payment savePayment(Payment payment, String loggedInId) {
		try{
			int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
			
			payment.setCreatedBy(loggedInId);
			payment.setUpdatedBy(loggedInId);
			payment.setParentId(parentId);
			commonHibernateDao.save(payment);
			
			clientsService.updateClientAsPerPayment(payment, loggedInId, parentId);			
			cashTransactionsService.addCashTransactionAsPerPayment(payment, loggedInId);
			
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

	public CashTransactionsService getCashTransactionsService() {
		return cashTransactionsService;
	}

	public void setCashTransactionsService(CashTransactionsService cashTransactionsService) {
		this.cashTransactionsService = cashTransactionsService;
	}
}
