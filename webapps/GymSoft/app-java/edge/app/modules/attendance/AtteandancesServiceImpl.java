package edge.app.modules.attendance;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.gyms.GymsService;
import edge.appCore.modules.auth.SecurityRoles;
import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;

@WebService
@Component
public class AtteandancesServiceImpl implements AttendancesService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private GymsService gymsService;

	@Override
	@Transactional
	public Attendance saveAttendance(Attendance attendance, String loggedInId) {
		try{
			int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_OPERATOR);
			
			attendance.setCreatedBy(loggedInId);
			attendance.setUpdatedBy(loggedInId);
			attendance.setSystemId(systemId);
			
			if(attendance.getCheckOutHr() != 0 && attendance.getCheckInHr() != 0){
				attendance.setHoursWorked(attendance.getCheckOutHr() - attendance.getCheckInHr());
			}else{
				attendance.setHoursWorked(0);
			}
			
			commonHibernateDao.saveOrUpdate(attendance);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex,  ex.getMessage());
		}
		return attendance;
	}

	@Override
	public List<Attendance> getAllAttendances(String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Attendance where systemId = '" + systemId +"' order by attendanceOn desc, checkInHr desc");
	}

	public GymsService getGymsService() {
		return gymsService;
	}

	public void setGymsService(GymsService gymsService) {
		this.gymsService = gymsService;
	}
}
