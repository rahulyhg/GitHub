package edge.app.modules.profileConnection;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

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
			profileConnection.setConnectionStatus(ConnectionStatusEnum.REQUESTED);
	
			commonHibernateDao.save(profileConnection);
			
			profileWallInfoService.addToReadProfiles(profileFrom, profileTo);
			
		}catch (DataIntegrityViolationException deve){
			throw new AppException(deve, "There is already a connection request with this combination.");
		}
		
	}

}
