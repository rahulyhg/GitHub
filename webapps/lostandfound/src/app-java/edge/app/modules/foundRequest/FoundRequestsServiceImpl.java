package edge.app.modules.foundRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.common.Utils;
import edge.app.modules.mail.EventDetailsEnum;
import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.mailSender.AppMailSender;

@WebService
@Component
public class FoundRequestsServiceImpl implements FoundRequestsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Override
	@Transactional
	public FoundRequest saveFoundRequest(FoundRequest foundRequest) {
		try{
			
			Date current = new Date();
			foundRequest.setCreatedOn(current);
			foundRequest.setUpdatedOn(current);
			foundRequest.setMatchingKey(Utils.deriveMatchingKey(foundRequest));
			
			commonHibernateDao.save(foundRequest);
			
			Map<String, Object> dataObject = new HashMap<String, Object>();
			dataObject.put("foundRequest", foundRequest);
			
			AppMailSender.sendEmail(String.valueOf("ID: " + foundRequest.getFoundRequestId()), foundRequest.getAddressEmail(), dataObject , EventDetailsEnum.FOUND_REQUEST_SAVED);
			
		}catch(DataIntegrityViolationException ex){
			ex.printStackTrace();
			throw new AppException(ex, "Similar request already exists");
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return foundRequest;
	}

	@Override
	public FoundRequest getFoundRequest(int foundRequestId) {
		return commonHibernateDao.getEntityById(FoundRequest.class, foundRequestId);
	}
	
}
