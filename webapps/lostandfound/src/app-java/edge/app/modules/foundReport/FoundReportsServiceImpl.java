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

import edge.app.modules.common.ReportStatusEnum;
import edge.app.modules.common.Utils;
import edge.app.modules.lostReport.LostReport;
import edge.app.modules.lostReport.LostReportsService;
import edge.app.modules.mail.EventDetailsEnum;
import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.mailSender.AppMailSender;

@WebService
@Component
public class FoundReportsServiceImpl implements FoundReportsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private LostReportsService lostReportsService;
	
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
			
			AppMailSender.sendEmail(String.valueOf("ID: " + foundReport.getFoundReportId()), foundReport.getAddressEmail(), dataObject , EventDetailsEnum.FOUND_REPORT_SAVED);
			
			searchMatchingReports(foundReport);
			
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
	public FoundReport getFoundReport(int foundReportId) {
		return commonHibernateDao.getEntityById(FoundReport.class, foundReportId);
	}

	@Override
	public List<FoundReport> searchMatchingReports(int lostReportId) throws Exception {
		return searchMatchingReports(lostReportsService.getLostReport(lostReportId));
	}

	@Override
	public List<FoundReport> searchMatchingReports(LostReport lostReport) throws Exception {

		String query = " from FoundReport where matchingKey = '" + lostReport.getMatchingKey() + "' and status = '" + lostReport.getStatus().name() + "'";
		List<FoundReport> foundReports = commonHibernateDao.getHibernateTemplate().find(query);
		
		switch(lostReport.getIdType()){
		case UNIQUE_ID:
		case LOST_AND_FOUND_ID:
			if(foundReports != null && foundReports.size() == 1){
				matchFound(lostReport, foundReports.get(0));
			}else{
				Map<String, Object> dataObject = new HashMap<String, Object>();
				dataObject.put("lostReport", lostReport);
				
				AppMailSender.sendEmail("" + lostReport.getLostReportId(),lostReport.getAddressEmail(), dataObject, EventDetailsEnum.MATCH_NOT_FOUND);
			}
			break;
		case NONE:
			break;
		}
		
		return foundReports;
	}

	@Override
	public List<FoundReport> searchMatchingReportsAsPerLFI(String lostAndFoundId) throws Exception {

		String query = " from FoundReport where lostAndFoundId = '" + lostAndFoundId + "'";
		List<FoundReport> foundReports = commonHibernateDao.getHibernateTemplate().find(query);
		
		if(foundReports != null && foundReports.size() == 1){
			matchFound(lostAndFoundId, foundReports.get(0));
		}else{
			Map<String, Object> dataObject = new HashMap<String, Object>();
			AppMailSender.sendEmail(lostAndFoundId,"lostandfound.report@gmail.com", dataObject, EventDetailsEnum.MATCH_NOT_FOUND_LFI);
		}
		
		return foundReports;
	}

	private void matchFound(String lostAndFoundId, FoundReport foundReport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<LostReport> searchMatchingReports(FoundReport foundReport) throws Exception {

		String query = " from LostReport where matchingKey = '" + foundReport.getMatchingKey() + "' and status = '" + foundReport.getStatus().name() + "'";
		List<LostReport> lostReports = commonHibernateDao.getHibernateTemplate().find(query);
		
		switch(foundReport.getIdType()){
		case UNIQUE_ID:
		case LOST_AND_FOUND_ID:
			if(lostReports != null && lostReports.size() == 1){
				matchFound(lostReports.get(0), foundReport);
			}
			break;
		case NONE:
			break;
		}
		
		return lostReports;
	}

	private void matchFound(LostReport lostReport, FoundReport foundReport) throws Exception {
		
		lostReport.setFoundReportId(foundReport.getFoundReportId());
		lostReport.setStatus(ReportStatusEnum.MATCHED);
		
		foundReport.setLostdReportId(lostReport.getFoundReportId());
		foundReport.setStatus(ReportStatusEnum.MATCHED);
		
		commonHibernateDao.update(lostReport);
		commonHibernateDao.update(foundReport);
		
		Map<String, Object> dataObject = new HashMap<String, Object>();
		dataObject.put("lostReport", lostReport);
		dataObject.put("foundReport", foundReport);
		
		AppMailSender.sendEmail("" + lostReport.getLostReportId() +  " - " + foundReport.getFoundReportId(), 
				new String[]{lostReport.getAddressEmail(), foundReport.getAddressEmail()}, dataObject, EventDetailsEnum.MATCH_FOUND);
	}
	
}
