package edge.app.modules.profile;

import java.security.Principal;
import java.util.Date;
import java.text.SimpleDateFormat;
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

import edge.core.config.CoreConstants;
import edge.core.modules.auth.SignUpEntity;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.common.EdgeResponse;

@Controller
public class ProfileController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	@RequestMapping(value={"/secured/profile/openMyProfile"})
	public EdgeResponse<ProfileDetails> openMyProfile(
			Principal principal
			){			
		String emailId = principal.getName();
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, emailId);
		
		ProfileDetails profileDetails = commonHibernateDao.getEntityById(ProfileDetails.class, signUpEntity.getProfileId());
		profileDetails.setSecure(commonHibernateDao.getEntityById(SecureProfileDetails.class, signUpEntity.getProfileId()));
		profileDetails.setSignUpEntity(signUpEntity);
		
		return EdgeResponse.createDataResponse(profileDetails, null);
	}
	
	@RequestMapping(value={"/secured/profile/updateMyProfile"})
	public EdgeResponse<ProfileDetails> updateMyProfile(
			Principal principal, @RequestBody ProfileDetails profileDetails
			){			
		String emailId = principal.getName();
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, emailId);
		
		String profileId = signUpEntity.getProfileId();
		profileDetails.setProfileId(profileId);
		
		List<String> errors = profileDetails.validate();
		if(errors != null && errors.size() != 0){
			return EdgeResponse.createErrorResponse(profileDetails, "There were below errors while processing your request", "Please try after some time.", errors);
		}else{
			commonHibernateDao.update(profileDetails);
			commonHibernateDao.update(profileDetails.getSecure());
			return EdgeResponse.createDataResponse(profileDetails, "Your profile has heen Successfully Updated!");			
		}

	}
}
