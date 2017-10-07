package edge.app.modules.profile;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.core.exception.AppException;
import edge.core.modules.auth.SignUpEntity;
import edge.core.modules.common.CommonHibernateDao;

@Component
public class ProfileServiceImpl implements ProfileService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);
	
	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	@Override
	public ProfileDetails getFullProfileDetails(String emailId) {
		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, emailId);
		
		ProfileDetails profileDetails = commonHibernateDao.getEntityById(ProfileDetails.class, signUpEntity.getProfileId());
		profileDetails.setSecure(commonHibernateDao.getEntityById(SecureProfileDetails.class, signUpEntity.getProfileId()));
		profileDetails.setSignUpEntity(signUpEntity);
		return profileDetails;
	}
	
	@Override
	@Transactional
	public ProfileDetails updateMyProfile(ProfileDetails profileDetails, String emailId){			

		SignUpEntity signUpEntity = commonHibernateDao.getEntityById(SignUpEntity.class, emailId);
		
		String profileId = signUpEntity.getProfileId();
		profileDetails.setProfileId(profileId);
		
		List<String> errors = profileDetails.validate();
		if(errors != null && errors.size() != 0){
			throw new AppException(null, "There were below errors while processing your request", "Please try after some time.", errors);
		}else{
			commonHibernateDao.update(profileDetails);
			commonHibernateDao.update(profileDetails.getSecure());
		}
		
		return profileDetails;
	}

}
