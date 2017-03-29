package edge.app.modules.search;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edge.app.modules.profile.ProfileDetails;
import edge.core.config.CoreConstants;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.common.EdgeResponse;

@Controller
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	@RequestMapping(value={"/unsecured/searchById"})
	public EdgeResponse<List<ProfileDetails>> searchById(
				@RequestBody String profileId
			){			
		
		List<ProfileDetails> searchedProfiles = new ArrayList<ProfileDetails>();
		
		ProfileDetails profileDetails = commonHibernateDao.getEntityById(ProfileDetails.class, profileId.toUpperCase());

		if(profileDetails == null){
			return EdgeResponse.createErrorResponse(null,"There is no such profile!", null, null);
		}else{
			searchedProfiles.add(profileDetails);
			return EdgeResponse.createDataResponse(searchedProfiles, "");			
		}

	}
}
