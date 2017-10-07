package edge.app.modules.register;

import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.profile.ProfileDetails;
import edge.appCore.modules.mailSender.EventDetailsEnum;
import edge.core.exception.AppException;
import edge.core.modules.auth.AuthService;
import edge.core.modules.auth.SignUpEntity;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.mailSender.AppMailSender;

@WebService
@Component
public class RegisterServiceImpl implements RegisterService{

	private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private CommonHibernateDao commonHibernateDao;
		
	@Override
	@Transactional
	public ProfileDetails register(ProfileDetails profileDetails) throws Exception {
		
		// For Cloning profile
		String emailId = profileDetails.getSecure().getEmailId();
		if(emailId == null || emailId.contains("@test.com")){
			profileDetails.setProfileId(null);
			profileDetails.getSecure().setProfileId(null);
		}
		
		SignUpEntity signUpEntity = new SignUpEntity();
		signUpEntity.setGender(profileDetails.getGender());
		signUpEntity.setEmailId(profileDetails.getSecure().getEmailId());
		profileDetails.setSignUpEntity(signUpEntity);
		
		authService.signUp(profileDetails.getSignUpEntity());
		
		String profileId = profileDetails.getSignUpEntity().getProfileId();
		profileDetails.setProfileId(profileId);
		profileDetails.getSecure().setProfileId(profileId);
		
		List<String> errors = profileDetails.validate();
		if(errors == null || errors.size() ==0){
			commonHibernateDao.save(profileDetails);
			commonHibernateDao.save(profileDetails.getSecure());
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("profileDetails", profileDetails);				
			AppMailSender.sendEmail(profileDetails.getSignUpEntity(), params, EventDetailsEnum.NEW_ACCT_CREATION);
			logger.debug("Account Successfully created for {} {}", profileDetails.getSignUpEntity().getEmailId(), profileDetails.getProfileId() );
		}else{
			throw new AppException(null, "There were below error(s) while processing your request ", "Please sign up again.", errors);
		}
		
		return profileDetails;
	}

	public AuthService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	public CommonHibernateDao getCommonHibernateDao() {
		return commonHibernateDao;
	}

	public void setCommonHibernateDao(CommonHibernateDao commonHibernateDao) {
		this.commonHibernateDao = commonHibernateDao;
	}

}
