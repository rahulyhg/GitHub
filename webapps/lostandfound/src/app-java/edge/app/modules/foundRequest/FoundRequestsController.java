package edge.app.modules.foundRequest;

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
public class FoundRequestsController {

	private static final Logger logger = LoggerFactory.getLogger(FoundRequestsController.class);
	
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

	@RequestMapping(value={"/unsecured/saveFoundRequest"})
	public EdgeResponse<FoundRequest> addFoundRequest(
			@RequestBody FoundRequest foundRequest		
			) throws Exception{	
		try{
			FoundRequest addFoundRequest = foundRequestsService.saveFoundRequest(foundRequest);
			return EdgeResponse.createDataResponse(addFoundRequest, "FoundRequest added Successfully with ID : " + addFoundRequest.getFoundRequestId());
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	
	@RequestMapping(value={"/unsecured/getFoundRequest"})
	public EdgeResponse<FoundRequest> getFoundRequest( 
			int foundRequestId
			) throws Exception{	
		try{	
			FoundRequest foundRequest = foundRequestsService.getFoundRequest(foundRequestId);
			return EdgeResponse.createDataResponse(foundRequest, "");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setFoundRequestsService(FoundRequestsService foundRequestsService) {
		this.foundRequestsService = foundRequestsService;
	}
}
