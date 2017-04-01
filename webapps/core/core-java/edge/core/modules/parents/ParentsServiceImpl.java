package edge.core.modules.parents;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.core.exception.AppException;
import edge.core.modules.auth.AuthService;
import edge.core.modules.auth.SecurityRoles;
import edge.core.modules.common.CommonHibernateDao;
	
@WebService
@Component
public class ParentsServiceImpl implements ParentsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public Parent addParent(Parent parent) {
		try{
			parent.setFromDate(DateUtils.addDays(new Date(), -1));
			
			Calendar cal = Calendar.getInstance(); 
			cal.add(Calendar.MONTH, 3);
			parent.setToDate(cal.getTime());
			parent.setAdmins(parent.getAdmins() + "," + parent.getEmailId());
			
			authService.signUpUsers(parent.getOperators().split(","));
			authService.signUpUsers(parent.getAdmins().split(","));
			
			commonHibernateDao.save(parent);
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return parent;
	}
	
	@Override
	public Parent updateParent(Parent parent, String loggedInId) {
		try{
			parent.setUpdatedBy(loggedInId);
			
			authService.signUpUsers(parent.getOperators().split(","));
			authService.signUpUsers(parent.getAdmins().split(","));
			
			commonHibernateDao.update(parent);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return parent;
	}

	@Override
	public List<Parent> getAllParents() {
		return commonHibernateDao.getHibernateTemplate().loadAll(Parent.class);
	}

	@Override
	public int getParentId(String loggedInId, SecurityRoles role) {
		String query = "";
		switch(role){
			case PARENT_OPERATOR: query = "from Parent where operators like '%," + loggedInId + ",%' OR admins like '%," + loggedInId + ",%'"; break; 
			case PARENT_ADMIN: query = "from Parent where admins like '%," + loggedInId + ",%'"; break;
		}
		query += " and fromDate <= current_date and toDate >= current_date ";
		List<Parent> parents = commonHibernateDao.getHibernateTemplate().find(query);
		if(parents == null || parents.size() != 1){
			throw new AppException(null, "Unauthorized Access");
		}
		
		return parents.get(0).getParentId();
	}

	@Override
	@Transactional
	public int executeQuery(String query) {
		return commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query).executeUpdate();
	}

	@Override
	public List showAll(String entity) {
		return commonHibernateDao.getHibernateTemplate().find(" from " + entity);
	}
}
