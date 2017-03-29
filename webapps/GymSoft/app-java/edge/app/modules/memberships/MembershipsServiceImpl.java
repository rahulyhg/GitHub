package edge.app.modules.memberships;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.clients.ClientsService;
import edge.app.modules.gyms.GymsService;
import edge.appCore.modules.auth.SecurityRoles;
import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;

@WebService
@Component
public class MembershipsServiceImpl implements MembershipsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private ClientsService clientsService;

	@Autowired
	private GymsService gymsService;

	@Override
	@Transactional
	public Membership saveMembership(Membership membership, String loggedInId) {
		try{
			int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_OPERATOR);
			
			membership.setSystemId(systemId);
			membership.setCreatedBy(loggedInId);
			membership.setUpdatedBy(loggedInId);
			commonHibernateDao.save(membership);
			getClientsService().updateClientAsPerMembership(membership, loggedInId, systemId);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return membership;
	}

	@Override
	public List<Membership> getAllMemberships(String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Membership where systemId = '" + systemId +"'");
	}

	public ClientsService getClientsService() {
		return clientsService;
	}

	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}

	public GymsService getGymsService() {
		return gymsService;
	}

	public void setGymsService(GymsService gymsService) {
		this.gymsService = gymsService;
	}

}
