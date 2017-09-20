package edge.app.modules.profileConnection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.profile.ProfileDetails;
import edge.app.modules.profile.SecureProfileDetails;
import edge.app.modules.profileWallInfo.ProfileWallInfoService;
import edge.app.modules.search.ConnectionAction;
import edge.core.exception.AppException;
import edge.core.modules.auth.SignUpEntity;
import edge.core.modules.common.CommonHibernateDao;

@Component
public class ProfileConnectionServiceImpl implements ProfileConnectionService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	@Autowired
	private ProfileWallInfoService profileWallInfoService;
	
	@Override
	public void sendConnectionRequest(String userName, String profileTo) {
		try{
			SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, userName);
			String profileFrom = signUpEntity.getProfileId();
			 
			Date today = new Date();
			
			ProfileConnection profileConnection = new ProfileConnection();
			profileConnection.setRequestedOn(today);
			profileConnection.setProfileFrom(profileFrom);
			profileConnection.setProfileTo(profileTo);
			profileConnection.setConnectionId(profileFrom, profileTo);
			profileConnection.setConnectionStatus(ConnectionStatusEnum.Requested);
	
			commonHibernateDao.save(profileConnection);
			
			profileWallInfoService.addToReadProfiles(profileFrom, profileTo);
			
		}catch (DataIntegrityViolationException deve){
			throw new AppException(deve, "There is already a connection request with this combination.");
		}
		
	}

	@Override
	@Transactional
	public List<ProfileDetails> searchProfiles(String userName, String searchType) {

		List<ProfileDetails> profileDetailsList = new ArrayList<ProfileDetails>();
		
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, userName);
		String IProfileId = signUpEntity.getProfileId();
		
		SearchTypeEnum searchTypeEnum = SearchTypeEnum.valueOf(searchType);
		String query = "";
		
		switch(searchTypeEnum){
		case IRequested:
			/*query = " from ProfileDetails where profileId In ( " +
					" select profileTo from ProfileConnection where profileFrom = '" + profileFrom + "' and connectionStatus = '" + ConnectionStatusEnum.REQUESTED + "'" +
					" ) ";*/
			query = " Select pd, pc " +
					" from ProfileDetails pd, ProfileConnection pc" +
					" where  pc.profileFrom = '" + IProfileId + "'" +
					"   and  pc.profileTo = pd.profileId " +
					"   and  pc.connectionStatus = '" + ConnectionStatusEnum.Requested + "'" +
					" order by pc.requestedOn desc ";
			break;
			
		case TheyRequested:
			query = " Select pd, pc " +
					" from ProfileDetails pd, ProfileConnection pc" +
					" where  pc.profileTo = '" + IProfileId + "'" +
					"   and  pc.profileFrom = pd.profileId " +
					"   and  pc.connectionStatus = '" + ConnectionStatusEnum.Requested + "'" +
					" order by pc.requestedOn desc ";
			break;
			
		case IRejected:
			query = " Select pd, pc " +
					" from ProfileDetails pd, ProfileConnection pc" +
					" where  pc.profileTo = '" + IProfileId + "'" +
					"   and  pc.profileFrom = pd.profileId " +
					"   and  pc.connectionStatus = '" + ConnectionStatusEnum.Rejected + "'" +
					" order by pc.actionedOn desc ";
			break;
			
		case TheyRejected:
			query = " Select pd, pc " +
					" from ProfileDetails pd, ProfileConnection pc" +
					" where  pc.profileFrom = '" + IProfileId + "'" +
					"   and  pc.profileTo = pd.profileId " +
					"   and  pc.connectionStatus = '" + ConnectionStatusEnum.Rejected + "'" +
					" order by pc.actionedOn desc ";
			break;
			
		case IAccepted:
			query = " Select pd, pc " +
					" from ProfileDetails pd, ProfileConnection pc" +
					" where  pc.profileTo = '" + IProfileId + "'" +
					"   and  pc.profileFrom = pd.profileId " +
					"   and  pc.connectionStatus = '" + ConnectionStatusEnum.Accepted + "'" +
					" order by pc.actionedOn desc ";
			break;
			
		case TheyAccepted:
			query = " Select pd, pc " +
					" from ProfileDetails pd, ProfileConnection pc" +
					" where  pc.profileFrom = '" + IProfileId + "'" +
					"   and  pc.profileTo = pd.profileId " +
					"   and  pc.connectionStatus = '" + ConnectionStatusEnum.Accepted + "'" +
					" order by pc.actionedOn desc ";
			break;
			
		}
		
		List<Object[]> searchedProfiles = commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query).list();
		
		for(Object[] obj : searchedProfiles){
			ProfileDetails profileDetails = (ProfileDetails) obj[0];
			profileDetails.setProfileConnection( (ProfileConnection) obj[1]);
			profileDetailsList.add(profileDetails);
		}
		
		return profileDetailsList;
	}

	@Override
	public void actionRequest(String userName, ConnectionAction connectionAction) {
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, userName);
		String IProfileId = signUpEntity.getProfileId();
		
		@SuppressWarnings("unchecked")
		List<ProfileConnection> searchedConnections = commonHibernateDao.getHibernateTemplate().find(
				" from ProfileConnection where profileTo = '" + IProfileId + "' and profileFrom = '" + connectionAction.getProfileId() + "'" +
				" and  ( 	connectionStatus = '" +  ConnectionStatusEnum.Requested.name() + "' " +
				"        OR connectionStatus = '" +  ConnectionStatusEnum.Rejected.name() + "'" +
				"       ) "
				);
		
		if(searchedConnections!= null && searchedConnections.size() == 1){
			Date today = new Date();
			ProfileConnection connection = searchedConnections.get(0);
			connection.setActionedOn(today);
			connection.setConnectionStatus(connectionAction.getConnectionStatus());
			commonHibernateDao.update(connection);
		}else{
			throw new AppException(null, "There is no such connection!");
		}
		
	}

	@Override
	public ProfileDetails showContactDetails(String userName, String profileId) {
		ProfileDetails profileDetails = null;
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, userName);
		String IProfileId = signUpEntity.getProfileId();
		
		if(checkIfConnectionStatus(IProfileId, profileId, ConnectionStatusEnum.Accepted)){
			profileDetails = commonHibernateDao.getEntityById(ProfileDetails.class, profileId);
			profileDetails.setSecure(commonHibernateDao.getEntityById(SecureProfileDetails.class, profileId));
		}else{
			throw new AppException(null, "Only accepted connections can see each other's contact details. Please send the connection request first.");
		}
		profileDetails.setSignUpEntity(null);
		return profileDetails;
	}

	@Override
	public boolean checkIfConnectionStatus(String profile1, String profile2, ConnectionStatusEnum status) {
		String connectionId = ProfileConnection.getConnectionId(profile1, profile2);
		ProfileConnection profileConnection = commonHibernateDao.getEntityById(ProfileConnection.class, connectionId);
		return profileConnection!= null && profileConnection.getConnectionStatus() == status;
	}

}
