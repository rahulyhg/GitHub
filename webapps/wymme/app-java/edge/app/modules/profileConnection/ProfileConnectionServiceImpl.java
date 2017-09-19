package edge.app.modules.profileConnection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.profile.ProfileDetails;
import edge.app.modules.profileWallInfo.ProfileWallInfoService;
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
			/*query = " from ProfileDetails where profileId In ( " +
					" select profileTo from ProfileConnection where profileFrom = '" + profileFrom + "' and connectionStatus = '" + ConnectionStatusEnum.REQUESTED + "'" +
					" ) ";*/
			query = " Select pd, pc " +
					" from ProfileDetails pd, ProfileConnection pc" +
					" where  pc.profileTo = '" + IProfileId + "'" +
					"   and  pc.profileFrom = pd.profileId " +
					"   and  pc.connectionStatus = '" + ConnectionStatusEnum.Requested + "'" +
					" order by pc.requestedOn desc ";
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

}
