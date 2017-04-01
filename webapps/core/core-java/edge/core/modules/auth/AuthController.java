package edge.core.modules.auth;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edge.core.modules.common.EdgeResponse;
import edge.core.modules.mailSender.AppMailSender;
import edge.core.modules.mailSender.EventDetails;

@Controller
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthService authService;
	
	@RequestMapping(value={"/unsecured/auth/signUp"})
	public EdgeResponse<SignUpEntity> signUp(
			@RequestBody SignUpEntity signUpEntity 			
			){			
		return authService.signUp(signUpEntity);
		
	}
	
	@RequestMapping(value={"/unsecured/auth/sendVerificationCode"})
	public EdgeResponse<String> sendVerificationCode(
			@RequestBody SignUpEntity signUpEntity	
			) throws Exception{
		
		String emailId = signUpEntity.getEmailId();
		if(StringUtils.isBlank(emailId) || !EmailValidator.getInstance().isValid(emailId)){
			return EdgeResponse.createErrorResponse("Invalid Email Address.",null, null,null);
		}
		
		String verificationCode = authService.sendVerificationCode(emailId);
		if(verificationCode != null){
			Map<String, Object> dataObject = new HashMap<String, Object>();
			dataObject.put("verificationCode", verificationCode);
			AppMailSender.sendEmail("Verification Code", emailId, dataObject, new EventDetails(
					"Verification Code for Password Reset!",
					"VerificationCode.html"
					));	
			return EdgeResponse.createSuccessResponse("Verification code sent through mail.", null, null, null);
		}else{
			return EdgeResponse.createErrorResponse("Invalid Email address.", null, null, null);
		}		
	}
	
	@RequestMapping(value={"/unsecured/auth/resetPassword"})
	public EdgeResponse<SignUpEntity> resetPassword(
			@RequestBody SignUpEntity signUpEntity 			
			){			
		return authService.resetPassword(signUpEntity);
		
	}
	
	@RequestMapping(value={"/unsecured/auth/getLoggedInUser"})
	public EdgeResponse<String> getLoggedInUser(
			Principal principal			
			){
		if(principal != null){
			return EdgeResponse.createDataResponse(principal.getName(), null);
		}else{
			return EdgeResponse.createDataResponse("", null);
		}
		
	}
	
	@RequestMapping(value={"/unsecured/auth/getLoggedInRole"})
	public EdgeResponse<String> getLoggedInRole(
			Principal principal			
			){
		if(principal != null){
			return EdgeResponse.createDataResponse(authService.getLoggedInRole(principal.getName()), null);
		}else{
			return EdgeResponse.createDataResponse("", null);
		}
		
	}
	
	@RequestMapping(value={"/unsecured/authFailed"})
	public EdgeResponse<String> authFailed(
			Principal principal			
			){
			
		return EdgeResponse.createErrorResponse("login", null, null, null);
	}
}
