package edge.app.modules.tagCreation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.common.Utils;
import edge.app.modules.foundReport.FoundReportsService;
import edge.app.modules.mail.EventDetailsEnum;
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
			
			Date current = new Date();
			tagCreation.setCreatedOn(current);
			tagCreation.setUpdatedOn(current);
			
			commonHibernateDao.save(tagCreation);
			
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
		return (TagCreation) commonHibernateDao.getHibernateTemplate().find(" from TagCreation where addressEmail = '" + addressEmail + "'").get(0);
	}

	public FoundReportsService getFoundReportsService() {
		return foundReportsService;
	}

	public void setFoundReportsService(FoundReportsService foundReportsService) {
		this.foundReportsService = foundReportsService;
	}
	
}
