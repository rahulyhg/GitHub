package edge.app.modules.foundRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.common.RequestStatusEnum;
import edge.app.modules.common.Utils;
import edge.app.modules.lostRequest.LostRequest;
import edge.app.modules.lostRequest.LostRequestsService;
import edge.app.modules.mail.EventDetailsEnum;
import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.mailSender.AppMailSender;

@WebService
@Component
public class FoundRequestsServiceImpl implements FoundRequestsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private LostRequestsService lostRequestsService;
	
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
			
			searchMatchingRequests(foundRequest);
			
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

	@Override
	public List<FoundRequest> searchMatchingRequests(int lostRequestId) throws Exception {
		return searchMatchingRequests(lostRequestsService.getLostRequest(lostRequestId));
	}

	@Override
	public List<FoundRequest> searchMatchingRequests(LostRequest lostRequest) throws Exception {

		String query = " from FoundRequest where matchingKey = '" + lostRequest.getMatchingKey() + "' and status = '" + lostRequest.getStatus().name() + "'";
		List<FoundRequest> foundRequests = commonHibernateDao.getHibernateTemplate().find(query);
		
		switch(lostRequest.getIdType()){
		case UNIQUE_ID:
		case LOST_AND_FOUND_ID:
			if(foundRequests != null && foundRequests.size() == 1){
				matchFound(lostRequest, foundRequests.get(0));
			}else{
				Map<String, Object> dataObject = new HashMap<String, Object>();
				dataObject.put("lostRequest", lostRequest);
				
				AppMailSender.sendEmail("" + lostRequest.getLostRequestId(),lostRequest.getAddressEmail(), dataObject, EventDetailsEnum.MATCH_NOT_FOUND);
			}
			break;
		case NONE:
			break;
		}
		
		return foundRequests;
	}

	@Override
	public List<FoundRequest> searchMatchingRequestsAsPerLFI(String lostAndFoundId) throws Exception {

		String query = " from FoundRequest where lostAndFoundId = '" + lostAndFoundId + "'";
		List<FoundRequest> foundRequests = commonHibernateDao.getHibernateTemplate().find(query);
		
		if(foundRequests != null && foundRequests.size() == 1){
			matchFound(lostAndFoundId, foundRequests.get(0));
		}else{
			Map<String, Object> dataObject = new HashMap<String, Object>();
			AppMailSender.sendEmail(lostAndFoundId,"patil.vinayb9@gmail.com", dataObject, EventDetailsEnum.MATCH_NOT_FOUND_LFI);
		}
		
		return foundRequests;
	}

	private void matchFound(String lostAndFoundId, FoundRequest foundRequest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<LostRequest> searchMatchingRequests(FoundRequest foundRequest) throws Exception {

		String query = " from LostRequest where matchingKey = '" + foundRequest.getMatchingKey() + "' and status = '" + foundRequest.getStatus().name() + "'";
		List<LostRequest> lostRequests = commonHibernateDao.getHibernateTemplate().find(query);
		
		switch(foundRequest.getIdType()){
		case UNIQUE_ID:
		case LOST_AND_FOUND_ID:
			if(lostRequests != null && lostRequests.size() == 1){
				matchFound(lostRequests.get(0), foundRequest);
			}
			break;
		case NONE:
			break;
		}
		
		return lostRequests;
	}

	private void matchFound(LostRequest lostRequest, FoundRequest foundRequest) throws Exception {
		
		lostRequest.setFoundRequestId(foundRequest.getFoundRequestId());
		lostRequest.setStatus(RequestStatusEnum.MATCHED);
		
		foundRequest.setLostdRequestId(lostRequest.getFoundRequestId());
		foundRequest.setStatus(RequestStatusEnum.MATCHED);
		
		commonHibernateDao.update(lostRequest);
		commonHibernateDao.update(foundRequest);
		
		Map<String, Object> dataObject = new HashMap<String, Object>();
		dataObject.put("lostRequest", lostRequest);
		dataObject.put("foundRequest", foundRequest);
		
		AppMailSender.sendEmail("" + lostRequest.getLostRequestId() +  " - " + foundRequest.getFoundRequestId(), 
				new String[]{lostRequest.getAddressEmail(), foundRequest.getAddressEmail()}, dataObject, EventDetailsEnum.MATCH_FOUND);
	}
	
}
