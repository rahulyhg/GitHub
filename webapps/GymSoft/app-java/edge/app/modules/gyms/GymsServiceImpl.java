package edge.app.modules.gyms;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.appCore.modules.auth.SecurityRoles;
import edge.core.exception.AppException;
import edge.core.modules.auth.AuthService;
import edge.core.modules.common.CommonHibernateDao;
	
@WebService
@Component
public class GymsServiceImpl implements GymsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public Gym addGym(Gym gym) {
		try{
			gym.setFromDate(DateUtils.addDays(new Date(), -1));
			
			Calendar cal = Calendar.getInstance(); 
			cal.add(Calendar.MONTH, 3);
			gym.setToDate(cal.getTime());
			gym.setAdmins(gym.getAdmins() + "," + gym.getEmailId());
			
			authService.signUpUsers(gym.getOperators().split(","));
			authService.signUpUsers(gym.getAdmins().split(","));
			
			commonHibernateDao.save(gym);
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return gym;
	}
	
	@Override
	public Gym updateGym(Gym gym, String loggedInId) {
		try{
			gym.setUpdatedBy(loggedInId);
			
			authService.signUpUsers(gym.getOperators().split(","));
			authService.signUpUsers(gym.getAdmins().split(","));
			
			commonHibernateDao.update(gym);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return gym;
	}

	@Override
	public List<Gym> getAllGyms() {
		return commonHibernateDao.getHibernateTemplate().loadAll(Gym.class);
	}

	@Override
	public int getSystemId(String loggedInId, SecurityRoles role) {
		String query = "";
		switch(role){
			case GYM_OPERATOR: query = "from Gym where operators like '%," + loggedInId + ",%' OR admins like '%," + loggedInId + ",%'"; break; 
			case GYM_ADMIN: query = "from Gym where admins like '%," + loggedInId + ",%'"; break;
		}
		query += " and fromDate <= current_date and toDate >= current_date ";
		List<Gym> gyms = commonHibernateDao.getHibernateTemplate().find(query);
		if(gyms == null || gyms.size() != 1){
			throw new AppException(null, "Unauthorized Access");
		}
		
		return gyms.get(0).getGymId();
	}

	@Override
	@Transactional
	public int executeQuery(String query) {
		return commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query).executeUpdate();
	}

	@Override
	public List showAll(String entity) {
		return commonHibernateDao.getHibernateTemplate().find(" from " + entity);
	}
}
