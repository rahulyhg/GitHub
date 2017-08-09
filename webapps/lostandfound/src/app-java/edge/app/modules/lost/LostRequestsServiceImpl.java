package edge.app.modules.lost;

import java.util.Date;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;

@WebService
@Component
public class LostRequestsServiceImpl implements LostRequestsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Override
	@Transactional
	public LostRequest saveLostRequest(LostRequest lostRequest) {
		try{
			
			Date current = new Date();
			lostRequest.setCreatedOn(current );
			lostRequest.setUpdatedOn(current);
			
			commonHibernateDao.save(lostRequest);
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

}
