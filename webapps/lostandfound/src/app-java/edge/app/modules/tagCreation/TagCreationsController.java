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
			return EdgeResponse.createDataResponse(addTagCreation, "Lost And Found ID has been successfully created with ID : '" + addTagCreation.getTagCreationId() 
					+ "'. Please check email for further details. Thank You!");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	
	public void setTagCreationsService(TagCreationsService tagCreationsService) {
		this.tagCreationsService = tagCreationsService;
	}
}
