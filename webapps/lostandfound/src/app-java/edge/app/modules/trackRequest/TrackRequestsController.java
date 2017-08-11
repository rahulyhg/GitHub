package edge.app.modules.trackRequest;

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

import edge.app.modules.foundRequest.FoundRequestsService;
import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class TrackRequestsController {

	private static final Logger logger = LoggerFactory.getLogger(TrackRequestsController.class);
	
	@Autowired
	private FoundRequestsService foundRequestsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public FoundRequestsService getFoundRequestsService() {
		return foundRequestsService;
	}

	@RequestMapping(value={"/unsecured/searchMatchingReqAsPerLRI"})
	public EdgeResponse<String> searchMatchingReqAsPerLRI(
			@RequestBody int lostRequestId	
			) throws Exception{	
		try{
			foundRequestsService.searchMatchingRequests(lostRequestId);
			return EdgeResponse.createDataResponse("", "Mail sent successfully!");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}catch(Exception ae){
			return EdgeResponse.createErrorResponse("","There is no such request.",null,null);
		}
		
	}

	@RequestMapping(value={"/unsecured/searchMatchingReqAsPerLFI"})
	public EdgeResponse<String> searchMatchingReqAsPerLFI(
			@RequestBody String lostAndFoundId	
			) throws Exception{	
		try{
			foundRequestsService.searchMatchingRequestsAsPerLFI(lostAndFoundId);
			return EdgeResponse.createDataResponse("", "Mail sent successfully!");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}catch(Exception ae){
			return EdgeResponse.createErrorResponse("","There is no such request.",null,null);
		}
		
	}
		
	public void setFoundRequestsService(FoundRequestsService foundRequestsService) {
		this.foundRequestsService = foundRequestsService;
	}
}
