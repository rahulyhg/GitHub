package edge.app.modules.cashTransactions;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.common.AppConstants;
import edge.app.modules.payments.Payment;
import edge.core.exception.AppException;
import edge.core.modules.auth.SecurityRoles;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.parents.ParentData;
import edge.core.modules.parents.ParentsService;

@WebService
@Component
public class CashTransactionsServiceImpl implements CashTransactionsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private ParentsService parentsService;

	@Override
	@Transactional
	public CashTransaction addCashTransactionAsPerPayment(Payment payment, String loggedInId) {
		
		CashTransaction cashTransaction = null;
		
		if(payment.getPymtMode().equalsIgnoreCase("cash")){
			int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
			ParentData parentData = parentsService.getParentData(loggedInId);
			
			cashTransaction = new CashTransaction();
			cashTransaction.setCreatedBy(loggedInId);
			cashTransaction.setUpdatedBy(loggedInId);
			cashTransaction.setParentId(parentId);
			cashTransaction.setTransactionDate(new Date());			
			cashTransaction.setMode("Credit");
			
			cashTransaction.setTransactionType("Payment");
			cashTransaction.setAmount(payment.getPaidAmount());
			cashTransaction.setStatus(AppConstants.EntityStatus.SYSTEM.getStatus());
			cashTransaction.setDetails(payment.getClient().getName() +  " :: " + payment.getPaymentId());
			
			BigDecimal newBalance =  parentData.getDeskCashBalance().add(cashTransaction.getAmount());
			cashTransaction.setBalance(newBalance);
			parentData.setDeskCashBalance(newBalance);
			
			commonHibernateDao.save(cashTransaction);
			commonHibernateDao.save(parentData);
		}
		
		return cashTransaction;
	}
	

	@Override
	@Transactional
	public CashTransaction approveCashTransaction(int cashTransactionId, String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_ADMIN);
		CashTransaction cashTransaction = (CashTransaction) commonHibernateDao.getHibernateTemplate().find("from CashTransaction where parentId = '" + parentId +"' and cashTransactionId = " + cashTransactionId).get(0);
		cashTransaction.setStatus(AppConstants.EntityStatus.APPROVED.getStatus());
		commonHibernateDao.update(cashTransaction);
		return cashTransaction;
	}
	
	@Override
	@Transactional
	public CashTransaction saveCashTransaction(CashTransaction cashTransaction, String loggedInId) {
		try{
			int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
			ParentData parentData = parentsService.getParentData(loggedInId);
			
			cashTransaction.setCreatedBy(loggedInId);
			cashTransaction.setUpdatedBy(loggedInId);
			cashTransaction.setParentId(parentId);
			cashTransaction.setTransactionDate(new Date());
			cashTransaction.setMode("Debit");
			
			BigDecimal newBalance = parentData.getDeskCashBalance().subtract(cashTransaction.getAmount());
			cashTransaction.setBalance(newBalance);
			parentData.setDeskCashBalance(newBalance);
						
			commonHibernateDao.save(cashTransaction);
			commonHibernateDao.save(parentData);
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return cashTransaction;
	}

	@Override
	public List<CashTransaction> getCashTransactions(String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from CashTransaction where parentId = '" + parentId +"' order by updatedOn desc LIMIT 1 ");
	}

	public ParentsService getParentsService() {
		return parentsService;
	}

	public void setParentsService(ParentsService parentsService) {
		this.parentsService = parentsService;
	}
}
