package edge.app.modules.wall;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import edge.app.modules.common.AppConstants;
import edge.app.modules.profile.ProfileDetails;
import edge.app.modules.profileWallInfo.ProfileWallInfoService;
import edge.core.modules.auth.SignUpEntity;
import edge.core.modules.common.CommonHibernateDao;

@Component
public class WallServiceImpl implements WallService {

	private static final Logger logger = LoggerFactory.getLogger(WallServiceImpl.class);
	
	@Autowired
	private ProfileWallInfoService profileWallInfoService;
	
	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	/* (non-Javadoc)
	 * @see edge.app.modules.wall.WallService#loadWallProfiles()
	 */
	@Override
	public List<ProfileDetails> loadWallProfiles(String userName){
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, userName);
		String profileId = signUpEntity.getProfileId();
		
		HibernateTemplate hibernateTemplate = commonHibernateDao.getHibernateTemplate();
		hibernateTemplate.setMaxResults(AppConstants.MAX_WALL_SIZE);
		
		return hibernateTemplate.find(getWallQuery(profileId));
	}

	private String getWallQuery(String profileId) {
		String basicQuery = " from ProfileDetails where 1=1 ";
		String removedProfilesClause = " and profileId Not In (" + profileWallInfoService.getRemovedProfiles(profileId, null) + ")";
		String readProfilesClause = " and profileId Not In (" + profileWallInfoService.getReadProfiles(profileId) + ")";
		
		return basicQuery + removedProfilesClause + readProfilesClause;
	}

	@Override
	public void removeFromWall(String userName, String toRemove) {
		
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, userName);
		String profileId = signUpEntity.getProfileId();
		profileWallInfoService.removeFromWall(profileId, toRemove);
		
	}

}
