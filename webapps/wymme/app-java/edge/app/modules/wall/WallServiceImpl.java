package edge.app.modules.wall;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;

import edge.app.modules.common.AppConstants;
import edge.app.modules.profile.ProfileDetails;
import edge.core.modules.auth.SignUpEntity;
import edge.core.modules.common.CommonHibernateDao;

@Controller
public class WallServiceImpl implements WallService {

	private static final Logger logger = LoggerFactory.getLogger(WallServiceImpl.class);
	
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
		String removedProfilesClause = " and profileId Not In (" + getRemovedProfiles(profileId) + ")";
		
		return basicQuery + removedProfilesClause;
	}

	private String getRemovedProfiles(String profileId) {
		ProfileWallInfo profileWallInfo = commonHibernateDao.getEntityById(ProfileWallInfo.class, profileId);
		if(profileWallInfo == null){
			return "";
		}else{
			return profileWallInfo.getRemovedProfiles();
		}
	}

	@Override
	public void removeFromWall(String userName, String toRemove) {
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, userName);
		String profileId = signUpEntity.getProfileId();
		
		ProfileWallInfo profileWallInfo = commonHibernateDao.getEntityById(ProfileWallInfo.class, profileId);
		if(profileWallInfo == null){
			profileWallInfo = new ProfileWallInfo();
			profileWallInfo.setProfileId(profileId);
			profileWallInfo.setRemovedProfiles("'" + toRemove +"'");
		}else{
			String removedProfiles = profileWallInfo.getRemovedProfiles();
			if(!removedProfiles.contains(toRemove)){
				removedProfiles += ",'" + toRemove +"'";
			}
			profileWallInfo.setRemovedProfiles(removedProfiles);
		}
		commonHibernateDao.saveOrUpdate(profileWallInfo);
	}

}
