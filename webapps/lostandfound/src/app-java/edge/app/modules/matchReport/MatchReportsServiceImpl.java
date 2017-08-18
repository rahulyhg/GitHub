package edge.app.modules.matchReport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edge.app.modules.common.ReportStatusEnum;
import edge.app.modules.foundReport.FoundReport;
import edge.app.modules.lostReport.LostReport;
import edge.app.modules.lostReport.LostReportsService;
import edge.app.modules.mail.EventDetailsEnum;
import edge.app.modules.tagCreation.TagCreation;
import edge.app.modules.tagCreation.TagCreationsService;
import edge.core.modules.common.CommonHibernateDao;
import edge.core.modules.mailSender.AppMailSender;

@WebService
@Component
public class MatchReportsServiceImpl implements MatchReportsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private LostReportsService lostReportsService;
	
	@Autowired
	private TagCreationsService tagCreationsService;
	
	@Override
	public List<FoundReport> searchMatchingReports(Long lostReportId) throws Exception {
		return searchMatchingReports(lostReportsService.getLostReport(lostReportId));
	}

	@Override
	public List<FoundReport> searchMatchingReports(LostReport lostReport) throws Exception {

		String query = " from MatchReport where matchingKey = '" + lostReport.getMatchingKey() + "' and status = '" + lostReport.getStatus().name() + "'";
		List<FoundReport> matchReports = commonHibernateDao.getHibernateTemplate().find(query);
		
		switch(lostReport.getIdType()){
		case UNIQUE_ID:
		case LOST_AND_FOUND_ID:
			if(matchReports != null && matchReports.size() == 1){
				matchFound(lostReport, matchReports.get(0));
			}else{
				Map<String, Object> dataObject = new HashMap<String, Object>();
				dataObject.put("lostReport", lostReport);
				
				AppMailSender.sendEmail("" + lostReport.getLostReportId(),lostReport.getAddressEmail(), dataObject, EventDetailsEnum.MATCH_NOT_FOUND);
			}
			break;
		case NONE:
			break;
		}
		
		return matchReports;
	}

	@Override
	public List<LostReport> searchMatchingReports(FoundReport matchReport) throws Exception {

		String query = " from LostReport where matchingKey = '" + matchReport.getMatchingKey() + "' and status = '" + matchReport.getStatus().name() + "'";
		List<LostReport> lostReports = commonHibernateDao.getHibernateTemplate().find(query);
		
		switch(matchReport.getIdType()){
		case UNIQUE_ID:
		case LOST_AND_FOUND_ID:
			if(lostReports != null && lostReports.size() == 1){
				matchFound(lostReports.get(0), matchReport);
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
		
		foundReport.setLostReportId(lostReport.getLostReportId());
		foundReport.setStatus(ReportStatusEnum.MATCHED);
		
		commonHibernateDao.update(lostReport);
		commonHibernateDao.update(foundReport);
		
		Map<String, Object> dataObject = new HashMap<String, Object>();
		dataObject.put("lostReport", lostReport);
		dataObject.put("matchReport", foundReport);
		
		AppMailSender.sendEmail("" + lostReport.getLostReportId() +  " - " + foundReport.getFoundReportId(), 
				new String[]{lostReport.getAddressEmail(), foundReport.getAddressEmail()}, dataObject, EventDetailsEnum.MATCH_FOUND);
	}
	
	
	/////////////////////////////////// LOST AND MATCH ID ///////////////////////////////////////////////////////////
	

	@Override
	public List<FoundReport> searchMatchingReportsAsPerLFI(Long lostAndFoundId) throws Exception {

		TagCreation tagCreation = tagCreationsService.getTagCreation(lostAndFoundId);
		
		String query = " from FoundReport where lostAndFoundId = '" + lostAndFoundId + "'";
		List<FoundReport> foundReports = commonHibernateDao.getHibernateTemplate().find(query);
		
		if(foundReports != null && foundReports.size() > 0){
			
			matchFoundAsPerLFI(tagCreation, foundReports);

			Map<String, Object> dataObject = new HashMap<String, Object>();
			dataObject.put("tagCreation", tagCreation);
			dataObject.put("matchReports", foundReports);
			
			AppMailSender.sendEmail("ID: " + tagCreation.getTagCreationId(),tagCreation.getAddressEmail(), dataObject, EventDetailsEnum.MATCH_FOUND_LFI);
			
		}else{
			
			Map<String, Object> dataObject = new HashMap<String, Object>();
			dataObject.put("tagCreation", tagCreation);
			
			AppMailSender.sendEmail("ID: " + lostAndFoundId,tagCreation.getAddressEmail(), dataObject, EventDetailsEnum.MATCH_NOT_FOUND_LFI);
		}
		
		return foundReports;
	}

	private void matchFoundAsPerLFI(TagCreation tagCreation, List<FoundReport> foundReports) {
		String query = " update FoundReport set status = '"+ReportStatusEnum.MATCHED.name()+"' where lostAndFoundId = '" + tagCreation.getTagCreationId() + "'";
		int bulkUpdate = commonHibernateDao.getHibernateTemplate().bulkUpdate(query);
	}

	
}
