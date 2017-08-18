package edge.app.modules.lostReport;

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
import edge.app.modules.matchReport.MatchReportsService;
import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.mailSender.AppMailSender;

@WebService
@Component
public class LostReportsServiceImpl implements LostReportsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private MatchReportsService matchReportsService;

	@Override
	@Transactional
	public LostReport saveLostReport(LostReport lostReport) {
		try{
			
			Date current = new Date();
			lostReport.setCreatedOn(current);
			lostReport.setUpdatedOn(current);
			lostReport.setMatchingKey(Utils.deriveMatchingKey(lostReport));
			
			commonHibernateDao.save(lostReport);
			
			Map<String, Object> dataObject = new HashMap<String, Object>();
			dataObject.put("lostReport", lostReport);
			
			AppMailSender.sendEmail(String.valueOf("ID: " + lostReport.getLostReportId()), lostReport.getAddressEmail(), dataObject , EventDetailsEnum.LOST_REPORT_SAVED);
			
			matchReportsService.searchMatchingReports(lostReport);
			
		}catch(DataIntegrityViolationException ex){
			ex.printStackTrace();
			throw new AppException(ex, "Similar report already exists");
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return lostReport;
	}

	@Override
	public LostReport getLostReport(Long lostReportId) {
		return commonHibernateDao.getEntityById(LostReport.class, lostReportId);
	}

	public MatchReportsService getMatchReportsService() {
		return matchReportsService;
	}

	public void setMatchReportsService(MatchReportsService matchReportsService) {
		this.matchReportsService = matchReportsService;
	}
	
}
