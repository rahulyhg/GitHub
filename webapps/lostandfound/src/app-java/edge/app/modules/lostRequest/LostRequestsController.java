package edge.app.modules.lostRequest;

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

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class LostRequestsController {

	private static final Logger logger = LoggerFactory.getLogger(LostRequestsController.class);
	
	@Autowired
	private LostRequestsService lostRequestsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public LostRequestsService getLostRequestsService() {
		return lostRequestsService;
	}

	@RequestMapping(value={"/unsecured/saveLostRequest"})
	public EdgeResponse<LostRequest> addLostRequest(
			@RequestBody LostRequest lostRequest		
			) throws Exception{	
		try{
			LostRequest addLostRequest = lostRequestsService.saveLostRequest(lostRequest);
			return EdgeResponse.createDataResponse(addLostRequest, "LostRequest added Successfully with ID : " + addLostRequest.getLostRequestId());
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	
	@RequestMapping(value={"/unsecured/getLostRequest"})
	public EdgeResponse<LostRequest> getLostRequest( 
			int lostRequestId
			) throws Exception{	
		try{	
			LostRequest lostRequest = lostRequestsService.getLostRequest(lostRequestId);
			return EdgeResponse.createDataResponse(lostRequest, "");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setLostRequestsService(LostRequestsService lostRequestsService) {
		this.lostRequestsService = lostRequestsService;
	}
}
