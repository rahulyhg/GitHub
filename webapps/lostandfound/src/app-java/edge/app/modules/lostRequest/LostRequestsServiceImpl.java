package edge.app.modules.lostRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.common.Utils;
import edge.app.modules.foundRequest.FoundRequestsService;
import edge.app.modules.mail.EventDetailsEnum;
import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.mailSender.AppMailSender;

@WebService
@Component
public class LostRequestsServiceImpl implements LostRequestsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	@Autowired
	private FoundRequestsService foundRequestsService;

	@Override
	@Transactional
	public LostRequest saveLostRequest(LostRequest lostRequest) {
		try{
			
			Date current = new Date();
			lostRequest.setCreatedOn(current);
			lostRequest.setUpdatedOn(current);
			lostRequest.setMatchingKey(Utils.deriveMatchingKey(lostRequest));
			
			commonHibernateDao.save(lostRequest);
			
			Map<String, Object> dataObject = new HashMap<String, Object>();
			dataObject.put("lostRequest", lostRequest);
			
			AppMailSender.sendEmail(String.valueOf("ID: " + lostRequest.getLostRequestId()), lostRequest.getAddressEmail(), dataObject , EventDetailsEnum.LOST_REQUEST_SAVED);
			
			foundRequestsService.searchMatchingRequests(lostRequest);
			
		}catch(DataIntegrityViolationException ex){
			ex.printStackTrace();
			throw new AppException(ex, "Similar request already exists");
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return lostRequest;
	}

	@Override
	public LostRequest getLostRequest(int lostRequestId) {
		return commonHibernateDao.getEntityById(LostRequest.class, lostRequestId);
	}

	public FoundRequestsService getFoundRequestsService() {
		return foundRequestsService;
	}

	public void setFoundRequestsService(FoundRequestsService foundRequestsService) {
		this.foundRequestsService = foundRequestsService;
	}
	
}
