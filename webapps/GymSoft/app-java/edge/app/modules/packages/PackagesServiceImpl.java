package edge.app.modules.packages;

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
public class PackagesServiceImpl implements PackagesService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private GymsService gymsService;

	@Override
	@Transactional
	public Package savePackage(Package packagei, String loggedInId) {
		try{
			int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_ADMIN);
			
			packagei.setCreatedBy(loggedInId);
			packagei.setUpdatedBy(loggedInId);
			packagei.setSystemId(systemId);
			commonHibernateDao.saveOrUpdate(packagei);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return packagei;
	}

	@Override
	public List<Package> getActivePackages(String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Package where systemId = '" + systemId +"' and status = 'Active' order by months desc, name ");
	}
	
	@Override
	public List<Package> getAllPackages(String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Package where systemId = '" + systemId +"' order by months desc, name ");
	}

	public GymsService getGymsService() {
		return gymsService;
	}

	public void setGymsService(GymsService gymsService) {
		this.gymsService = gymsService;
	}
}
