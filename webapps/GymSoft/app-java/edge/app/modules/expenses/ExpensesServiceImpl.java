package edge.app.modules.expenses;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.core.exception.AppException;
import edge.core.modules.auth.SecurityRoles;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.parents.ParentsService;

@WebService
@Component
public class ExpensesServiceImpl implements ExpensesService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private ParentsService parentsService;

	@Override
	@Transactional
	public Expense saveExpense(Expense expense, String loggedInId) {
		try{
			int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_ADMIN);
			
			expense.setCreatedBy(loggedInId);
			expense.setUpdatedBy(loggedInId);
			expense.setParentId(parentId);
			
			commonHibernateDao.save(expense);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return expense;
	}

	@Override
	public List<Expense> getAllExpenses(String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Expense where parentId = '" + parentId +"'");
	}

	public ParentsService getParentsService() {
		return parentsService;
	}

	public void setParentsService(ParentsService parentsService) {
		this.parentsService = parentsService;
	}
}
