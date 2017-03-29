package edge.app.modules.reports;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jws.WebService;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.gyms.GymsService;
import edge.appCore.modules.auth.SecurityRoles;
import edge.core.modules.common.CommonHibernateDao;

@WebService
@Component
public class ReportsServiceImpl implements ReportsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private GymsService gymsService;
	
	@Override
	@Transactional
	public List getMembershipsReport(String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_ADMIN);
		GregorianCalendar from = new GregorianCalendar();
		from.add(Calendar.MONTH, -12);

		String queryString = "select year(fromDate), month(fromDate), collectionByName, packageName, sum(effectiveAmount) as collection from Membership where systemId= " + systemId +"  group by year(fromDate), month(fromDate), collectionByName, packageName order by year(fromDate) desc, month(fromDate) desc, packageName ";
		Query query = commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(queryString);
		
		List list = query.list();
		return list;
	}
	
	@Override
	@Transactional
	public List getPaymentsReport(String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_ADMIN);
		GregorianCalendar from = new GregorianCalendar();
		from.add(Calendar.MONTH, -12);

		String queryString = "select year(paidOn), month(paidOn), pymtMode, sum(paidAmount) as collection from Payment where systemId= " + systemId +"  group by year(paidOn),month(paidOn), pymtMode order by year(paidOn) desc, month(paidOn) desc";
		Query query = commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(queryString);
		
		List list = query.list();
		return list;
	}

	public GymsService getGymsService() {
		return gymsService;
	}

	public void setGymsService(GymsService gymsService) {
		this.gymsService = gymsService;
	}

	@Override
	@Transactional
	public List getAttendancesReport(String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_ADMIN);
		GregorianCalendar from = new GregorianCalendar();
		from.add(Calendar.MONTH, -12);

		String queryString = "select year(attendanceOn), month(attendanceOn), employeeName, count(attendanceOn), sum(hoursWorked), avg(hoursWorked) as collection from Attendance where systemId= " + systemId +"  group by year(attendanceOn), month(attendanceOn), employeeName order by year(attendanceOn) desc, month(attendanceOn) desc";
		Query query = commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(queryString);
		
		List list = query.list();
		return list;
	}
	
	@Override
	@Transactional
	public List getExpensesReport(String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_ADMIN);
		GregorianCalendar from = new GregorianCalendar();
		from.add(Calendar.MONTH, -12);

		String queryString = "select year(paidOn), month(paidOn), pymtMode, sum(paidAmount) as collection from Expense where systemId= " + systemId +"  group by year(paidOn),month(paidOn), pymtMode order by year(paidOn) desc, month(paidOn) desc";
		Query query = commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(queryString);
		
		List list = query.list();
		return list;
	}
}
