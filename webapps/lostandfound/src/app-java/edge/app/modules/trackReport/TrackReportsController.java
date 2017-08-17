package edge.app.modules.trackReport;

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

import edge.app.modules.foundReport.FoundReportsService;
import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class TrackReportsController {

	private static final Logger logger = LoggerFactory.getLogger(TrackReportsController.class);
	
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

	@RequestMapping(value={"/unsecured/searchMatchingReqAsPerLRI"})
	public EdgeResponse<String> searchMatchingReqAsPerLRI(
			@RequestBody int lostReportId	
			) throws Exception{	
		try{
			foundReportsService.searchMatchingReports(lostReportId);
			return EdgeResponse.createDataResponse("", "Mail sent successfully!");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}catch(Exception ae){
			return EdgeResponse.createErrorResponse("","There is no such report.",null,null);
		}
		
	}

	@RequestMapping(value={"/unsecured/searchMatchingReqAsPerLFI"})
	public EdgeResponse<String> searchMatchingReqAsPerLFI(
			@RequestBody String lostAndFoundId	
			) throws Exception{	
		try{
			foundReportsService.searchMatchingReportsAsPerLFI(lostAndFoundId);
			return EdgeResponse.createDataResponse("", "Mail sent successfully!");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}catch(Exception ae){
			return EdgeResponse.createErrorResponse("","There is no such report.",null,null);
		}
		
	}
		
	public void setFoundReportsService(FoundReportsService foundReportsService) {
		this.foundReportsService = foundReportsService;
	}
}