package edge.app.modules.tagCreation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.foundReport.FoundReportsService;
import edge.app.modules.mail.EventDetailsEnum;
import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.mailSender.AppMailSender;

@WebService
@Component
public class TagCreationsServiceImpl implements TagCreationsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	@Autowired
	private FoundReportsService foundReportsService;

	@Override
	@Transactional
	public TagCreation saveTagCreation(TagCreation tagCreation) throws Exception {
		try{
			String otp = tagCreation.getOtp();
			Date current = new Date();
			
			if(otp != null && otp.trim().length() != 0){
				// UPDATE CASE
				
				tagCreation.setUpdatedOn(current);
				tagCreation.setOtp("");
				commonHibernateDao.update(tagCreation);
				
			}else{
				
				// NEW CASE
				tagCreation.setCreatedOn(current);
				tagCreation.setUpdatedOn(current);
				
				commonHibernateDao.save(tagCreation);
				
			}

			Map<String, Object> dataObject = new HashMap<String, Object>();
			dataObject.put("tagCreation", tagCreation);
			
			AppMailSender.sendEmail(String.valueOf("ID: " + tagCreation.getTagCreationId()), tagCreation.getAddressEmail(), dataObject , EventDetailsEnum.TAG_CREATION_SAVED);
			
			
		}catch(DataIntegrityViolationException ex){
			ex.printStackTrace();
			throw new AppException(ex, "This email Id already exists. Please use Update feature to update the details.");
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return tagCreation;
	}

	@Override
	public TagCreation getTagCreation(Long tagCreationId) {
		return commonHibernateDao.getEntityById(TagCreation.class, tagCreationId);
	}

	@Override
	public TagCreation getTagCreationByEmail(String addressEmail) {
		List tagCreations = commonHibernateDao.getHibernateTemplate().find(" from TagCreation where addressEmail = '" + addressEmail + "'");
		if(tagCreations != null && tagCreations.size() == 1){
			return (TagCreation) tagCreations.get(0);
		}
		return null;
	}

	public FoundReportsService getFoundReportsService() {
		return foundReportsService;
	}

	public void setFoundReportsService(FoundReportsService foundReportsService) {
		this.foundReportsService = foundReportsService;
	}

	@Override
	public void sendOTPForTagUpdate(String emailId) throws Exception {
		
		TagCreation tagCreation = getTagCreationByEmail(emailId);
		
		if(tagCreation == null){
			throw new AppException(null, "Invalid Email Address.");
		}
		
		String otp = RandomStringUtils.randomAlphanumeric(CoreConstants.PROFILE_ID_SIZE).toUpperCase();
		tagCreation.setOtp(otp);
		commonHibernateDao.update(tagCreation);
		
		Map<String, Object> dataObject = new HashMap<String, Object>();
		dataObject.put("tagCreation", tagCreation);
		
		AppMailSender.sendEmail("OTP: " + otp, tagCreation.getAddressEmail(), dataObject , EventDetailsEnum.TAG_UPDATE_OTP);
		
	}

	@Override
	public TagCreation verifyOTPForTagUpdate(String emailId, String otp) {

		TagCreation tagCreationByEmail = getTagCreationByEmail(emailId);
		if(tagCreationByEmail == null || !tagCreationByEmail.getOtp().equals(otp)){
			throw new AppException(null, "Invalid OTP Entered");
		}
		return tagCreationByEmail;
	}
	
}
