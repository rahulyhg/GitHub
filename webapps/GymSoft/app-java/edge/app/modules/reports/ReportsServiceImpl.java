package edge.app.modules.reports;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jws.WebService;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.core.modules.auth.SecurityRoles;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.parents.ParentsService;

@WebService
@Component
public class ReportsServiceImpl implements ReportsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private ParentsService parentsService;
	
	@Override
	@Transactional
	public List getMembershipsReport(String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_ADMIN);
		GregorianCalendar from = new GregorianCalendar();
		from.add(Calendar.MONTH, -12);

		String queryString = "select year(fromDate), month(fromDate), collectionByName, packageName, count(membershipId), sum(effectiveAmount) as collection from Membership where parentId= " + parentId +
				             "  group by year(fromDate), month(fromDate), collectionByName, packageName " +
				             "  order by year(fromDate) desc, month(fromDate) desc, collectionByName, packageName ";
		
		Query query = commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(queryString);
		
		List list = query.list();
		return list;
	}
	
	@Override
	@Transactional
	public List getPaymentsReport(String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_ADMIN);
		GregorianCalendar from = new GregorianCalendar();
		from.add(Calendar.MONTH, -12);

		String queryString = "select year(paidOn), month(paidOn), pymtMode, count(paymentId), sum(paidAmount) as collection from Payment where parentId= " + parentId +
				             "  group by year(paidOn),month(paidOn), pymtMode " +
				             "  order by year(paidOn) desc, month(paidOn) desc, pymtMode ";
		
		Query query = commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(queryString);
		
		List list = query.list();
		return list;
	}

	public ParentsService getParentsService() {
		return parentsService;
	}

	public void setParentsService(ParentsService parentsService) {
		this.parentsService = parentsService;
	}

	@Override
	@Transactional
	public List getAttendancesReport(String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_ADMIN);
		GregorianCalendar from = new GregorianCalendar();
		from.add(Calendar.MONTH, -12);

		String queryString = "select year(attendanceOn), month(attendanceOn), employeeName, count(attendanceOn), sum(hoursWorked), avg(hoursWorked) as collection from Attendance where parentId= " + parentId +
				             "  group by year(attendanceOn), month(attendanceOn), employeeName " +
				             "  order by year(attendanceOn) desc, month(attendanceOn) desc, employeeName ";
		
		Query query = commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(queryString);
		
		List list = query.list();
		return list;
	}
	
	@Override
	@Transactional
	public List getExpensesReport(String loggedInId) {
		int parentId = parentsService.getParentId(loggedInId, SecurityRoles.PARENT_ADMIN);
		GregorianCalendar from = new GregorianCalendar();
		from.add(Calendar.MONTH, -12);

		String queryString = "select year(paidOn), month(paidOn), pymtMode, count(expenseId), sum(paidAmount) as collection from Expense where parentId= " + parentId +
				             "  group by year(paidOn),month(paidOn), pymtMode " +
				             "  order by year(paidOn) desc, month(paidOn) desc, pymtMode ";
		
		Query query = commonHibernateDao.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(queryString);
		
		List list = query.list();
		return list;
	}
}
