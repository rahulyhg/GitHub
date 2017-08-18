package edge.app.modules.lostReport;

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
public class LostReportsController {

	private static final Logger logger = LoggerFactory.getLogger(LostReportsController.class);
	
	@Autowired
	private LostReportsService lostReportsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public LostReportsService getLostReportsService() {
		return lostReportsService;
	}

	@RequestMapping(value={"/unsecured/saveLostReport"})
	public EdgeResponse<LostReport> addLostReport(
			@RequestBody LostReport lostReport		
			) throws Exception{	
		try{
			LostReport addLostReport = lostReportsService.saveLostReport(lostReport);
			return EdgeResponse.createDataResponse(addLostReport, "Lost Report submitted Successfully with ID : '" + addLostReport.getLostReportId() 
					+ "'. Please check email for further details. Thank You!");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	
	@RequestMapping(value={"/unsecured/getLostReport"})
	public EdgeResponse<LostReport> getLostReport( 
			Long lostReportId
			) throws Exception{	
		try{	
			LostReport lostReport = lostReportsService.getLostReport(lostReportId);
			return EdgeResponse.createDataResponse(lostReport, "");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setLostReportsService(LostReportsService lostReportsService) {
		this.lostReportsService = lostReportsService;
	}
}
