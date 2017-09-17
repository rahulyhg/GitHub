package edge.app.modules.wall;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;

import edge.app.modules.common.AppConstants;
import edge.app.modules.profile.ProfileDetails;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.common.EdgeResponse;

@Controller
public class WallServiceImpl implements WallService {

	private static final Logger logger = LoggerFactory.getLogger(WallServiceImpl.class);
	
	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	/* (non-Javadoc)
	 * @see edge.app.modules.wall.WallService#loadWallProfiles()
	 */
	@Override
	public EdgeResponse<List<ProfileDetails>> loadWallProfiles(String userName){			
		HibernateTemplate hibernateTemplate = commonHibernateDao.getHibernateTemplate();
		hibernateTemplate.setMaxResults(AppConstants.MAX_WALL_SIZE);
		
		List<ProfileDetails> searchedProfiles = hibernateTemplate.find(getWallQuery(userName));

		if(searchedProfiles == null || searchedProfiles.size() == 0){
			return EdgeResponse.createErrorResponse(null,"There is no such profile!", null, null);
		}else{
			return EdgeResponse.createDataResponse(searchedProfiles, "");			
		}

	}

	private String getWallQuery(String userName) {
		return " from ProfileDetails ";
	}

}
