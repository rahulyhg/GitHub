package edge.core.modules.auth;

import javax.jws.WebService;

@WebService
public interface AuthService {

	SignUpEntity signUp(SignUpEntity signUpEntity);
	
	SignUpEntity resetPassword(SignUpEntity signUpEntity);

	String sendVerificationCode(String emailId);
	
	UserViewModel getLoggedInUser(String loggedInId);

	void signUpUsers(String[] emailIds);

	SignUpEntity getSignUpEntity(String emailId);
	
	

}
