package edge.app.modules.expenses;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.gyms.GymsService;
import edge.appCore.modules.auth.SecurityRoles;
import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;

@WebService
@Component
public class ExpensesServiceImpl implements ExpensesService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private GymsService gymsService;

	@Override
	@Transactional
	public Expense saveExpense(Expense expense, String loggedInId) {
		try{
			int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_ADMIN);
			
			expense.setCreatedBy(loggedInId);
			expense.setUpdatedBy(loggedInId);
			expense.setSystemId(systemId);
			
			commonHibernateDao.save(expense);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return expense;
	}

	@Override
	public List<Expense> getAllExpenses(String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Expense where systemId = '" + systemId +"'");
	}

	public GymsService getGymsService() {
		return gymsService;
	}

	public void setGymsService(GymsService gymsService) {
		this.gymsService = gymsService;
	}
}
