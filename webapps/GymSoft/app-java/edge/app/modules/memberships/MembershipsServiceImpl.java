package edge.app.modules.memberships;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.clients.ClientsService;
import edge.core.exception.AppException;
import edge.core.modules.auth.SecurityRoles;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.parents.ParentsService;

@WebService
@Component
public class MembershipsServiceImpl implements MembershipsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private ClientsService clientsService;

	@Autowired
	private ParentsService parentsService;

	@Override
	@Transactional
	public Membership saveMembership(Membership membership, String loggedInId) {
		try{
			int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
			if(membership.getSelectedPackage().getMaxDiscount().compareTo(membership.getDiscountAmount()) == -1){
				throw new AppException(null, "Discount offered should be less than " + membership.getSelectedPackage().getMaxDiscount());
			}
			membership.setParentId(parentId);
			membership.setCreatedBy(loggedInId);
			membership.setUpdatedBy(loggedInId);
			commonHibernateDao.save(membership);
			getClientsService().updateClientAsPerMembership(membership, loggedInId, parentId);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return membership;
	}

	@Override
	public List<Membership> getAllMemberships(String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Membership where parentId = '" + parentId +"'");
	}

	public ClientsService getClientsService() {
		return clientsService;
	}

	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}

	public ParentsService getParentsService() {
		return parentsService;
	}

	public void setParentsService(ParentsService parentsService) {
		this.parentsService = parentsService;
	}

}
