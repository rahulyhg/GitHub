package edge.app.modules.foundReport;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class FoundReportsController {

	private static final Logger logger = LoggerFactory.getLogger(FoundReportsController.class);
	
	@Autowired
	private FoundReportsService foundReportsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public FoundReportsService getFoundReportsService() {
		return foundReportsService;
	}

	@RequestMapping(value={"/unsecured/saveFoundReport"})
	public EdgeResponse<FoundReport> addFoundReport(
			@RequestBody FoundReport foundReport		
			) throws Exception{	
		try{
			FoundReport addFoundReport = foundReportsService.saveFoundReport(foundReport);
			return EdgeResponse.createDataResponse(addFoundReport, "Found Report submitted Successfully with ID : '" + addFoundReport.getFoundReportId()
					+ "'. Please check email for further details. Thank You!");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	
	@RequestMapping(value={"/unsecured/getFoundReport"})
	public EdgeResponse<FoundReport> getFoundReport( 
			Long foundReportId
			) throws Exception{	
		try{	
			FoundReport foundReport = foundReportsService.getFoundReport(foundReportId);
			return EdgeResponse.createDataResponse(foundReport, "");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setFoundReportsService(FoundReportsService foundReportsService) {
		this.foundReportsService = foundReportsService;
	}
}
