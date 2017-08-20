package edge.app.modules.foundReport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.common.Utils;
import edge.app.modules.lostReport.LostReport;
import edge.app.modules.mail.EventDetailsEnum;
import edge.app.modules.matchReport.MatchReportsService;
import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.mailSender.AppMailSender;

@WebService
@Component
public class FoundReportsServiceImpl implements FoundReportsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private MatchReportsService matchReportsService;
		
	@Override
	@Transactional
	public FoundReport saveFoundReport(FoundReport foundReport) {
		try{
			
			Date current = new Date();
			foundReport.setCreatedOn(current);
			foundReport.setUpdatedOn(current);
			foundReport.setMatchingKey(Utils.deriveMatchingKey(foundReport));
			
			commonHibernateDao.save(foundReport);
			
			Map<String, Object> dataObject = new HashMap<String, Object>();
			dataObject.put("foundReport", foundReport);
			
			List<LostReport> lostReports = matchReportsService.searchMatchingReports(foundReport);
			
			AppMailSender.sendEmail(String.valueOf("ID: " + foundReport.getFoundReportId()), foundReport.getAddressEmail(), dataObject , EventDetailsEnum.FOUND_REPORT_SAVED);
			
		}catch(DataIntegrityViolationException ex){
			ex.printStackTrace();
			throw new AppException(ex, "Similar report already exists");
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return foundReport;
	}

	@Override
	public FoundReport getFoundReport(Long foundReportId) {
		return commonHibernateDao.getEntityById(FoundReport.class, foundReportId);
	}
	
}
