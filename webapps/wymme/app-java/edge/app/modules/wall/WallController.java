package edge.app.modules.wall;

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
public class WallController {

	private static final Logger logger = LoggerFactory.getLogger(WallController.class);
	
	@Autowired
	private WallService wallService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	@RequestMapping(value={"/secured/loadWallProfiles"})
	public EdgeResponse<List<ProfileDetails>> loadWallProfiles(Principal principal){
		String userName = principal.getName();
		List<ProfileDetails> searchedProfiles = wallService.loadWallProfiles(userName);

		if(searchedProfiles == null || searchedProfiles.size() == 0){
			return EdgeResponse.createErrorResponse(null,"There is no such profile!", null, null);
		}else{
			return EdgeResponse.createDataResponse(searchedProfiles, "");			
		}
	}
	
	@RequestMapping(value={"/secured/removeFromWall"})
	public EdgeResponse<String> removeFromWall(Principal principal, 
			@RequestBody String toRemove){
		try{
			String userName = principal.getName();
			wallService.removeFromWall(userName, toRemove);
			return EdgeResponse.createDataResponse("", "Profile Removed Successfully.");
		}catch (Exception ex) {
			return EdgeResponse.createExceptionResponse(ex.getMessage(), new AppException(ex, ""));
		}
	}
	
}
