package edge.app.modules.register;

import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edge.app.modules.profile.ProfileDetails;
import edge.appCore.modules.mailSender.EventDetailsEnum;
import edge.core.modules.auth.AuthService;
import edge.core.modules.auth.SignUpEntity;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.common.EdgeResponse;
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
	public EdgeResponse<ProfileDetails> register(ProfileDetails profileDetails) throws Exception {
				
		EdgeResponse<SignUpEntity> response = authService.signUp(profileDetails.getSignUpEntity());
		
		if(response.isSuccess()){
			profileDetails.setProfileId(profileDetails.getSignUpEntity().getProfileId());
			profileDetails.getSecure().setEmail(profileDetails.getSignUpEntity().getEmailId());
			
			List<String> errors = profileDetails.validate();
			if(errors == null || errors.size() ==0){
				commonHibernateDao.save(profileDetails);
				commonHibernateDao.save(profileDetails.getSecure());
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("profileDetails", profileDetails);				
				AppMailSender.sendEmail(profileDetails.getSignUpEntity(), params, EventDetailsEnum.NEW_ACCT_CREATION);
				logger.debug("Account Successfully created for {} {}", profileDetails.getSignUpEntity().getEmailId(), profileDetails.getProfileId() );
			}else{
				return EdgeResponse.createErrorResponse(
						profileDetails,
						"There were below error(s) while processing your request.",
						"Please sign up again.",
						errors);
			}
		}else{
			return EdgeResponse.createErrorResponse(profileDetails, response.getHeader(), response.getFooter(), response.getErrors());
		}
		return EdgeResponse.createSuccessResponse(profileDetails, response.getHeader(), response.getFooter(), response.getMessages());
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
