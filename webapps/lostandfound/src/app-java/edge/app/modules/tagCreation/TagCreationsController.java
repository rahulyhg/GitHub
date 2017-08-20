package edge.app.modules.tagCreation;

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
import org.springframework.web.bind.annotation.RequestParam;

import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class TagCreationsController {

	private static final Logger logger = LoggerFactory.getLogger(TagCreationsController.class);
	
	@Autowired
	private TagCreationsService tagCreationsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public TagCreationsService getTagCreationsService() {
		return tagCreationsService;
	}

	@RequestMapping(value={"/unsecured/saveTagCreation"})
	public EdgeResponse<TagCreation> saveTagCreation(
			@RequestBody TagCreation tagCreation		
			) throws Exception{	
		try{
			TagCreation addTagCreation = tagCreationsService.saveTagCreation(tagCreation);
			return EdgeResponse.createDataResponse(addTagCreation, "Lost And Found ID has been successfully saved with ID : '" + addTagCreation.getTagCreationId() 
					+ "'. Please check email for further details. Thank You!");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	

	@RequestMapping(value={"/unsecured/sendOTPForTagUpdate"})
	public EdgeResponse<String> sendOTPForTagUpdate(
				@RequestParam String emailId	
			) throws Exception{	
		try{
			tagCreationsService.sendOTPForTagUpdate(emailId);
			return EdgeResponse.createDataResponse("", "Please check email for OTP!");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}catch(Exception ae){
			return EdgeResponse.createErrorResponse("","Some error while processing.",null,null);
		}
		
	}

	@RequestMapping(value={"/unsecured/verifyOTPForTagUpdate"})
	public EdgeResponse<TagCreation> verifyOTPForTagUpdate(
				@RequestParam String emailId, @RequestParam String otp
			) throws Exception{	
		try{
			TagCreation tagCreation = tagCreationsService.verifyOTPForTagUpdate(emailId, otp);
			return EdgeResponse.createDataResponse(tagCreation, "Verification successful! Please update and submit the form!");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}catch(Exception ae){
			return EdgeResponse.createErrorResponse(null,"Some error while processing.",null,null);
		}
		
	}
	
	public void setTagCreationsService(TagCreationsService tagCreationsService) {
		this.tagCreationsService = tagCreationsService;
	}
}
