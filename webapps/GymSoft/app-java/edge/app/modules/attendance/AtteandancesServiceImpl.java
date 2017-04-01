package edge.app.modules.attendance;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.core.exception.AppException;
import edge.core.modules.auth.SecurityRoles;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.parents.ParentsService;

@WebService
@Component
public class AtteandancesServiceImpl implements AttendancesService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private ParentsService parentsService;

	@Override
	@Transactional
	public Attendance saveAttendance(Attendance attendance, String loggedInId) {
		try{
			int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
			
			attendance.setCreatedBy(loggedInId);
			attendance.setUpdatedBy(loggedInId);
			attendance.setParentId(parentId);
			
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
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Attendance where parentId = '" + parentId +"' order by attendanceOn desc, checkInHr desc");
	}

	public ParentsService getParentsService() {
		return parentsService;
	}

	public void setParentsService(ParentsService parentsService) {
		this.parentsService = parentsService;
	}
}
