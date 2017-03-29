package edge.app.modules.employees;

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
public class EmployeesServiceImpl implements EmployeesService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private GymsService gymsService;

	@Override
	@Transactional
	public Employee saveEmployee(Employee employee, String loggedInId) {
		try{
			int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_ADMIN);
			
			employee.setCreatedBy(loggedInId);
			employee.setUpdatedBy(loggedInId);
			employee.setSystemId(systemId);
			
			commonHibernateDao.saveOrUpdate(employee);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return employee;
	}

	@Override
	public List<Employee> getActiveEmployees(String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Employee where systemId = '" + systemId +"' and status = 'Active' order by name ");
	}

	@Override
	public List<Employee> getAllEmployees(String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Employee where systemId = '" + systemId +"' order by name ");
	}

	public GymsService getGymsService() {
		return gymsService;
	}

	public void setGymsService(GymsService gymsService) {
		this.gymsService = gymsService;
	}
}
