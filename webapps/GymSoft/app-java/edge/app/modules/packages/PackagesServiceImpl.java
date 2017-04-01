package edge.app.modules.packages;

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
public class PackagesServiceImpl implements PackagesService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private ParentsService parentsService;

	@Override
	@Transactional
	public Package savePackage(Package packagei, String loggedInId) {
		try{
			int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_ADMIN);
			
			packagei.setCreatedBy(loggedInId);
			packagei.setUpdatedBy(loggedInId);
			packagei.setParentId(parentId);
			commonHibernateDao.saveOrUpdate(packagei);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return packagei;
	}

	@Override
	public List<Package> getActivePackages(String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Package where parentId = '" + parentId +"' and status = 'Active' order by months desc, name ");
	}
	
	@Override
	public List<Package> getAllPackages(String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Package where parentId = '" + parentId +"' order by months desc, name ");
	}

	public ParentsService getParentsService() {
		return parentsService;
	}

	public void setParentsService(ParentsService parentsService) {
		this.parentsService = parentsService;
	}
}
