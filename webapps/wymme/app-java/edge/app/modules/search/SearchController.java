package edge.app.modules.search;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import edge.app.modules.profileConnection.ProfileConnectionService;
import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private SearchService searchService;

	@Autowired
	private ProfileConnectionService profileConnectionService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	@RequestMapping(value={"/unsecured/searchById"})
	public EdgeResponse<List<ProfileDetails>> searchById(
				@RequestBody String profileId
			){
		
		ProfileDetails profileDetails = searchService.searchById(profileId.toUpperCase());

		if(profileDetails == null){
			return EdgeResponse.createErrorResponse(null,"No Profile Found As Per Criteria!", null, null);
		}else{
			List<ProfileDetails> profileDetailsList = new ArrayList<ProfileDetails>();
			profileDetailsList.add(profileDetails);
			
			return EdgeResponse.createDataResponse(profileDetailsList, "");			
		}
	}

	@RequestMapping(value={"/secured/loadRemovedProfiles"})
	public EdgeResponse<List<ProfileDetails>> loadRemovedProfiles(Principal principal){
		String userName = principal.getName();
		List<ProfileDetails> searchedProfiles = searchService.loadRemovedProfiles(userName);

		if(searchedProfiles == null || searchedProfiles.size() == 0){
			return EdgeResponse.createErrorResponse(null,"No Profile Found As Per Criteria!", null, null);
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
			return EdgeResponse.createDataResponse("", "'" + toAdd + "' Profile Added Back Successfully to your wall.");
		}catch (AppException ex) {
			return EdgeResponse.createExceptionResponse(ex);
		}
	}

	@RequestMapping(value={"/secured/searchProfiles"})
	public EdgeResponse<List<ProfileDetails>> searchProfiles(Principal principal, 
			@RequestBody String searchType){
		try{
			String userName = principal.getName();
			List<ProfileDetails> searchedProfiles = profileConnectionService.searchProfiles(userName, searchType);
			
			if(searchedProfiles == null || searchedProfiles.size() == 0){
				return EdgeResponse.createErrorResponse(null,"No Profile Found As Per Criteria!", null, null);
			}else{
				return EdgeResponse.createDataResponse(searchedProfiles, "");			
			}
			
		}catch (AppException ex) {
			return EdgeResponse.createExceptionResponse(ex);
		}
	}
	
	@RequestMapping(value={"/secured/actionRequest"})
	public EdgeResponse<String> actionRequest(Principal principal, 
			@RequestBody ConnectionAction connectionAction){
		try{
			String userName = principal.getName();
			profileConnectionService.actionRequest(userName, connectionAction);
			return EdgeResponse.createDataResponse("", "Request from profile '" + connectionAction.getProfileId() + "' has been successfully " + connectionAction.getConnectionStatus().name() + ".");
		}catch (AppException ex) {
			return EdgeResponse.createExceptionResponse(ex);
		}
	}
	
	@RequestMapping(value={"/secured/showContactDetails"})
	public EdgeResponse<ProfileDetails> showContactDetails(Principal principal, 
			@RequestBody String profileId){
		try{
			String userName = principal.getName();
			ProfileDetails profileDetails = profileConnectionService.showContactDetails(userName, profileId);
			return EdgeResponse.createDataResponse(profileDetails, "");
		}catch (AppException ex) {
			return EdgeResponse.createExceptionResponse(ex);
		}
	}
	
}
