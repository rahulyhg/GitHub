package edge.app.modules.search;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edge.app.modules.profile.ProfileDetails;
import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private SearchService searchService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	@RequestMapping(value={"/unsecured/searchById"})
	public EdgeResponse<ProfileDetails> searchById(
				@RequestBody String profileId
			){			
		
		ProfileDetails profileDetails = searchService.searchById(profileId.toUpperCase());

		if(profileDetails == null){
			return EdgeResponse.createErrorResponse(null,"There is no such profile!", null, null);
		}else{
			return EdgeResponse.createDataResponse(profileDetails, "");			
		}
	}

	@RequestMapping(value={"/secured/loadRemovedProfiles"})
	public EdgeResponse<List<ProfileDetails>> loadRemovedProfiles(Principal principal){
		String userName = principal.getName();
		List<ProfileDetails> searchedProfiles = searchService.loadRemovedProfiles(userName);

		if(searchedProfiles == null || searchedProfiles.size() == 0){
			return EdgeResponse.createErrorResponse(null,"There is no such profile!", null, null);
		}else{
			return EdgeResponse.createDataResponse(searchedProfiles, "");			
		}
	}
	

	@RequestMapping(value={"/secured/undoRemoveFromWall"})
	public EdgeResponse<String> undoRemoveFromWall(Principal principal, 
			@RequestBody String toAdd){
		try{
			String userName = principal.getName();
			searchService.undoRemoveFromWall(userName, toAdd);
			return EdgeResponse.createDataResponse("", "Profile Added Back Successfully.");
		}catch (Exception ex) {
			return EdgeResponse.createExceptionResponse(ex.getMessage(), new AppException(ex, ""));
		}
	}
}
