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
import edge.core.exception.AppException;
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

	////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<FoundReport> searchMatchingReports(Long lostReportId) throws Exception {
		return searchMatchingReports(lostReportsService.getLostReport(lostReportId));
	}
	
	@Override
	public List<FoundReport> searchMatchingReports(LostReport lostReport) throws Exception {

		List<FoundReport> foundReports = null;
		
		switch(lostReport.getIdType()){
		case UNIQUE_ID:
			searchMatchingReportsAsPerUnique(lostReport);
			break;
		case NONE:
			searchMatchingReportsAsPerNone(lostReport.getMatchingKey());
			break;
		case LOST_AND_FOUND_ID:
			throw new AppException(null, "Lost Request with Lost and Found Id can not be submitted!");
		}
		
		return foundReports;
	}

	@Override
	public List<LostReport> searchMatchingReports(FoundReport foundReport) throws Exception {
		List<LostReport> lostReports = null;
		switch(foundReport.getIdType()){
		case UNIQUE_ID:
			lostReports = searchMatchingReportsAsPerUnique(foundReport);
			break;
		case NONE:
			searchMatchingReportsAsPerNone(foundReport.getMatchingKey());
			break;
		case LOST_AND_FOUND_ID:
			searchMatchingReportsAsPerLFI(foundReport.getLostAndFoundId());
		}
		return lostReports;
	}

	/////////////////////////////////// NONE ////////////////////////////////////////////////////////////////
	
	@Override
	public void searchMatchingReportsAsPerNone(String matchingKey) throws Exception {

		String lostQuery = " from LostReport where matchingKey = '" + matchingKey + "'";
		List<LostReport> lostReports = commonHibernateDao.getHibernateTemplate().find(lostQuery);

		String foundQuery = " from FoundReport where matchingKey = '" + matchingKey + "'";
		List<FoundReport> foundReports = commonHibernateDao.getHibernateTemplate().find(foundQuery);
		
		if(lostReports != null && lostReports.size() > 0){
			
			for (LostReport lostReport : lostReports) {
				if(foundReports != null && foundReports.size() > 0){
					for (FoundReport foundReport : foundReports) {
						Map<String, Object> dataObject = new HashMap<String, Object>();
						dataObject.put("owner", lostReport);
						dataObject.put("foundReport", foundReport);
						
						AppMailSender.sendEmail("Lost Id: " + lostReport.getLostReportId() + " : Found Id" + foundReport.getFoundReportId(),
								new String[]{lostReport.getAddressEmail()}, 
								dataObject, EventDetailsEnum.PROBABLE_MATCH_FOUND);
					}
				}
			}
		}
	}
	
	/////////////////////////////////// UNIQUE ID ///////////////////////////////////////////////////////////
	
	@Override
	public List<LostReport> searchMatchingReportsAsPerUnique(FoundReport foundReport) throws Exception {

		String query = " from LostReport where matchingKey = '" + foundReport.getMatchingKey() + "'";
		List<LostReport> lostReports = commonHibernateDao.getHibernateTemplate().find(query);
		
		if(lostReports != null && lostReports.size() > 0){
			
			updateDatabaseAsPerUnique(foundReport.getMatchingKey());

			for (LostReport lostReport : lostReports) {
				Map<String, Object> dataObject = new HashMap<String, Object>();
				dataObject.put("owner", lostReport);
				dataObject.put("foundReport", foundReport);
				
				AppMailSender.sendEmail("Lost Id: " + lostReport.getLostReportId() + " : Found Id" + foundReport.getFoundReportId(),
						new String[]{lostReport.getAddressEmail(),foundReport.getAddressEmail()}, 
						dataObject, EventDetailsEnum.MATCH_FOUND);
			}
			
		}
		
		return lostReports;
	}

	@Override
	public List<FoundReport> searchMatchingReportsAsPerUnique(LostReport lostReport) throws Exception {

		String query = " from FoundReport where matchingKey = '" + lostReport.getMatchingKey() + "'";
		List<FoundReport> foundReports = commonHibernateDao.getHibernateTemplate().find(query);
		
		if(foundReports != null && foundReports.size() > 0){
			
			updateDatabaseAsPerUnique(lostReport.getMatchingKey());

			for (FoundReport foundReport : foundReports) {
				Map<String, Object> dataObject = new HashMap<String, Object>();
				dataObject.put("owner", lostReport);
				dataObject.put("foundReport", foundReport);
				
				AppMailSender.sendEmail("Lost Id: " + lostReport.getLostReportId() + " : Found Id" + foundReport.getFoundReportId(),
						new String[]{lostReport.getAddressEmail(),foundReport.getAddressEmail()}, 
						dataObject, EventDetailsEnum.MATCH_FOUND);
			}
			
		}else{
			
			Map<String, Object> dataObject = new HashMap<String, Object>();
			dataObject.put("owner", lostReport);
			
			AppMailSender.sendEmail("Lost ID: " + lostReport, 
					lostReport.getAddressEmail(), 
					dataObject, EventDetailsEnum.MATCH_NOT_FOUND);
		}
		
		return foundReports;
	}
	
	private void updateDatabaseAsPerUnique(String matchingKey) {
		String query = " update FoundReport set status = '"+ReportStatusEnum.MATCHED.name()+"' where matchingKey = '" + matchingKey + "'";
		int bulkUpdate = commonHibernateDao.getHibernateTemplate().bulkUpdate(query);
		
		query = " update LostReport set status = '"+ReportStatusEnum.MATCHED.name()+"' where matchingKey = '" + matchingKey + "'";
		bulkUpdate = commonHibernateDao.getHibernateTemplate().bulkUpdate(query);
	}
	
	/////////////////////////////////// LOST AND FOUND ID ///////////////////////////////////////////////////////////
	
	@Override
	public List<FoundReport> searchMatchingReportsAsPerLFI(Long lostAndFoundId) throws Exception {

		TagCreation tagCreation = tagCreationsService.getTagCreation(lostAndFoundId);
		
		String query = " from FoundReport where lostAndFoundId = '" + lostAndFoundId + "'";
		List<FoundReport> foundReports = commonHibernateDao.getHibernateTemplate().find(query);
		
		if(foundReports != null && foundReports.size() > 0){
			
			updateDatabaseAsPerLfi(tagCreation.getTagCreationId(), foundReports);

			for (FoundReport foundReport : foundReports) {
				Map<String, Object> dataObject = new HashMap<String, Object>();
				dataObject.put("owner", tagCreation);
				dataObject.put("foundReport", foundReport);
				
				AppMailSender.sendEmail("LandF ID: " + tagCreation.getTagCreationId(),
						new String[]{tagCreation.getAddressEmail(),foundReport.getAddressEmail()}, 
						dataObject, EventDetailsEnum.MATCH_FOUND);
			}
			
		}else{
			
			Map<String, Object> dataObject = new HashMap<String, Object>();
			dataObject.put("owner", tagCreation);
			
			AppMailSender.sendEmail("ID: " + lostAndFoundId, 
					tagCreation.getAddressEmail(), 
					dataObject, EventDetailsEnum.MATCH_NOT_FOUND);
		}
		
		return foundReports;
	}

	private void updateDatabaseAsPerLfi(Long lostAndFoundId, List<FoundReport> foundReports) {
		String query = " update FoundReport set status = '"+ReportStatusEnum.MATCHED.name()+"' where lostAndFoundId = '" + lostAndFoundId + "'";
		int bulkUpdate = commonHibernateDao.getHibernateTemplate().bulkUpdate(query);
	}
}
