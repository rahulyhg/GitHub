package edge.app.modules.reports;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class ReportsController {

private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	
	@Autowired
	private ReportsService reportsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public ReportsService getReportsService() {
		return reportsService;
	}
	
	@RequestMapping(value={"/secured/getMembershipsReport"})
	public EdgeResponse<List> getMembershipsReport( 
			Principal principal			
			) throws Exception{	
		try{	
			List allReports = reportsService.getMembershipsReport(principal.getName());
			return EdgeResponse.createDataResponse(allReports, "");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	@RequestMapping(value={"/secured/getPaymentsReport"})
	public EdgeResponse<List> getPaymentsReport( 
			Principal principal			
			) throws Exception{	
		try{	
			List allReports = reportsService.getPaymentsReport(principal.getName());
			return EdgeResponse.createDataResponse(allReports, "");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	@RequestMapping(value={"/secured/getAttendancesReport"})
	public EdgeResponse<List> getAttendancesReport( 
			Principal principal			
			) throws Exception{	
		try{	
			List allReports = reportsService.getAttendancesReport(principal.getName());
			return EdgeResponse.createDataResponse(allReports, "");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	@RequestMapping(value={"/secured/getExpensesReport"})
	public EdgeResponse<List> getExpensesReport( 
			Principal principal			
			) throws Exception{	
		try{	
			List allReports = reportsService.getExpensesReport(principal.getName());
			return EdgeResponse.createDataResponse(allReports, "");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setReportsService(ReportsService reportsService) {
		this.reportsService = reportsService;
	}
}
