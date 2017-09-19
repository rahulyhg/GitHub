package edge.app.modules.search;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import edge.app.modules.common.AppConstants;
import edge.app.modules.profile.ProfileDetails;
import edge.app.modules.profileWallInfo.ProfileWallInfo;
import edge.app.modules.profileWallInfo.ProfileWallInfoService;
import edge.core.config.CoreConstants;
import edge.core.modules.auth.SignUpEntity;
import edge.core.modules.common.CommonHibernateDao;

@Component
public class SearchServiceImpl implements SearchService {

	private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);
	
	@Autowired
	private ProfileWallInfoService profileWallInfoService;
	
	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	/* (non-Javadoc)
	 * @see edge.app.modules.search.SearchService#searchById(java.lang.String)
	 */
	@Override
	public ProfileDetails searchById(String profileId){			
		
		return commonHibernateDao.getEntityById(ProfileDetails.class, profileId.toUpperCase());

	}
	
	@Override
	public List<ProfileDetails> loadRemovedProfiles(String userName){
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, userName);
		String profileId = signUpEntity.getProfileId();
		
		HibernateTemplate hibernateTemplate = commonHibernateDao.getHibernateTemplate();
		hibernateTemplate.setMaxResults(AppConstants.MAX_REMOVED_SIZE);
		
		return hibernateTemplate.find(getRemovedProfilesQuery(profileId));
	}

	private String getRemovedProfilesQuery(String profileId) {
		String basicQuery = " from ProfileDetails where 1=1 ";
		int length = (CoreConstants.PROFILE_ID_SIZE + 3) * AppConstants.MAX_REMOVED_SIZE + 2;
		String removedProfilesClause = " and profileId In (" + profileWallInfoService.getRemovedProfiles(profileId, length) + ")";
		
		return basicQuery + removedProfilesClause;
	}

	@Override
	public void undoRemoveFromWall(String userName, String toAdd) {
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, userName);
		String profileId = signUpEntity.getProfileId();
		
		profileWallInfoService.undoRemoveFromWall(profileId, toAdd);
	}

}
