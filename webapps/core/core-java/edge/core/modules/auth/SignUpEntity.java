package edge.core.modules.auth;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;

import edge.core.modules.common.EdgeEntity;

@Entity
@Table(name = "users")
public class SignUpEntity extends EdgeEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USERNAME", unique = true, nullable = false, updatable=false, length = 100)
	private String userName;		
	
	@Column(name = "PASSWORD", nullable = false, length = 50)
	private String pwd;
	
	@Column(name = "ENABLED", nullable = false)
	private Boolean enabled = true;	
	
	@Column(name = "CONFIRM_PASSWORD", nullable = false, length = 50)
	private String confirmPwd;
	
	@Column(unique = true, nullable = false, updatable=false, length = 20)	
	private String profileId;
	
	@Column(unique = true, nullable = false, updatable=false, length = 100)
	private String emailId;	
	
	@Column(nullable = false, length = 10)
	private String gender;
	
	private String verificationCode;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId.toLowerCase();
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	
	@Override
	public void maskData() {
		pwd = "";
		confirmPwd = "";
	}
	
	@Override
	public List<String> validate() {
		
		List<String> errors = new ArrayList<String>();
		if(StringUtils.isBlank(pwd)){
			errors.add("Password can not be empty.");
		}
		if(StringUtils.isBlank(confirmPwd)){
			errors.add("Confirm Password can not be empty.");
		}
		if(StringUtils.isBlank(emailId) || !EmailValidator.getInstance().isValid(emailId)){
			errors.add("Invalid Email Id entered " + emailId);
		}
		if(!StringUtils.equals(pwd, confirmPwd)){
			errors.add("Password and Confirm Password must match.");
		}
		
		if(errors.size() == 0){
			return null;
		}else{
			return errors;
		}
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
}
