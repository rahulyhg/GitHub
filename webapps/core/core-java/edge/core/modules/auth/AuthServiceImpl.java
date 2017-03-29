package edge.core.modules.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import edge.core.config.CoreConstants;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.common.EdgeResponse;
import edge.core.modules.mailSender.AppMailSender;
import edge.core.modules.mailSender.EventDetails;
import edge.core.utils.SpringQueriesUtil;

@WebService
@Component
public class AuthServiceImpl implements AuthService{

	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	@Autowired
	CommonHibernateDao commonHibernateDao;
	
	@Override
	public EdgeResponse<SignUpEntity> signUp(SignUpEntity signUpEntity) {
		if (checkIfExistingUser(signUpEntity)) {
					return EdgeResponse.createErrorResponse(
							signUpEntity,
							"This email Id is already registered with us! Please login using 'Log In' option. ",
							null,
							null);
		} else {

			List<String> errors = new ArrayList<String>();
			
			if(signUpEntity == null){
				errors .add("Please fill up the form.");
			}else{
				
				String emailId = signUpEntity.getEmailId();
				if(StringUtils.isBlank(emailId) || !EmailValidator.getInstance().isValid(emailId)){
					errors .add("Invalid Email Id entered " + emailId);
				}
				if(StringUtils.isBlank(signUpEntity.getGender())){
					errors .add("Please select gender. ");
				}
			}
			
			if (errors != null && errors.size() != 0){
				return EdgeResponse.createErrorResponse(
						signUpEntity,
						"There were below error(s) while processing your request.",
						"Please sign up again.",
						errors);
			} else {				
				return signUpNewMember(signUpEntity);						
			}
		}
	}

	private EdgeResponse<SignUpEntity> signUpNewMember(SignUpEntity signUpEntity) {
		EdgeResponse<SignUpEntity> edgeResponse = null;
		String profileId = null;
		try{
			boolean success = false;
			int attempt = 0;
			while(!success){
				try{
					profileId = RandomStringUtils.randomAlphanumeric(CoreConstants.PROFILE_ID_SIZE).toUpperCase(); 
					profileId = signUpEntity.getGender().toCharArray()[0] + profileId;
					String userName = signUpEntity.getEmailId();
					//String userName = profileId;
					
					signUpEntity.setUserName(userName);
					signUpEntity.setProfileId(profileId);
					String password = RandomStringUtils.randomAlphanumeric(CoreConstants.PROFILE_ID_SIZE).toUpperCase();
					if(userName.contains("@test.com")){
						password = "123456";
					}
					signUpEntity.setPwd(SpringSecurityUtil.encodePassword(password, null));
					signUpEntity.setConfirmPwd(password);
					
					commonHibernateDao.save(signUpEntity);
					
					AuthorityEntity authorityEntity = new AuthorityEntity();
					authorityEntity.setAuthority("ROLE_USER");
					authorityEntity.setUsername(userName);
					commonHibernateDao.save(authorityEntity);
					
					edgeResponse = EdgeResponse.createDataResponse(
													signUpEntity,
													"Congratulations, Your account has been successfully created! " +
													" Your Profile Id is : " + profileId + ". " +
													" Please check mail for further details, Thank You!.");
					success = true;
					
					Map<String, Object> dataObject = new HashMap<String, Object>();
					dataObject.put("signUpEntity", signUpEntity);
					AppMailSender.sendEmail("Account Creation", signUpEntity.getEmailId(), dataObject, new EventDetails(
							"Account Creation Successful! ",
							"NewAccountCreationSuccess.html"
							));	
					
				}catch (DataIntegrityViolationException e){
					e.printStackTrace();
					success = false;
					attempt++;
					if(attempt == CoreConstants.MAX_PROFILE_RETRY){
						throw new Exception("There was an error while processing your request. We Apologize! Please try again!");
					}
				}
			}			
			
		}catch (Exception e){
			e.printStackTrace();
			return EdgeResponse.createErrorResponse(signUpEntity, "There was an error while processing your request. We Apologize! Please try again!", null, null);
		}
		
		return edgeResponse;
	}
	
	private boolean checkIfExistingUser(SignUpEntity signUpEntity) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("user" , signUpEntity);
		String query = SpringQueriesUtil.getQuery("CHECK_IF_EXISTING_USER", paramsMap);
		
		SignUpEntity dbEntity = (SignUpEntity) commonHibernateDao.queryForObject(query, null);
		return dbEntity != null;
	}

	@Override
	public String sendVerificationCode(String emailId) {
		String verificationCode = null;
		SignUpEntity entityById = commonHibernateDao.getEntityById(SignUpEntity.class, emailId);
		if(entityById != null){
			verificationCode = RandomStringUtils.randomAlphanumeric(5).toUpperCase(); 
			entityById.setVerificationCode(verificationCode);
			commonHibernateDao.update(entityById);
		}
		return verificationCode;
	}

	@Override
	public EdgeResponse<SignUpEntity> resetPassword(SignUpEntity uiEntity) {

		 List<String> errors = uiEntity.validate();		 
		 if (errors != null){
				return EdgeResponse.createErrorResponse(
						uiEntity,
						"There were below error(s) while processing your request.",
						"Please try again.",
						errors);
		 }
		 
		 SignUpEntity entityById = commonHibernateDao.getEntityById(SignUpEntity.class, uiEntity.getEmailId());
		 
		 if(entityById == null){
			 return EdgeResponse.createErrorResponse(
						uiEntity,
						"Invalid Email Address.",
						"Please Register to continue.",
						null);
		 }
		 
		 String dbCode = entityById.getVerificationCode();
		 String uiCode = uiEntity.getVerificationCode();
		 logger.debug(" Inside ResetPassword {} {}", dbCode, uiCode );
		 
		 if(dbCode == null || ! dbCode.equals(uiCode)){
			 return EdgeResponse.createErrorResponse(
						uiEntity,
						"Invalid Verification Code.",
						"Please check mail or generate code again.",
						null);
		 }
		 
		 entityById.setPwd(SpringSecurityUtil.encodePassword(uiEntity.getPwd(), null));
		 entityById.setConfirmPwd(uiEntity.getPwd());
		 entityById.setVerificationCode(null);
			 
		 commonHibernateDao.update(entityById);	 
		
		 return EdgeResponse.createDataResponse(
				 	entityById,
					"Password Updated Successfully.");
	}

	@Override
	public String getLoggedInRole(String loggedInId) {
		List<AuthorityEntity> auths = commonHibernateDao.getHibernateTemplate().find("from AuthorityEntity where username='" + loggedInId + "'");
		if(auths != null && auths.size() == 1){
			return auths.get(0).getAuthority();
		}
		return null;
	}

	@Override
	public void signUpUsers(String[] emailIds) {
		for(String emailId : emailIds){
			if(emailId != null && emailId.trim().length() != 0){
				emailId = emailId.trim();
				SignUpEntity signUpEntity = new SignUpEntity();
				signUpEntity.setEmailId(emailId);
				signUpEntity.setGender("S");
				signUp(signUpEntity);
			}
		}
	}

}
