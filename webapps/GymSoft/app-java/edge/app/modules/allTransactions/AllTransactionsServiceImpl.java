package edge.app.modules.allTransactions;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.clients.Client;
import edge.app.modules.common.AppConstants;
import edge.app.modules.payments.Payment;
import edge.appCore.modules.mailSender.GymSoftMailSender;
import edge.core.exception.AppException;
import edge.core.modules.auth.AuthService;
import edge.core.modules.auth.SecurityRoles;
import edge.core.modules.auth.UserViewModel;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.parents.Parent;
import edge.core.modules.parents.ParentData;
import edge.core.modules.parents.ParentsService;

@WebService
@Component
public class AllTransactionsServiceImpl implements AllTransactionsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private ParentsService parentsService;

	@Autowired
	private AuthService authService;

	@Override
	@Transactional
	public AllTransaction addAllTransactionAsPerPayment(Payment payment, String loggedInId) throws Exception {
		
		AllTransaction allTransaction = null;
		
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		ParentData parentData = parentsService.getParentData(loggedInId);
		
		allTransaction = new AllTransaction();
		allTransaction.setCreatedBy(loggedInId);
		allTransaction.setUpdatedBy(loggedInId);
		allTransaction.setParentId(parentId);
		allTransaction.setTransactionDate(new Date());			
		allTransaction.setMode("Credit");
		
		allTransaction.setTransactionType("Payment-" + payment.getPymtMode());
		allTransaction.setAmount(payment.getPaidAmount());
		allTransaction.setStatus(AppConstants.EntityStatus.SYSTEM.getStatus());
		allTransaction.setDetails(payment.getClient().getName() +  " :: " + payment.getPaymentId());
		
		BigDecimal currDeskCashBalance = parentData.getDeskCashBalance();
		BigDecimal currAllBalance = parentData.getAllBalance();		
		BigDecimal transactionAmt = allTransaction.getAmount();			
		BigDecimal newBalance =  null;
		String subject = null;
		
		if(payment.getPymtMode().equalsIgnoreCase("Cash")){
			
			// All Balances Will Remain Unaffected
			parentData.setAllBalance(currAllBalance);
			allTransaction.setAllBalance(currAllBalance);
			
			// Desk Cash Balance will Increase
			newBalance =  currDeskCashBalance.add(transactionAmt);
			parentData.setDeskCashBalance(newBalance);
			allTransaction.setDeskCashBalance(newBalance);
			
			allTransaction.setDeskTransactionFlag("Yes");
			allTransaction.setMode("Desk-Credit");
			
			subject = " Payment (" + payment.getPymtMode() + ") Received - New Desk Balance - " + newBalance + " (" + currDeskCashBalance  + " + " + transactionAmt + ") ";
			
		}else{	
			// Desk Balance Will remain Unaffected
			parentData.setDeskCashBalance(currDeskCashBalance);
			allTransaction.setDeskCashBalance(currDeskCashBalance);
			
			// All balance will Increase
			newBalance =  currAllBalance.add(transactionAmt); 
			parentData.setAllBalance(newBalance);
			allTransaction.setAllBalance(newBalance);
			
			allTransaction.setDeskTransactionFlag("No");
			allTransaction.setMode("Bank-Credit");
			
			subject = " Payment (" + payment.getPymtMode() + ") Received - New Bank Balance - " + newBalance + " (" + currAllBalance  + " + " + transactionAmt + ") ";
		}
		
		commonHibernateDao.save(allTransaction);
		commonHibernateDao.save(parentData);
		
		Client client = (Client) commonHibernateDao.getHibernateTemplate().find("from Client where parentId = '" + parentId +"' and clientId = " + payment.getClientId()).get(0);
		Parent parent = commonHibernateDao.getEntityById(Parent.class, client.getParentId());
		
		GymSoftMailSender.notifyGym(subject, parent, client, loggedInId);
		
		return allTransaction;
	}
	

	@Override
	@Transactional
	public AllTransaction approveAllTransaction(int allTransactionId, String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_ADMIN);
		AllTransaction allTransaction = (AllTransaction) commonHibernateDao.getHibernateTemplate().find("from AllTransaction where parentId = '" + parentId +"' and allTransactionId = " + allTransactionId).get(0);
		allTransaction.setStatus(AppConstants.EntityStatus.APPROVED.getStatus());
		commonHibernateDao.update(allTransaction);
		return allTransaction;
	}
	
	@Override
	@Transactional
	public AllTransaction saveAllTransaction(AllTransaction allTransaction, String loggedInId) {
		try{
			int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
			Parent parent = commonHibernateDao.getEntityById(Parent.class, parentId);
			ParentData parentData = parentsService.getParentData(loggedInId);
			
			allTransaction.setCreatedBy(loggedInId);
			allTransaction.setUpdatedBy(loggedInId);
			allTransaction.setParentId(parentId);
			allTransaction.setTransactionDate(new Date());
			
			allTransaction.setStatus(AppConstants.EntityStatus.DRAFT.getStatus());
			
			BigDecimal currDeskCashBalance = parentData.getDeskCashBalance();
			BigDecimal currAllBalance = parentData.getAllBalance();
			BigDecimal transactionAmt = allTransaction.getAmount();
			String subject = null;
			
			if(allTransaction.getTransactionType().equalsIgnoreCase("Bank Deposit")){
				
				// Desk Cash Balance will Decrease
				BigDecimal newDeskCashBalance = currDeskCashBalance.subtract(transactionAmt);
				parentData.setDeskCashBalance(newDeskCashBalance);
				allTransaction.setDeskCashBalance(newDeskCashBalance);
				
				// All Balance Will Increase
				BigDecimal newAllBalance = currAllBalance.add(transactionAmt);
				parentData.setAllBalance(newAllBalance);
				allTransaction.setAllBalance(newAllBalance);
				
				allTransaction.setDeskTransactionFlag("Yes");
				allTransaction.setMode("Desk-to-Bank");
				
				subject = "Bank Deposit - Desk Balance - " + newDeskCashBalance + " (" + currDeskCashBalance  + " - " + transactionAmt + ")";
				subject += " - Bank Balance " + newAllBalance;
				
			}else if(allTransaction.getTransactionType().equalsIgnoreCase("Desk Expense")){
				
				// Desk Cash Balance will Decrease
				BigDecimal newDeskCashBalance = currDeskCashBalance.subtract(transactionAmt);
				parentData.setDeskCashBalance(newDeskCashBalance);
				allTransaction.setDeskCashBalance(newDeskCashBalance);	
				
				// All Balance will remain unaffected
				allTransaction.setAllBalance(currAllBalance);
				parentData.setAllBalance(currAllBalance);
				
				allTransaction.setDeskTransactionFlag("Yes");
				allTransaction.setMode("Desk-Debit");
				
				subject = "Desk Expense - New Balance - " + newDeskCashBalance + " (" + currDeskCashBalance  + " - " + transactionAmt + ")";
				
			}else if(allTransaction.getTransactionType().equalsIgnoreCase("Bank Expense")){
				
				// Desk  Cash will remain unaffected
				allTransaction.setDeskCashBalance(currDeskCashBalance);
				parentData.setDeskCashBalance(currDeskCashBalance);
				
				// Bank Balance will Decrease
				BigDecimal newAllBalance = currAllBalance.subtract(transactionAmt);
				parentData.setAllBalance(newAllBalance);
				allTransaction.setAllBalance(newAllBalance);	
				
				allTransaction.setDeskTransactionFlag("No");
				allTransaction.setMode("Bank-Debit");
				
				subject = "Bank Expense - New Balance - " + newAllBalance + " (" + currAllBalance  + " - " + transactionAmt + ")";
				
			}
			
			commonHibernateDao.save(allTransaction);
			commonHibernateDao.save(parentData);
			
			String text = 	"" + subject +
					        "<br> " + allTransaction.toComment();
			
			GymSoftMailSender.notifyGym(subject, parent, text, loggedInId);
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return allTransaction;
	}

	@Override
	public List<AllTransaction> getAllTransactions(String loggedInId) {
		
		String query = "";
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		String role = parentsService.getRole(loggedInId);
		
		if(role.equals(SecurityRoles.PARENT_ADMIN.getRoleDescription())){
			query = "from AllTransaction where parentId = '" + parentId +"' order by updatedOn desc";
		}else{
			query = "from AllTransaction where parentId = '" + parentId +"' and deskTransactionFlag = 'Yes' order by updatedOn desc";
		}
		return commonHibernateDao.getHibernateTemplate().find(query);
	}

	public ParentsService getParentsService() {
		return parentsService;
	}

	public void setParentsService(ParentsService parentsService) {
		this.parentsService = parentsService;
	}
}
